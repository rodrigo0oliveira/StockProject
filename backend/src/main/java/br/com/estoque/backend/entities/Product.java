package br.com.estoque.backend.entities;

import br.com.estoque.backend.enums.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@Table(name = "tb_products")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private BigDecimal price;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(unique = true)
    private Integer code;
}
