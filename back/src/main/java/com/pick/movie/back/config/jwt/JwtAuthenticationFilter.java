package com.pick.movie.back.config.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pick.movie.back.config.auth.PrincipalDetails;
import com.pick.movie.back.dto.JwtDto;
import com.pick.movie.back.dto.LoginRequestDto;
import com.pick.movie.back.model.RefreshToken;
import com.pick.movie.back.repository.RefreshTokenRepository;
import com.pick.movie.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{


    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private final UserRepository userRepository;




    private final ObjectMapper om = new ObjectMapper();

    // Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
    // 인증 요청시에 실행되는 함수 => /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter : 진입");

        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
        LoginRequestDto loginRequestDto = null;
        try {
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("JwtAuthenticationFilter : "+loginRequestDto);

        // 유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword());

        System.out.println("JwtAuthenticationFilter : 토큰생성완료");

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.

        if(userRepository.findByUsername(loginRequestDto.getUsername())==null){
            //response.getWriter().write("아이디가 없음");
        }


        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("Authentication : "+principalDetailis.getUser().getUsername());
        return authentication;
    }

    // JWT Token 생성해서 response에 담아주기  여기가 어차피 로그인 성공이 되면 진입되는곳.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();

        System.out.println(JwtProperties.EXPIRATION_TIME);
        System.out.println(JwtProperties.SECRET);


        String jwtAccessToken = JWT.create()
                .withSubject(principalDetailis.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetailis.getUser().getId())
                .withClaim("username", principalDetailis.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        String jwtRefreshToken = JWT.create()
                .withSubject(principalDetailis.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.REFRESH_EXPIRATION_TIME))  //14일로 추가.
                .withClaim("id", principalDetailis.getUser().getId())
                .withClaim("username", principalDetailis.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        JwtDto jwtDto = new JwtDto().builder()
                        .jwtAccessToken(JwtProperties.TOKEN_PREFIX+jwtAccessToken)
                        .jwtRefreshToken(JwtProperties.TOKEN_PREFIX+jwtRefreshToken)
                        .build();

        refreshTokenRepository.save(new RefreshToken(jwtRefreshToken));

        String tokensJson = om.writeValueAsString(jwtDto);

        response.getWriter().write(tokensJson);

        //response.addHeader(JwtProperties.REFRESH_HEADER_STRING, tokensJson);        //refresh와 Access 모두 줌. header대신 바디에 줌.
    }

}