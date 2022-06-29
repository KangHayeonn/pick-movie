package com.pick.movie.back.config;

import com.pick.movie.back.config.jwt.JwtAuthenticationFilter;
import com.pick.movie.back.config.jwt.JwtAuthorizationFilter;
import com.pick.movie.back.repository.RefreshTokenRepository;
import com.pick.movie.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter{



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CorsConfig corsConfig;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(),refreshTokenRepository))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                .authorizeRequests()
                .antMatchers("/api/v1/reissueAccessToken")
                .permitAll()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
