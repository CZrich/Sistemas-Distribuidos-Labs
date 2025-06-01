package com.soap.product;

import io.spring.guides.gs_producing_web_service.Category;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import io.spring.guides.gs_producing_web_service.Product;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductRepository {

    private static final Map<String,Product> products = new HashMap<>();

    @PostConstruct
    public void initData() {

        Product tv = new Product();
        tv.setName("SmartTv");
        tv.setCategory(Category.TECHNOLOGICAL);
        tv.setDescription("La nueva Tv inteligente");
        tv.setPrice(1560.99);
        products.put(tv.getName(),tv);

        Product Sofa = new Product();
        Sofa.setName("Sofa");
        Sofa.setCategory(Category.HOGAR);
        Sofa.setDescription("Comodo sofa");
        Sofa.setPrice(2000);
        products.put(Sofa.getName(),Sofa);

        Product Polo = new Product();
        Polo.setName("Polo");
        Polo.setCategory(Category.OTROS);
        Polo.setDescription("nueva edición de polos");
        Polo.setPrice(100);
        products.put(Polo.getName(),Polo);
    }


    public void saveProduct(Product product) {
        Assert.notNull(product.getName(), "El nombre no puede ser nulo");
        products.put(product.getName(), product);
    }
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Product findProduct(String name) {
        Assert.notNull(name, "no existe el producto");
        return products.get(name);
    }
}
