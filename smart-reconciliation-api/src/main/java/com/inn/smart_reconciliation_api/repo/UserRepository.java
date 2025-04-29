package com.inn.smart_reconciliation_api.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.smart_reconciliation_api.entities.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
    
    Optional<Users> findByUsername(String username);
}
