package com.phitruong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phitruong.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer>{

}
