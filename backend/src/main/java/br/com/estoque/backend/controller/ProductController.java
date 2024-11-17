package br.com.estoque.backend.controller;

import br.com.estoque.backend.dtos.NewProductDto;
import br.com.estoque.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth/product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody NewProductDto newProductDto){
        String response = productService.createProduct(newProductDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
