package com.cosmetics.myshop.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
public class MailConfiguration {
	@Autowired
	private Environment env; //Get property from applicaiton.yml

	@Bean
	JavaMailSender javaMailSender() {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost("smtp.gmail.com");
		javaMailSenderImpl.setPort(587);
		System.out.println(env.getProperty("email.username"));
		javaMailSenderImpl.setUsername(env.getProperty("email.username"));
		javaMailSenderImpl.setPassword(env.getProperty("email.password"));
		Properties props = javaMailSenderImpl.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
//	    props.put("mail.debug", "true"); // Enable debug mode for troubleshooting
		return javaMailSenderImpl;
	}
}
