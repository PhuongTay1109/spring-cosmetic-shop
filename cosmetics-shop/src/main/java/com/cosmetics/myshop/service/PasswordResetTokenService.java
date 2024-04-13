package com.cosmetics.myshop.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import com.cosmetics.myshop.model.PasswordResetToken;
import com.cosmetics.myshop.model.User;

import jakarta.mail.MessagingException;

public interface PasswordResetTokenService {
	public void deleteTokenBy(PasswordResetToken passwordResetToken);
	public Optional<PasswordResetToken> findByToken(String token);
	public Optional<PasswordResetToken> findByUser(User user);
	public PasswordResetToken createPasswordResetToken(User user);
	public boolean validatePasswordResetToken(String token);
	public void sendResetEmail(String recipientEmail, String token) throws UnsupportedEncodingException, MessagingException;
}
