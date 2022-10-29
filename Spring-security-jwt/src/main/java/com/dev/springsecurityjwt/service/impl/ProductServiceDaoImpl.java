package com.dev.springsecurityjwt.service.impl;

import com.dev.springsecurityjwt.domain.entity.Product;
import com.dev.springsecurityjwt.repository.ProductRepository;
import com.dev.springsecurityjwt.service.contract.ProductServiceDao;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceDaoImpl implements ProductServiceDao {

    private final ProductRepository productRepository;

    public ProductServiceDaoImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Transactional
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
