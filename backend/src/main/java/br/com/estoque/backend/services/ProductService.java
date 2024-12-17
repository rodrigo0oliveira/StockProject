package br.com.estoque.backend.services;

import br.com.estoque.backend.dtos.EditProductDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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

        verifyIfCodeExist(newProductDto.code(),user.getId());

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

        ProductResponseDto responseDto = ProductToDto(product);
        sseService.sendEvents("Create_Product",responseDto);
    }

    public List<ProductResponseDto> findAll(int page,int itens){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = authService.getUser(authentication);

        Page<Product> productList = productRepository.findAllProductsToUsers(user.getId(),PageRequest.of(page,itens));
        return ListProductToDto(productList);
    }

    public void verifyIfCodeExist(Integer code,String user_id){
        if(findByCode(code,user_id)!=null){
            throw new IllegalArgumentException("Codigo informado ja existe");
        }
    }

    public Product findByCode(Integer code,String user_id){
        return productRepository.findProductByCodeToUsers(code,user_id).orElse(null);
    }

    private ProductResponseDto ProductToDto(Product product){
        return new ProductResponseDto(product.getCode(),product.getName()
        ,product.getPrice(),product.getQuantity(),product.getCategory().getName(),product.getStatus().getName());
    }

    private List<ProductResponseDto> ListProductToDto(Page<Product> productList){
        return productList.stream().map(p->
                        new ProductResponseDto(p.getCode(),p.getName(),
                p.getPrice(),p.getQuantity(),p.getCategory().getName(),p.getStatus().getName()))
                .collect(Collectors.toList());
    }

    public ProductResponseDto editProduct(Integer code,EditProductDto editProductDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = authService.getUser(authentication);

        Product product = findByCode(code,user.getId());
        log.info(product.getName());

        Category category = categoryService.findCategory(editProductDto.name().toUpperCase());
        if(product!=null){
            product.setName(editProductDto.name());
            product.setPrice(editProductDto.price());
            product.setQuantity(editProductDto.quantity());
            product.setCategory(category);

            product = productRepository.save(product);

            return new ProductResponseDto(product.getCode(),product.getName(),
                    product.getPrice(),product.getQuantity(),product.getCategory().getName(),product.getStatus().getName());
        }
        return null;
    }
}
