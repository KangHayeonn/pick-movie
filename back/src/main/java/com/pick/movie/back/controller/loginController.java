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


    // ?????? ????????? ?????? ??????
    @ApiOperation(value = "HTTP GET EXAMPLE", notes = "GET ????????? ?????? ?????? ?????????.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "??????"),
            @ApiResponse(code = 500, message = "????????????"),
            @ApiResponse(code = 404, message = "?????? ??? ??????")
    })

    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
    }



    // Tip : JWT??? ???????????? UserDetailsService??? ???????????? ?????? ????????? @AuthenticationPrincipal ?????? ?????????.
    // ???????????? @AuthenticationPrincipal??? UserDetailsService?????? ????????? ??? ??????????????? ????????????.



    // ?????? ?????? ????????? ?????? ???????????? ?????? ??????
    @GetMapping("user")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal : "+principal.getUser().getId());
        System.out.println("principal : "+principal.getUser().getUsername());
        System.out.println("principal : "+principal.getUser().getPassword());
        return "<h1>user  user</h1>";
    }


    @ApiOperation(value = "showtags", notes = "showtags API?????????.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "[{\"id\":2,\"tag\":\"Action\"},{\"id\":3,\"tag\":\"Adventure\"}]"),
            @ApiResponse(code = 500, message = "????????????"),
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

    @ApiOperation(value = "login", notes = "login API?????????.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value ="????????? ID(email)", required = true),
            @ApiImplicitParam(name="password", value ="????????????", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "??????"),
            @ApiResponse(code = 500, message = "????????????"),
            @ApiResponse(code = 401, message = "?????? ????????? ?????????."),
            @ApiResponse(code = 402, message = "???????????????????????? ???????????? ????????? ???????????? 8??? ??????????????? ?????????.")
    })
    @PostMapping("/login")
    public String login(@RequestBody HttpServletRequest request){
        return "????????? ????????? ?????? api";
    }



    @ApiOperation(value = "reissueAccessToken", notes = "reissueAccessToken API?????????.\n" +
            "header???  key : token value : {\"jwtAccessToken\": \"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZXVuZ21pbjIiLCJpZCI6NCwiZXhwIjoxNjU2NTI0NjE2LCJ1c2VybmFtZSI6InNldW5nbWluMiJ9.ciIX0cXD3ahJIvB4f2GE60n0qRPaE2HQfwqr7nBtBoKiXKCSacaZh-2wKJo_9gXK9KtKUUAtIRh6vtfe0AapuQ\", \"jwtRefreshToken\":\"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZXVuZ21pbjIiLCJpZCI6NCwiZXhwIjoxNjU3NzM0MjA2LCJ1c2VybmFtZSI6InNldW5nbWluMiJ9.7K5zMxSlaUb1flbKCLYfuY83QUxnIF5LpjxJSuwKtBwXfvP2z6eN9_dmv3YUuDzEnRJFVT_moXjpagSG39oSiw\"} ")
    @ApiImplicitParams({
            @ApiImplicitParam(name="jwtAccessToken", value ="????????? AccessToken", required = true),
            @ApiImplicitParam(name="jwtRefreshToken", value ="RefreshToken", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Access token reissued"),
            @ApiResponse(code = 500, message = "????????????"),
            @ApiResponse(code = 401, message = "Access token not Expire")
    })
    @GetMapping(path = "reissueAccessToken")
    public AccessTokenDto reissueAccessToken(@RequestHeader Map<String, Object> requestHeader, HttpServletResponse response) throws JsonProcessingException {

        //????????? ????????? ?????????. token : {"jwtAccessToken": "tekslkj", "jwtRefreshToken":"dfjwfwccc"}

        JwtDto jwtDto = om.readValue((String)requestHeader.get("token"), JwtDto.class);
        System.out.println(jwtDto.toString());
        String username;

        try {
            username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtDto.getJwtAccessToken().replace(JwtProperties.TOKEN_PREFIX, ""))
                    .getClaim("username").asString();

            System.out.println(username);

        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("expired")) {    //????????? ???????????? ?????????.
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

    @ApiOperation(value = "signup", notes = "signup API?????????.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value ="????????? ID(email)", required = true),
            @ApiImplicitParam(name="password", value ="????????????", required = true),
            @ApiImplicitParam(name="tags", value ="?????????", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\n" +
                    "    \"id\": 34,\n" +
                    "    \"username\": \"testId5@naver.com\"\n" +
                    "}"),
            @ApiResponse(code = 201, message = "???????????? ?????? ??????????????? ???????????????. ?????? ????????? ???????????? ??????????????? ?????????????????????."),
            @ApiResponse(code = 500, message = "????????????"),
            @ApiResponse(code = 401, message = "?????? ???????????? ??????"),
            @ApiResponse(code = 402, message = "???????????????????????? ???????????? ????????? ???????????? 8??? ??????????????? ?????????."),
            @ApiResponse(code = 403, message = "????????? ????????? ??????????????????."),
            @ApiResponse(code = 405, message = "????????? ????????? ????????????.")
    })
    @PostMapping("signup")
    public SignupResponseDto signup(@RequestBody SignupDto signupDto,HttpServletResponse response) {

        if(signupDto==null){
            response.setStatus(405);
            return null;
            //return "?????? ????????????.";
        }

        Optional<User> isUser = Optional.ofNullable(userRepository.findByUsername(signupDto.getUsername()));

        Pattern passPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
        Matcher passMatcher = passPattern.matcher(signupDto.getPassword());

        Pattern emailPattern = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
        Matcher emailMatcher = emailPattern.matcher(signupDto.getUsername());

        if(isUser.isPresent()){
            response.setStatus(401);
            return null;
            //return "?????? ???????????? ??????????????????.";
        }

        if(!passMatcher.find()){
            response.setStatus(402);
            return null;
            //return "??????????????? ????????? ???????????? ????????? ???????????? 8??? ??????????????? ?????????.";
        }

        if(!emailMatcher.find()){
            response.setStatus(403);
            return null;
            //return "????????? ????????? ??????????????????.";
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

            // java 11 ??? ??????. ???????????? ????????? ??? ????????? ??????.

            byTag.ifPresentOrElse(tag ->{
                HashtagRelationUser hashtagRelationUser = new HashtagRelationUser();
                hashtagRelationUser.setUser(user);
                hashtagRelationUser.setTag(tag);
                hashtagRelationUserRepository.save(hashtagRelationUser);
            },() -> response.setStatus(201));
        }
        if(response.getStatus()==201){      //?????? ???????????? ????????? ????????? ???.
            return null;
            //return "???????????? ?????? ??????????????? ???????????????. ?????? ????????? ???????????? ??????????????? ?????????????????????.";
        }

        SignupResponseDto signupSignupResponseDto = new SignupResponseDto();
        signupSignupResponseDto.setId(user.getId());
        signupSignupResponseDto.setUsername(user.getUsername());
        return signupSignupResponseDto;
    }
}
