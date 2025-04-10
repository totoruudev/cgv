package com.cgv.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	@Column(nullable = false, length = 100)
	private String cate;

	@Column(nullable = false, length = 200)
	private String pname;
	
	@Column(columnDefinition = "TEXT")
	private String comment;
	
	@Builder.Default
	private int price1 = 1000;
	
	@Builder.Default
	private int price2 = 1200;
	
	@Builder.Default
	private int amount = 1;
	
	@Builder.Default
	private String img1 = "/images/.jpg";
	
	@Builder.Default
	private String img2 = "/images/.jpg";
	
	@Builder.Default
	private LocalDateTime resdate = LocalDateTime.now();
}
