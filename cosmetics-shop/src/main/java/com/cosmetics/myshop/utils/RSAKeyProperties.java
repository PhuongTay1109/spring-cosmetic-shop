package com.cosmetics.myshop.utils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import lombok.Data;

@Data
public class RSAKeyProperties {
	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;
	
}
