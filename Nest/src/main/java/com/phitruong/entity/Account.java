package com.phitruong.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="username",nullable = false)
    private String username;
    
    @Column(name="password",nullable = false)
    private String password;
    
    @Column(name="email",nullable = false)
    private String email;
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(name="address",nullable = false)
    private String address;
    
    @Column(name="avatar")
    private String avatar;
    
    @OneToMany(mappedBy = "account")
    private List<Bill> bills;
}
