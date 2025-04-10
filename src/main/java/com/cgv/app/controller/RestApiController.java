package com.cgv.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgv.app.entity.Sample;
import com.cgv.app.service.SampleService;

// @RestController는 대답을 json으로 함
@RestController
@RequestMapping("/api")
public class RestApiController {

	// SampleController.java 내용 복붙 하기
	private final SampleService sampleService;

	public RestApiController(SampleService sampleService) { // 생성자 주입
		this.sampleService = sampleService; // 생성자 주입
	}

	// localhost:8081/api/list
	@GetMapping("/list")
	public ResponseEntity<List<Sample>> getAllSamples() {
		List<Sample> samples = sampleService.getAllSamples();
		return ResponseEntity.ok(samples);
	}

	// 샘플 자세히보기
	// localhost:8081/api/detail/1
	// json의 형태로 Sample 데이터를 넘김
	@GetMapping("/detail/{no}")
	public ResponseEntity<Sample> getSample(@PathVariable("no") Long no) {
		Sample sample = sampleService.getSampleByNo(no); // Optional로 받으면 ByNo로 처리
		return ResponseEntity.ok(sample);
	}

	// 샘플 추가(저장)
	// localhost:8081/api/save
	@PostMapping("/save")
	public ResponseEntity<Sample> saveSample(@RequestBody Sample sample) {
		return ResponseEntity.ok(sampleService.saveSample(sample));
	}
	
	// 샘플 수정
	@PutMapping("/edit/{no}")
	public String editForm(@PathVariable("no") Long no, Model model) {
		model.addAttribute("sample", sampleService.getSampleByNo(no));
		return "sample-edit";
	}


	// 샘플 삭제
	@DeleteMapping("/delete/{no}")
	public ResponseEntity<Void> deleteSample(@PathVariable("no") Long no) {
		sampleService.deleteSample(no);
		return ResponseEntity.noContent().build();
	}
}
