package com.example.Product_Service.payload;

import lombok.Data;

@Data
public class ClientDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    // Getters and Setters
}