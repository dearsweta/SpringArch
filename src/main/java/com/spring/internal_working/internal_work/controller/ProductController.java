package com.spring.internal_working.internal_work.controller;

import com.spring.internal_working.internal_work.dtos.ProductDto;
import com.spring.internal_working.internal_work.entities.Product;
import com.spring.internal_working.internal_work.mappers.ProductMapper;
import com.spring.internal_working.internal_work.repositories.CategoryRepository;
import com.spring.internal_working.internal_work.repositories.ProductRepository;
import com.spring.internal_working.internal_work.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class   ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping()
    public Iterable<ProductDto> getAllProducts(
           @RequestParam(required = false,name = "categoryId") Byte categoryId
    ) {
        List<Product> products;
        if(categoryId != null) {
             products = productRepository.findProductByCategory_Id(categoryId);
        }
        else
             products = productRepository.findAll();

        return products
                .stream()
                .map(productMapper :: toDto)
                .toList();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {

        var product = productRepository.findById(productId).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(productMapper.toDto(product));


    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct( @RequestBody ProductDto productDto,
    UriComponentsBuilder uriBuilder) {

        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category == null){
            return ResponseEntity.badRequest().build();
        }

        var product = productMapper.toEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);

        productDto.setId(product.getId());
        var uri = uriBuilder.path("/products/{productId}").buildAndExpand(productDto.getId()).toUri();



        return ResponseEntity.created(uri).body(productDto);

    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(name = "productId") Long productId, @RequestBody ProductDto productDto) {

        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        var product = productRepository.findById(productId).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        if(category == null){
            return ResponseEntity.badRequest().build();
        }

        productMapper.toDto(product,productDto);
        product.setCategory(category );
        productRepository.save(product);
        productDto.setId(product.getId());
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "productId") Long productId) {
        var product = productRepository.findById(productId).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }

        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }


}
