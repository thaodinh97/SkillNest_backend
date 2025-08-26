package com.example.skillnest.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.skillnest.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    @Query(
            value = """
            select u.* from users u
            join users_roles ur on u.id = ur.user_id
            join role r on r.name = ur.roles_name
            where r.name = :roleName
            """, nativeQuery = true
    )
    List<User> findUsersByRoleName(@Param("roleName") String roleName);
}
