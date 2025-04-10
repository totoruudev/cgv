package com.cgv.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cgv.app.entity.Sample;
import com.cgv.app.service.SampleService;

@Controller
@RequestMapping
public class SampleController {
	// 빌더의 빌드 순서는 아래(처리 순서)와 같다
	// 처리 순서: Entity => Repository => Service => Controller
	private final SampleService sampleService;

	public SampleController(SampleService sampleService) { // 생성자 주입
		this.sampleService = sampleService; // 생성자 주입
	}

	// @GetMapping, PostMapping, PutMapping, DeleteMapping, PatchMapping => 한꺼번에 쓸 수
	// 애너테이션(@RequestMapping)
	// Model(모델): 전달 저장소 클래스
	// Model.addAttribute("저장소명", 저장할값또는식)
	// html에서 a태그를 이용한 데이터는 GetMapping을 이용
	// sql에서 insert문을 이용하는 것들은 PostMapping을 이용
	@GetMapping("/list")
	public String getAllSamples(Model model) {
		model.addAttribute("sample", sampleService.getAllSamples()); // sampleService.getAllSamples가 "sample"에 저장됨
		return "sample-list";
	}

	// @PathVariable 경로 상에 매개변수가 있는 경우 사용
	@GetMapping("/detail/{no}")
	public String getSampleById(@PathVariable("no") Long no, Model model) {
		model.addAttribute("sample", sampleService.getSampleByID(no));
		return "sample-detail";
	}

	// localhost:8081/sample/ins
	@GetMapping("/ins")
	public String newSample(Model model) {
		model.addAttribute("sample", new Sample());
		return "sample-form";
	}

	// RestApiController의 샘플 자세히보기
	// @ModelAttribute: 객체로 받을 경우 활용되는 애너테이션
	@PostMapping("/save")
	public String getInsForm(@ModelAttribute Sample sample) {
		sampleService.saveSample(sample);
		return "redirection:/sample/list";
	}

	// 샘플 수정
	// localhost:8081/api/edit/1
	// @PatchMapping도 기능을 갖고 있으나 거의 쓰지 않음
	@GetMapping("/edit/{no}")
	public String editForm(@PathVariable("no") Long no, Model model) {
		model.addAttribute("sample", sampleService.getSampleByNo(no));
		return "sample-edit";
	}

	@PostMapping("/update/{no}")
	public String updateSample(@PathVariable("no") Long no, @ModelAttribute Sample sample) {
		sample.setNo(no);
		sampleService.updateSample(sample);
		return "redirect:/sample/list";
	}

	@GetMapping("/delete/{no}")
	public String deleteSample(@PathVariable("no") Long no) {
		sampleService.deleteSample(no);
		return "redirect:/sample/list";
	}
}
