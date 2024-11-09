package br.com.estoque.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estoque.backend.entities.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles,String>{

    Roles findByName(String name);
    
}
