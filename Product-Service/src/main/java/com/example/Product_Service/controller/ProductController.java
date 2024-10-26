package com.example.Product_Service.controller;


import com.example.Product_Service.entity.Product;
import com.example.Product_Service.payload.ApiResponseDto;
import com.example.Product_Service.payload.ProductDto;
import com.example.Product_Service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService=productService;
    }
    // http://localhost:8080/api/products
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto dto = productService.createProduct(productDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    // http://localhost:8080/api/products

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    // http://localhost:8080/api/products/1


  /*  @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto dto = productService.getProductById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }*/
    // http://localhost:8080/api/products/1

    @GetMapping("/{id}")
  public ResponseEntity<ApiResponseDto> getProductById(@PathVariable Long id) {
      ApiResponseDto responseDto = productService.getProductById(id);
      return new ResponseEntity<>(responseDto,HttpStatus.OK);
  }

    // http://localhost:8080/api/products/update/1

    @PutMapping("update/{id}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable Long id,@RequestBody ProductDto productDto) {
        ProductDto dto = productService.updateProduct(id,productDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    // http://localhost:8080/api/products/delete/1

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<String>("Product entity deleted!!!",HttpStatus.OK);
    }
}

