package com.pick.movie.back.repository;

import com.pick.movie.back.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByRefreshToken(String refreshToken);
}
