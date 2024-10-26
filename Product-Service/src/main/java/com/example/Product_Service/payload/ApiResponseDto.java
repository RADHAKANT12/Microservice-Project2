package com.example.Product_Service.payload;

import lombok.Data;

@Data
public class ApiResponseDto {
    private ProductDto product;
    private ClientDto client;
}
