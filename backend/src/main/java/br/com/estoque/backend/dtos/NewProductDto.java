package br.com.estoque.backend.dtos;

import java.math.BigDecimal;

public record NewProductDto(Integer code, String name, BigDecimal price,Integer quantity) {
}
