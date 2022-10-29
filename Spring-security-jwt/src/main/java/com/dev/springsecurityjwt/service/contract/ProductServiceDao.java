package com.dev.springsecurityjwt.service.contract;

import com.dev.springsecurityjwt.domain.entity.Product;
import java.util.List;

public interface ProductServiceDao {

    List<Product> getProducts();

    Product createProduct(Product product);
}
