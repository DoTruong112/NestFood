package com.phitruong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phitruong.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

}
