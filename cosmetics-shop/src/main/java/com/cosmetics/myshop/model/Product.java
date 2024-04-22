package com.cosmetics.myshop.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product {
	
	
	public Product(String brand, String name, Double price, String currency, String imageLink, String description,
			Double rating, String productType, String categoryName, String tagList) {
		super();
		this.brand = brand;
		this.name = name;
		this.price = price;
		this.currency = currency;
		this.imageLink = imageLink;
		this.description = description;
		this.rating = rating;
		this.productType = productType;
		this.categoryName = categoryName;
		this.tagList = tagList;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "brand")
	private String brand;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private Double price;

	@Column(name = "currency")
	private String currency;

	@Column(name = "image_link")
	private String imageLink;

	@Column(name = "description",columnDefinition = "TEXT")
	private String description;

	@Column(name = "rating")
	private Double rating;

	@Column(name = "product_type")
	private String productType;
	
	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "tag_list")
	private String tagList;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at")
    private Date modifiedAt;

}
