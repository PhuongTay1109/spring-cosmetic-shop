package com.cosmetics.myshop.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import com.cosmetics.myshop.model.PasswordResetToken;
import com.cosmetics.myshop.model.User;

import jakarta.mail.MessagingException;

public interface PasswordResetTokenService {
	public Optional<PasswordResetToken> findByToken(String token);
	public PasswordResetToken createPasswordResetToken(User user);
	public boolean validatePasswordResetToken(String token);
	public void resetUserPassword(String token, String newPassword);
	public void sendResetEmail(String recipientEmail, String token) throws UnsupportedEncodingException, MessagingException;
}
