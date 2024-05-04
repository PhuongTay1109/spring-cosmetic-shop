package com.cosmetics.myshop.configuration;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class VNPayConfig {

	public static String vnp_TmnCode = "B408DUT4";
	public static String vnp_HashSecret = "PYBFIPHNTLCNZOYXNRKSQNUONIDFBVVB";

	public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
	public static String vnp_ReturnBuyNowUrl = "http://localhost:8080/vnpayment/buynow/transaction";
	public static String vnp_ReturnCartUrl = "http://localhost:8080/vnpayment/cart/transaction";
	public static String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

	public static String Sha256(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(message.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();
		} catch (UnsupportedEncodingException ex) {
			digest = "";
		} catch (NoSuchAlgorithmException ex) {
			digest = "";
		}
		return digest;
	}

	public static String hmacSHA512(final String key, final String data) {
		try {
			if (key == null || data == null) 
				throw new NullPointerException();

			final Mac hmac512 = Mac.getInstance("HmacSHA512");
			byte[] hmacKeyBytes = key.getBytes();
			
			final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
			hmac512.init(secretKey);
			
			byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
			byte[] result = hmac512.doFinal(dataBytes);
			
			StringBuilder sb = new StringBuilder(2 * result.length);
		
			for (byte b : result) {
				sb.append(String.format("%02x", b & 0xff));
			}
			
			return sb.toString();
		} catch (Exception ex) {
			return "";
		}
	}
	
	public static String getRandomNumber(int len) {
		Random rnd = new Random();
		String chars = "0123456789";
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}
}
