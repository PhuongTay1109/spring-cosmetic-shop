package com.cosmetics.myshop.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cart")
public class Cart {
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User user;
}
