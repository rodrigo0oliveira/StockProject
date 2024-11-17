package br.com.estoque.backend.entities;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_roles")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Roles implements GrantedAuthority{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id")
    private String id;

    @NotNull
    private String name;

    public Roles(String name){
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
	public String toString() {
        return "{\"id\":\"" + id + "\", \"name\":\"" + name + "\"}";
    }
}
