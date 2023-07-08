package com.phitruong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phitruong.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

}
