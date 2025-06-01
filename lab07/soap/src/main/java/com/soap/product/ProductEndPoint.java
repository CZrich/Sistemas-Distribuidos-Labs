package com.soap.product;


import io.spring.guides.gs_producing_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;


@Endpoint
public class ProductEndPoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private ProductRepository productRepository;

    @Autowired
    public ProductEndPoint(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getCountry(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        response.setProduct(productRepository.findProduct(request.getName()));

        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddProductRequest")
    @ResponsePayload
    public AddProductResponse addProduct(@RequestPayload AddProductRequest request) {
        AddProductResponse response = new AddProductResponse();

        Product newProduct = request.getProduct();
        productRepository.saveProduct(newProduct);

        response.setMessage("Producto agregado exitosamente: " + newProduct.getName());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload   GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        response.getProduct().addAll(productRepository.findAll()); // Debes crear este método en ProductRepository
        return response;
    }



}
