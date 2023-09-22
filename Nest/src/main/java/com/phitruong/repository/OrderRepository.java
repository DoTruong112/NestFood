package com.phitruong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phitruong.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
