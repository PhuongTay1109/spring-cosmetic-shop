package com.cosmetics.myshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cosmetics.myshop.model.PasswordResetToken;
import com.cosmetics.myshop.model.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long > {
	Optional<PasswordResetToken> findByToken(String token);
	Optional<PasswordResetToken> findByUser(User user);

}
