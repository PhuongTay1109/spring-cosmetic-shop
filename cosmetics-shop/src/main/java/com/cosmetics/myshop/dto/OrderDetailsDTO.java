package com.cosmetics.myshop.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO {
    private int id;
    private Double total;
    private Date createdAt;
    private String name;
    private String address;
    private String phone;
    public String paymentMethod;
}

