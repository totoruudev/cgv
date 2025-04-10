package com.cgv.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cgv.app.entity.Product;
import com.cgv.app.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {
	
	private final ProductService productService;
	
	// 상품 목록
	@GetMapping("/list")
	public String getAllList(Model model) {
		model.addAttribute("products", productService.findAll());
		return "product/list";
	}

	// 1건의 상품 조회
	@GetMapping("/detail/{no}")
	public String getProduct(@PathVariable("no") Long no, Model model) {
		model.addAttribute("product", productService.findById(no));
		return "product/detail";
	}
	
	// 상품 등록 폼 로딩
	@GetMapping("/ins")
	public String productForm(Model model) {
		model.addAttribute("product", new Product());
		return "product/form";
	}
	
	// 상품 폼 작성 후 처리
	@PostMapping
	public String insProduct(@ModelAttribute Product product,
			@RequestParam("pic1") MultipartFile pic1,
			@RequestParam("pic2") MultipartFile pic2)throws IOException{
		String uploadPath = "src/main/resources/static/images";
	
		if(!pic1.isEmpty()) { //img1 = d:\kim\pro02\images\pd001.jpg => xacxacb_pd001.jpg
			String fileName = UUID.randomUUID() + "_" + pic1.getOriginalFilename();
			pic1.transferTo(new File(uploadPath+fileName));
			product.setImg1("./images/"+fileName);
		}
		
		if(!pic2.isEmpty()) { //img2 = d:\kim\pro02\images\pd001_1.jpg => xacxacb_pd001_1.jpg
			String fileName = UUID.randomUUID() + "_" + pic2.getOriginalFilename();
			pic2.transferTo(new File(uploadPath+fileName));
			product.setImg2("./images/"+fileName);
		}
		
		productService.save(product);
		return "redirect:/product/list";
	}
	
	@GetMapping("/edit/{no}")	//상품 정보 수정 폼 로딩
	public String editForm(@PathVariable("no") Long no, Model model) {
		model.addAttribute("product", productService.findById(no));
		return "product/edit";
	}
	
	@PostMapping("/update")	//상품 정보 수정 처리
	public String update(@ModelAttribute Product product, 
			@RequestParam("pic1") MultipartFile pic1, 
			@RequestParam("pic2") MultipartFile pic2) throws IOException {
		insProduct(product, pic1, pic2);
		return "redirect:/product/list";
	}
	
	
}
