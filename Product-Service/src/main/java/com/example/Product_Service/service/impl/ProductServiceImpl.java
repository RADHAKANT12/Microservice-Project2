package com.example.Product_Service.service.impl;

import com.example.Product_Service.entity.Product;
import com.example.Product_Service.exception.ResourceNotFoundException;
import com.example.Product_Service.payload.ApiResponseDto;
import com.example.Product_Service.payload.ClientDto;
import com.example.Product_Service.payload.ProductDto;
import com.example.Product_Service.repository.ProductRepository;
import com.example.Product_Service.service.ApiClient;
import com.example.Product_Service.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ModelMapper mapper;
    private ProductRepository productRepo;
    private ApiClient apiClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl( ModelMapper mapper,ProductRepository productRepo,ApiClient apiClient){
        this.mapper=mapper;
        this.productRepo=productRepo;
        this.apiClient=apiClient;
    }
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepo.findAll();
        List<ProductDto> dtos = products.stream().map(product -> mapToDto(product)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = mapToEntity(productDto);
        Product product1 = productRepo.save(product);
        ProductDto productDto1 = mapToDto(product1);
        return productDto1;
    }

    private Product mapToEntity(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);

        return product;
    }
    private ProductDto mapToDto(Product product1) {
        ProductDto productDto = mapper.map(product1, ProductDto.class);
        return productDto;
    }



  /*  @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", id)
        );
        return mapToDto(product);
    }*/
 // @CircuitBreaker(name ="PRODUCT-BREAKERS", fallbackMethod = "getDefaultClient")
  @Retry(name ="PRODUCT-BREAKERS", fallbackMethod = "getDefaultClient")

  @Override
  public ApiResponseDto getProductById(Long id) {
      LOGGER.info("inside getProductById() method");

      Product product = productRepo.findById(id).orElseThrow(
              () -> new ResourceNotFoundException("Product", "Id", id)
      );
      ClientDto clientDto = apiClient.getClientById(product.getClientId());
      ProductDto productDto = mapToDto(product);
      ApiResponseDto apiResponseDto= new ApiResponseDto();
      apiResponseDto.setClient(clientDto);
      apiResponseDto.setProduct(productDto);
      return apiResponseDto;

  }
    public ApiResponseDto getDefaultClient(Long id,Exception exception) {
        LOGGER.info("inside getDefaultClient() method");

        Product product = productRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", id)
        );
        ClientDto clientDto = new ClientDto();
        clientDto.setId(0L);
        clientDto.setFirstName("Default");
        clientDto.setLastName("Client");
        clientDto.setEmail("default@client.com");

        ProductDto productDto = mapToDto(product);

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setProduct(productDto);
        apiResponseDto.setClient(clientDto);

        return apiResponseDto;


    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", id)
        );
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        Product product1 = productRepo.save(product);
        return mapToDto(product1);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", id)
        );
        productRepo.delete(product);

    }
}
