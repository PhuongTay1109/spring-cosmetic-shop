package com.cosmetics.myshop.utils;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class RSAKeyProperties {
	
	public RSAKeyProperties() {
		KeyPair pair = KeyGenerator.generateRSAKey();
		this.publicKey = (RSAPublicKey)pair.getPublic();
		this.privateKey = (RSAPrivateKey)pair.getPrivate();
	}
	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;
	
}
