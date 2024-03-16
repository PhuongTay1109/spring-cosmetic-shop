package com.cosmetics.myshop.model;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail {
	
	
	@EmbeddedId
	private OrderDetailId id;

	@Column(name = "quantity")
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
	ShopOrder shopOrder;
	
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
	Product product;
	
}

@Embeddable
class OrderDetailId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "order_id")
    private int orderId;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "product_id")
    private int productId;

    // Constructors, getters, setters, and equals/hashCode methods
}