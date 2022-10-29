package com.dev.springsecurityjwt.repository;

import com.dev.springsecurityjwt.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
