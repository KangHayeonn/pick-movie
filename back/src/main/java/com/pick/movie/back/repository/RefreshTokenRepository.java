package com.pick.movie.back.repository;

import com.pick.movie.back.model.RefreshToken;
import com.pick.movie.back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    User findByRefreshToken(String refreshToken);
}
