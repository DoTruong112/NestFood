package com.phitruong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orderdetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
	 @Id
	 @Column(name="id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer id;
	    
	 @ManyToOne
	 @JoinColumn(name = "orderid", referencedColumnName = "id")
	 private Order order;
	    
	 @ManyToOne
	 @JoinColumn(name = "productid", referencedColumnName = "id")
	 private Product product;
	    
	 @Column(nullable = false)
	 private int quantity;
	    
	 @Column(nullable = false)
	 private double price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	 
	 
}
