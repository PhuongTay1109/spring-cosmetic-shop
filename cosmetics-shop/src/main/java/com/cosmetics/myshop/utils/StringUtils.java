package com.cosmetics.myshop.utils;

public class StringUtils {
	public static String normalizeString(String input) {
		String result = input.trim().replaceAll("_", " ");
		return result.toLowerCase();		
	}
}
