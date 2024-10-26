package com.example.Product_Service.service;

import com.example.Product_Service.payload.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//@FeignClient(name = "CLIENT-SERVICE", url = "http://clientms:8081")
@FeignClient(name = "CLIENT-SERVICE", url = "${client-service.url}")
//@FeignClient(name = "CLIENT-SERVICE", url = "http://localhost:8081")


public interface ApiClient {
    @GetMapping("/api/clients/{id}")
    ClientDto getClientById(@PathVariable Long id);
}
