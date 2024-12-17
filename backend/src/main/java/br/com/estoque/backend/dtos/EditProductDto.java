package br.com.estoque.backend.dtos;

import java.math.BigDecimal;

public record EditProductDto(String name, BigDecimal price,Integer quantity,String category) {
}
