package com.pick.movie.back.config;

import com.pick.movie.back.repository.TagRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  //JPA Auditing 활성화
public class JpaConfig {

}
