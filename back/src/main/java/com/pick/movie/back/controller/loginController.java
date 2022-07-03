package com.pick.movie.back.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pick.movie.back.config.auth.PrincipalDetails;
import com.pick.movie.back.config.jwt.JwtProperties;
import com.pick.movie.back.dto.*;
import com.pick.movie.back.model.RefreshToken;
import com.pick.movie.back.model.User;
import com.pick.movie.back.repository.RefreshTokenRepository;
import com.pick.movie.back.repository.UserRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class loginController {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    PasswordEncoder passwordEncoder;


    ObjectMapper om = new ObjectMapper();

    @Autowired
    public loginController(UserRepository userRepository, PasswordEncoder passwordEncoder, RefreshTokenRepository refreshTokenRepository) {

        this.userRepository =userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    // 모든 사람이 접근 가능
    @ApiOperation(value = "HTTP GET EXAMPLE", notes = "GET 요청에 대한 예제 입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 404, message = "찾을 수 없음")
    })
    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
    }

    // Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
    // 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.

    // 유저 혹은 매니저 혹은 어드민이 접근 가능
    @GetMapping("user")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal : "+principal.getUser().getId());
        System.out.println("principal : "+principal.getUser().getUsername());
        System.out.println("principal : "+principal.getUser().getPassword());

        return "<h1>user  user</h1>";
    }

    // 매니저 혹은 어드민이 접근 가능
    @GetMapping("manager/reports")
    public String reports() {
        return "<h1>reports</h1>";
    }

    // 어드민이 접근 가능
    @GetMapping("admin/users")
    public List<User> users(){
        return userRepository.findAll();
    }

    @GetMapping(path = "reissueAccessToken")
    public String reissueAccessToken(@RequestHeader Map<String, Object> requestHeader, HttpServletResponse response) throws JsonProcessingException {

        //여기에 토큰을 받아옴. {"jwtAccessToken": "tekslkj", "jwtRefreshToken":"dfjwfwccc"}

        JwtDto jwtDto = om.readValue((String)requestHeader.get("token"), JwtDto.class);
        System.out.println(jwtDto.toString());
        String username;

        try {
            username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtDto.getJwtAccessToken().replace(JwtProperties.TOKEN_PREFIX, ""))
                    .getClaim("username").asString();

            System.out.println(username);

        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("expired")) {    //만료가 되었으니 재발급.
                RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(   jwtDto.getJwtRefreshToken().replace(JwtProperties.TOKEN_PREFIX, ""))  ;
                System.out.println(refreshToken.getRefreshToken()+"  "+refreshToken.getCreateDate());

                username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(refreshToken.getRefreshToken())
                        .getClaim("username").asString();

                User user = userRepository.findByUsername(username);

                String jwtAccessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                        .withClaim("id", user.getId())
                        .withClaim("username", user.getUsername())
                        .sign(Algorithm.HMAC512(JwtProperties.SECRET));

                AccessTokenDto accessTokenDto = new AccessTokenDto(JwtProperties.TOKEN_PREFIX+jwtAccessToken);

                String tokensJson = om.writeValueAsString(accessTokenDto);

                response.addHeader(JwtProperties.REFRESH_HEADER_STRING, tokensJson);
                return "Access token reissued";
            }
        }

        response.setStatus(401);
        return "Access token not Expire";
    }

    @ApiOperation(value = "signup", notes = "signup API입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value ="사용자 ID(email)", required = true),
            @ApiImplicitParam(name="password", value ="비밀번호", required = true),
            @ApiImplicitParam(name="tags", value ="태그들", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "이미 존재하는 회원"),
            @ApiResponse(code = 402, message = "비밀번호는영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.")
    })
    @PostMapping("singup")
    public String singup( SignupDto signupDto,HttpServletResponse response) {

         Optional<User> isUser = Optional.ofNullable(userRepository.findByUsername(signupDto.getUsername()));

        System.out.println(signupDto.toString());

         if(isUser.isPresent()){
             response.setStatus(401);
             return "이미 존재하는 회원명입니다.";
         }

        Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
        Matcher passMatcher1 = passPattern1.matcher(signupDto.getPassword());

        if(!passMatcher1.find()){
            response.setStatus(402);
            return "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.";
        }

        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setPassword(signupDto.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return "회원가입 완료";
    }
}
