package com.phitruong.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "order_date", nullable = false)
    private Date orderDate;
    
    @Column(name = "total_amount", nullable = false)
    private double totalAmount;
    
    @OneToMany(mappedBy = "bill")
    private List<OrderDetail> orderDetails;
}
