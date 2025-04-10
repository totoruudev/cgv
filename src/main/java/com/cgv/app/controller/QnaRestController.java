package com.cgv.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgv.app.entity.Qna;
import com.cgv.app.service.QnaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna/api/")
@CrossOrigin(origins = "*")
public class QnaRestController {

	private final QnaService qnaService;
	
	// Q&A 리스트
	@PostMapping("/list")
	public List<Qna> getAllQnas() {
		return qnaService.getAllQna();
	}
	
	// 질문글 자세히 보기
	// {no}에 해당하는 Q&A 조회
	@GetMapping("/detail/answer/{no}")
	public Qna getQna(@PathVariable Long no) {
		return qnaService.findByQnaNo(no);
	}
	
	// 질문글, 답글 저장	
	@PostMapping("/new")
	public Qna insertQna(@RequestBody Qna qna) {
		return qnaService.saveQna(qna);
	}
	
	// 질문글, 답글 수정
	@GetMapping("/update/{no}")
	public Qna updateQna(@PathVariable("no") Long no,
			@RequestBody Qna qna) {
		qna.setNo(no);
		return qnaService.saveQna(qna);
	}
	
	// 질문'만' 삭제
	@DeleteMapping("/delete/question/{no}")
	public void deleteQuestion(@PathVariable("no") Long no) {
		qnaService.deleteQna(no);
	}
		
	// 답글 삭제
	@DeleteMapping("/delete/answer/{parno}")
	public void deleteAnswer(@PathVariable("parno") Long parno, @RequestBody Qna qna) {
		qnaService.deleteQna(parno);
	}
	
	@DeleteMapping("/delete/{no}")
	public void deleteQna(@PathVariable("no") Long no) {
		qnaService.deleteQna(no);
	}
	
	
}
