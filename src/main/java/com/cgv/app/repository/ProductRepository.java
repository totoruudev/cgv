package com.cgv.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cgv.app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
