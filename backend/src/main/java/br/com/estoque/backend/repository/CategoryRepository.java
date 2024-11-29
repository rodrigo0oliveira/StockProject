package br.com.estoque.backend.repository;

import br.com.estoque.backend.entities.Category;
import br.com.estoque.backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {

    Category findByName(String name);
}
