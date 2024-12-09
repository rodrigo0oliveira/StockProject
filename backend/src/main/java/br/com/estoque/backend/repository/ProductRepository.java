package br.com.estoque.backend.repository;

import br.com.estoque.backend.entities.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product,String> {

    Product findByCode(Integer code);

    @Query("select p from Product p where p.user.id = :user_id")
    Page<Product> findAllProductsToUsers(@Param("user_id") String user_id, Pageable pageable);
}
