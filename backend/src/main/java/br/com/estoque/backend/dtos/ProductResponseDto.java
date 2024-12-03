package br.com.estoque.backend.dtos;


import java.math.BigDecimal;

public record ProductResponseDto(Integer code, String name, BigDecimal price, Integer quantity
, String category, String status) {
}
