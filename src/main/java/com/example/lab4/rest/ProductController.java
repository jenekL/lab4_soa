package com.example.lab4.rest;

import com.example.lab4.model.Product;
import com.example.lab4.repository.ProductRepository;
import com.example.lab4.rest.dto.ProductRequest;
import com.example.lab4.rest.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("all")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(mapToResponseList(productRepository.getAllProducts()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(mapToResponse(productRepository.findProductById(id)));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        Product product = new Product(
                productRequest.getName(),
                productRequest.getExtension(),
                productRequest.getPrice(),
                productRequest.getSellNumber());
        return ResponseEntity.ok(mapToResponse(productRepository.save(product)));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @RequestBody ProductRequest productRequest){
        Product product = new Product(
                productRequest.getName(),
                productRequest.getExtension(),
                productRequest.getPrice(),
                productRequest.getSellNumber());
        return ResponseEntity.ok(mapToResponse(productRepository.update(id, product)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    private List<ProductResponse> mapToResponseList(List<Product> products) {
        return products.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setExtension(product.getExtension());
        productResponse.setPrice(product.getPrice());
        productResponse.setName(product.getName());
        productResponse.setSellNumber(product.getSellNumber());
        return productResponse;
    }
}
