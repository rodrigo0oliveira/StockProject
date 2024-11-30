package br.com.estoque.backend.services;

import br.com.estoque.backend.dtos.EntryDto;
import br.com.estoque.backend.dtos.NewProductDto;
import br.com.estoque.backend.entities.Category;
import br.com.estoque.backend.entities.Product;
import br.com.estoque.backend.entities.User;
import br.com.estoque.backend.enums.Status;
import br.com.estoque.backend.repository.ProductRepository;
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

    public String createProduct(NewProductDto newProductDto) throws Exception {
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

        newProduct = productRepository.save(newProduct);
        entryService.createEntry(new EntryDto(newProduct,newProduct.getQuantity()));

        return "Produdo criado com sucesso";
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
