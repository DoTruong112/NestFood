package com.phitruong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phitruong.entity.Role;
import com.phitruong.repository.RoleRepository;

@Service
public class RolesService {

	private final RoleRepository rolesRepository;
    
    @Autowired
    public RolesService(RoleRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public List<Role> getAllRoles() {
        return rolesRepository.findAll();
    }

    public Role getRoleById(String id) {
        return rolesRepository.findById(id).orElse(null);
    }
}
