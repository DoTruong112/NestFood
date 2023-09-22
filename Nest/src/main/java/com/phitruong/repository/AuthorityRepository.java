package com.phitruong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phitruong.entity.Account;
import com.phitruong.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	void deleteByAccount(Account account);
}
