package com.example.Product_Service.service;



import com.example.Product_Service.payload.ApiResponseDto;
import com.example.Product_Service.payload.ProductDto;
import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto createProduct(ProductDto productDto);

   // ProductDto getProductById(Long id);
    ApiResponseDto getProductById(Long id);

    ProductDto updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);
}

