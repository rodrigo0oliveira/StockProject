package br.com.estoque.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "tb_category")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category(String id,String name){
        this.id = id;
        this.name = name;
    }
}
