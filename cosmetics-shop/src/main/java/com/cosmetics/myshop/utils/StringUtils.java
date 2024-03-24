package com.cosmetics.myshop.utils;

public class StringUtils {
	
	public StringUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String normalizeString(String input) {
		String result = input.trim().replaceAll("_", " ");
		String[] words = result.split("\\s+");
		result = "";
		for (String word: words) {
			String firstLetter = word.substring(0,1).toUpperCase();
			result +=firstLetter;
			String restOfWord = word.substring(1);
			result +=restOfWord + " ";
			
		}
		System.out.println(result);
		return result;		
	}
}
