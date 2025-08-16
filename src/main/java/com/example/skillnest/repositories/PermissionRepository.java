package com.example.skillnest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.skillnest.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
