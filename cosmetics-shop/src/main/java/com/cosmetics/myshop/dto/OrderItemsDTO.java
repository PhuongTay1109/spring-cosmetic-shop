package com.cosmetics.myshop.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDTO {
	private Integer productId;
	private String name;
	private Double price;
	private String imageLink;
	private String description;
	private String tagList;
	private String categoryName;
    private int quantity;
    private Double total;
    private Date createdAt;
    private Date modifiedAt;
}
