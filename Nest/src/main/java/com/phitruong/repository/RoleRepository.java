package com.phitruong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phitruong.entity.Role;


public interface RoleRepository extends JpaRepository<Role, String>{
	Role findByName(String name);
}
