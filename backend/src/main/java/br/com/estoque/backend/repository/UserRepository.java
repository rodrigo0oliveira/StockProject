package br.com.estoque.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estoque.backend.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
    Optional<User> findByEmail(String email);
} 
    

