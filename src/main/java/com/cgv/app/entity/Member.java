package com.cgv.app.entity;

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

public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	@Column(unique = true, nullable = false, length = 50)
	private String id;
	
	@Column(nullable = false)
	private String pw;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, length = 16)
	private String tel;
	
	@Column(nullable = false)
	private String email;
}
