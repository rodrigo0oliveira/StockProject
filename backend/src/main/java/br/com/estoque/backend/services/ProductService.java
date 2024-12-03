package br.com.estoque.backend.services;

import br.com.estoque.backend.dtos.EntryDto;
import br.com.estoque.backend.dtos.NewProductDto;
import br.com.estoque.backend.dtos.ProductResponseDto;
import br.com.estoque.backend.entities.Category;
import br.com.estoque.backend.entities.Product;
import br.com.estoque.backend.entities.User;
import br.com.estoque.backend.enums.Status;
import br.com.estoque.backend.repository.ProductRepository;
import br.com.estoque.backend.sse.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AuthService authService;
    private final CategoryService categoryService;
    private final EntryService entryService;
    private final SseService sseService;

    public void createProduct(NewProductDto newProductDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = authService.getUser(authentication);

        verifyIfCodeExist(newProductDto.code());

        Category category = categoryService.findCategory(newProductDto.category());

        Product newProduct = Product.builder()
                .id(UUID.randomUUID().toString())
                .name(newProductDto.name())
                .price(newProductDto.price())
                .quantity(newProductDto.quantity())
                .status(Status.AVAILABLE)
                .code(newProductDto.code())
                .category(category)
                .user(user)
                .build();

        Product product = productRepository.save(newProduct);
        entryService.createEntry(new EntryDto(product,product.getQuantity()));

        ProductResponseDto responseDto = new ProductResponseDto(product.getCode(),product.getName(),product.getPrice()
        ,product.getQuantity(),product.getCategory().getName(),product.getStatus().getName());

        sseService.sendEvents("Create_Product",responseDto);
    }

    public void verifyIfCodeExist(Integer code){
        if(findByCode(code)!=null){
            throw new IllegalArgumentException();
        }
    }

    public Product findByCode(Integer code){
        return productRepository.findByCode(code);
    }
}
