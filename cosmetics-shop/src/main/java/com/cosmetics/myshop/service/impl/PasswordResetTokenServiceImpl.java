package com.cosmetics.myshop.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.PasswordResetToken;
import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.repository.PasswordResetTokenRepository;
import com.cosmetics.myshop.service.PasswordResetTokenService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	public PasswordResetToken createPasswordResetToken(User user) {
		// TODO Auto-generated method stub
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setToken(generateToken());
		passwordResetToken.setExpiryDate(calculateExpiryDate());
		passwordResetToken.setUser(user);
		return passwordResetTokenRepository.save(passwordResetToken);
	}

	private String generateToken() {
		return UUID.randomUUID().toString();
	}

	private Date calculateExpiryDate() {
		long currentTimeMillis = System.currentTimeMillis();
		return new Date(currentTimeMillis + PasswordResetToken.EXPIRATION);
	}

	@Override
	public boolean validatePasswordResetToken(String token) {
		Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(token);
		if (isTokenExpired(passwordResetToken.get())) {
			passwordResetTokenRepository.delete(passwordResetToken.get());
		}
		return passwordResetToken.isPresent() && !isTokenExpired(passwordResetToken.get());
	}

	@Override
	public void resetUserPassword(String token, String newPassword) {
		// TODO Auto-generated method stub

	}

	private boolean isTokenExpired(PasswordResetToken token) {
		Date expiryDate = token.getExpiryDate();
		return expiryDate != null && expiryDate.before( new java.util.Date());
	}

	@Override
	public void sendResetEmail(String recipientEmail, String token) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@shopme.com", "Cosmetics Support");
		helper.setTo(recipientEmail);

		String subject = "Here's the link to reset your password";
		String link = "http://localhost:8080/reset_password?token=" + token;

		String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + link
				+ "\">Change my password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password, "
				+ "or you have not made the request.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);
		javaMailSender.send(message);
	}

	@Override
	public Optional<PasswordResetToken> findByToken(String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

}
