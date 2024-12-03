package br.com.estoque.backend.repository;

import br.com.estoque.backend.entities.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {

    Product findByCode(Integer code);

    Page<Product> findAll(Pageable pageable);
}
