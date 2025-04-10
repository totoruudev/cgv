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

public class Qna {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;
	
	// 1. 질문
	// 2. 답변
	private int level;
	
	// 질문글이 0이면 해당 답글도 0
	private	Long parno;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@Column(nullable = false, length = 50)
	private String author;
	
	@Builder.Default
	private LocalDateTime resdate = LocalDateTime.now();
	
	@Builder.Default
	private int hits = 0;
	
}
