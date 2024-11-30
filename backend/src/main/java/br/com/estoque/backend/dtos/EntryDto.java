package br.com.estoque.backend.dtos;

import br.com.estoque.backend.entities.Product;

public record EntryDto(Product product,Integer quantity) {
}
