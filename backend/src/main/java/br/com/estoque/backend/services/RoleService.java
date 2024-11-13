package br.com.estoque.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estoque.backend.entities.Roles;
import br.com.estoque.backend.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Roles findByName(String name){
       Roles roles = roleRepository.findByName(name);

       if(roles==null){
            Roles newRole = Roles.builder()
            .name(name)
            .build();

            roleRepository.save(newRole);

            return newRole;
       }

       return roles;
       
    }
    
}
