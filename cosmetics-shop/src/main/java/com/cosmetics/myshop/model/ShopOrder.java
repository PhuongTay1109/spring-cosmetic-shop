package com.cosmetics.myshop.model;

import java.sql.Timestamp;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "shop_order")
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "note")
    private String note;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "method")
    private String method;

    @Column(name = "total")
    private Double total;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private Cart cart;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;
    
    

    // Constructors, getters, setters, and additional methods as needed
}

