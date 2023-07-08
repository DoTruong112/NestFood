package com.phitruong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phitruong.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

}
