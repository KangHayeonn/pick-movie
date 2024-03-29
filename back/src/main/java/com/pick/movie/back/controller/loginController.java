package com.pick.movie.back.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pick.movie.back.config.auth.PrincipalDetails;
import com.pick.movie.back.config.jwt.JwtProperties;
import com.pick.movie.back.dto.*;
import com.pick.movie.back.model.HashtagRelationUser;
import com.pick.movie.back.model.RefreshToken;
import com.pick.movie.back.model.TagList;
import com.pick.movie.back.model.User;
import com.pick.movie.back.movieparser.GetMovieInfo;
import com.pick.movie.back.repository.HashtagRelationUserRepository;
import com.pick.movie.back.repository.RefreshTokenRepository;
import com.pick.movie.back.repository.TagRepository;
import com.pick.movie.back.repository.UserRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class loginController {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TagRepository tagRepository;
    private final HashtagRelationUserRepository hashtagRelationUserRepository;

    PasswordEncoder passwordEncoder;
    ObjectMapper om = new ObjectMapper();

    @Autowired
    public loginController(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository, TagRepository tagRepository, PasswordEncoder passwordEncoder,HashtagRelationUserRepository hashtagRelationUserRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.tagRepository = tagRepository;
        this.passwordEncoder = passwordEncoder;
        this.hashtagRelationUserRepository=hashtagRelationUserRepository;
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


    @ApiOperation(value = "showtags", notes = "회원가입 시 선택할 총 태그목록")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[{\"id\":2,\"tag\":\"Action\"},{\"id\":3,\"tag\":\"Adventure\"}]"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/showtags")
    public String showtags() throws Exception{
        List<TagList> all = tagRepository.findAll();
        List<TagDto> tagNames = new ArrayList<>();

        for (TagList tagList : all) {
            TagDto tagDto = new TagDto();
            tagDto.setTag(tagList.getTag());
            tagDto.setId(tagList.getId());
            System.out.println(tagDto);
            tagNames.add(tagDto);
        }
        final String json = om.writeValueAsString(tagNames);
        return json;
    }

    @ApiOperation(value = "login", notes = "login API입니다.")
    @ApiImplicitParams({
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "토큰 시간이 만료됨."),
            @ApiResponse(code = 402, message = "비밀번호는영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.")
    })
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto){
        return "스웨거 명세를 위한 api";
    }



    @ApiOperation(value = "reissueAccessToken", notes = "reissueAccessToken API입니다.\n" +
            "header에  key : token value : {\"jwtAccessToken\": \"\", \"jwtRefreshToken\":\"\"} ")
    @ApiImplicitParams({
            @ApiImplicitParam(name="jwtAccessToken", value ="만료된 AccessToken", required = true),
            @ApiImplicitParam(name="jwtRefreshToken", value ="RefreshToken", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Access token reissued"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "Access token not Expire")
    })
    @GetMapping(path = "reissueAccessToken")
    public AccessTokenDto reissueAccessToken(@RequestHeader Map<String, Object> requestHeader, HttpServletResponse response) throws JsonProcessingException {

        //여기에 토큰을 받아옴. token : {"jwtAccessToken": "tekslkj", "jwtRefreshToken":"dfjwfwccc"}

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

                //String tokensJson = om.writeValueAsString(accessTokenDto);
                //response.addHeader(JwtProperties.REFRESH_HEADER_STRING, tokensJson);
                return accessTokenDto;
            }
        }
        response.setStatus(401);
        return null;
        //return "Access token not Expire";
    }

    @ApiOperation(value = "signup", notes = "signup API입니다.")
    @ApiImplicitParams({
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\n" +
                    "    \"id\": 34,\n" +
                    "    \"username\": \"testId5@naver.com\"\n" +
                    "}"),
            @ApiResponse(code = 201, message = "존재하지 않는 태그이름을 포함합니다. 해당 태그를 제외하고 회원가입이 성공하였습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "이미 존재하는 회원"),
            @ApiResponse(code = 402, message = "비밀번호는영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다."),
            @ApiResponse(code = 403, message = "이메일 형식을 유지해주세요."),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @PostMapping("signup")
    public SignupResponseDto signup(@RequestBody SignupDto signupDto,HttpServletResponse response) {

        if(signupDto==null){
            response.setStatus(405);
            return null;
            //return "값이 없습니다.";
        }

        Optional<User> isUser = Optional.ofNullable(userRepository.findByUsername(signupDto.getUsername()));

        Pattern passPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
        Matcher passMatcher = passPattern.matcher(signupDto.getPassword());

        Pattern emailPattern = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
        Matcher emailMatcher = emailPattern.matcher(signupDto.getUsername());

        if(isUser.isPresent()){
            response.setStatus(401);
            return null;
            //return "이미 존재하는 회원명입니다.";
        }

        if(!passMatcher.find()){
            response.setStatus(402);
            return null;
            //return "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.";
        }

        if(!emailMatcher.find()){
            response.setStatus(403);
            return null;
            //return "이메일 형식을 유지해주세요.";
        }

        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setPassword(signupDto.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");


        List<String> tags = signupDto.getTags();
        int tagSize = tags.size();

        userRepository.save(user);

        for (int i = 0; i < tagSize; i++) {
            Optional<TagList> byTag = Optional.ofNullable(tagRepository.findByTag(tags.get(i)));
//
//            if(byTag.isPresent()){
//                TagList tag = byTag.get();
//                HashtagRelationUser hashtagRelationUser = new HashtagRelationUser();
//                hashtagRelationUser.setUser(user);
//                hashtagRelationUser.setTag(tag);
//                hashtagRelationUserRepository.save(hashtagRelationUser);
//            }else{
//                response.setStatus(201);
//            }

            // java 11 이 하단. 지원하지 않으면 위 소스로 진행.

            byTag.ifPresentOrElse(tag ->{
                HashtagRelationUser hashtagRelationUser = new HashtagRelationUser();
                hashtagRelationUser.setUser(user);
                hashtagRelationUser.setTag(tag);
                hashtagRelationUserRepository.save(hashtagRelationUser);
            },() -> response.setStatus(201));
        }
        if(response.getStatus()==201){      //태그 저장에서 실패가 있었을 시.
            return null;
            //return "존재하지 않는 태그이름을 포함합니다. 해당 태그를 제외하고 회원가입이 성공하였습니다.";
        }

        SignupResponseDto signupSignupResponseDto = new SignupResponseDto();
        signupSignupResponseDto.setId(user.getId());
        signupSignupResponseDto.setUsername(user.getUsername());
        return signupSignupResponseDto;
    }
}
