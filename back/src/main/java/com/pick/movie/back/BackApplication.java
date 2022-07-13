package com.pick.movie.back;

import com.pick.movie.back.movieparser.GetMovieInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BackApplication {


	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}



	public static void main(String[] args) throws Exception {
		SpringApplication.run(BackApplication.class, args);
	}

}
