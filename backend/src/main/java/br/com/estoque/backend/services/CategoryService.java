package br.com.estoque.backend.services;

import br.com.estoque.backend.entities.Category;
import br.com.estoque.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findCategory(String name){
        Category category = categoryRepository.findByName(name);
        if(category!=null){
            return category;
        }
        Category newCategory = Category.builder()
                .id(UUID.randomUUID().toString())
                .name(name.toUpperCase()).build();

        return categoryRepository.save(newCategory);
    }
}
