package com.cosmetics.myshop.dto;

import lombok.Data;

@Data
public class RegisterDTO {
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String username;
	private String address;
	private String password;
	private boolean isValid;
	@Override
	public String toString() {
		return "RegisterDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone="
				+ phone + ", username=" + username + ", address=" + address + ", password=" + password + ", isValid="
				+ isValid + "]";
	}
	
	
	

}
