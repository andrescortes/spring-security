package com.dev.springsecurityjwt.controller;

import com.dev.springsecurityjwt.domain.entity.Product;
import com.dev.springsecurityjwt.service.impl.ProductServiceDaoImpl;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

    private final ProductServiceDaoImpl serviceDao;

    public ProductController(ProductServiceDaoImpl serviceDao) {
        this.serviceDao = serviceDao;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(serviceDao.getProducts(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody final Product product) {
        return new ResponseEntity<>(serviceDao.createProduct(product), HttpStatus.CREATED);
    }
}
