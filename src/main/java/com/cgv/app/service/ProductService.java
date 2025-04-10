package com.cgv.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cgv.app.entity.Product;
import com.cgv.app.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	// 상품 목록
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	// 상품 자세히보기
	public Product findById(Long no) {
		return productRepository.findById(no).orElse(null);
	}
	
	// 상품 저장
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	// 상품 수정
	public Product update(Product product) {
		return productRepository.save(product);
	}
	
	// 상품 삭제
	public void delete(Long no) {
		productRepository.deleteById(no);
	}
	
	
}
