package com.example.Product_Service.payload;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private Long clientId;
}
