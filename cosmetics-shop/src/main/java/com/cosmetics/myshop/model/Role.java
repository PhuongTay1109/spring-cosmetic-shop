package com.cosmetics.myshop.model;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5432509889530429733L;

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "authority")
	private String authority;
	
	public Role() {
		super();
	}
	
	public Role(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return authority;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
