package br.com.estoque.backend.controller;

import br.com.estoque.backend.dtos.NewProductDto;
import br.com.estoque.backend.dtos.ProductResponseDto;
import br.com.estoque.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/auth/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @CrossOrigin(origins = "https://localhost", allowCredentials = "true")
    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody NewProductDto newProductDto) throws Exception {
        productService.createProduct(newProductDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "https://localhost", allowCredentials = "true")
    @GetMapping("/findAll")
    public ResponseEntity<List<ProductResponseDto>> findAll(@RequestParam int page,@RequestParam int itens){
        return  new ResponseEntity<>(productService.findAll(page,itens),(HttpStatus.OK));
    }
}
