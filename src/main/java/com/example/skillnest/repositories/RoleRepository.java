package com.example.skillnest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.skillnest.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
