package com.cgv.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cgv.app.entity.Member;
import com.cgv.app.entity.Qna;
import com.cgv.app.service.QnaService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
@CrossOrigin(origins = "*")
public class QnaController {

	private final QnaService qnaService;

	// 전체 글 목록
	@GetMapping("/list")
	public String getAllQna(Model model) {
		model.addAttribute("qnas", qnaService.getAllQna());
		return "qna/qna-list";
	}

	// 답글 자세히보기
	@GetMapping("/detail/answer/{no}")
	public String getQna(@PathVariable("no") Long no, Model model, HttpServletRequest req, HttpServletResponse res) {
		
		Cookie[] cookies = req.getCookies(); // 사용자 컴퓨터의 쿠키 선언
		boolean isViewed = false;
		String cookieName = "qna_view_" + no;

		// 쿠키를 순회하여 조회
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					isViewed = true;
					break;
				}
			}
		}

		// 조회 내역이 없을 경우 게시글의 조회수 증가와 사용자 컴퓨터에 쿠키 생성
		if (!isViewed) {
			qnaService.increaseHits(no);
			Cookie newCookie = new Cookie(cookieName, "viewed"); // 쿠키 생성
			newCookie.setMaxAge(60 * 60 * 24); // 24시간
			newCookie.setPath("/");
			res.addCookie(newCookie);
		}

		model.addAttribute("qna", qnaService.findByQnaNo(no));
		model.addAttribute("answers", qnaService.findAnswers(no));
		return "qna-detail";
	}

	// 질문 글 자세히 보기
	@GetMapping("/detail/question/{parno}")
	public String getQuestion(@PathVariable("parno") Long parno, Model model) {
		return "qna/qna-detail";
	}

	// 질문 글 쓰기 폼 로딩
	@GetMapping("/ins/question")
	public String QnaForm(HttpSession session, Model model) {
		Qna qna = new Qna();
		Member member = (Member) session.getAttribute("loginMember");
		qna.setAuthor(member.getId());
		model.addAttribute("qna", qna);
		return "qna/qna-form";
	}

	// 질문 글 쓰기
	@PostMapping("/new/question")
	public String insQuestion(@ModelAttribute Qna qna) {
		qnaService.saveQna(qna);
		return "redirect:/qna/qna-list";
	}

	// 답글 쓰기 폼 로딩
	@GetMapping("/ins/answer/{no}")
	public String answerForm(@PathVariable("no") Long no, HttpSession session, Model model) {
		Qna qna = new Qna();
		Member member = (Member) session.getAttribute("loginMember");
		qna.setParno(no);
		qna.setAuthor(member.getId());
		model.addAttribute("qna", qna);
		return "qna/qna-answer";
	}

	// 답글 쓰기
	@PostMapping("/ins/answer")
	public String insAnswer(@ModelAttribute Qna qna) {
		qna.setLevel(2);
		qnaService.saveQna(qna);
		return "redirect:/qna/qna-list";
	}

	// 질문글, 답글 수정 폼 로딩
	@GetMapping("/edit/{no}")
	public String updateForm(@PathVariable("no") Long no, Model model) {
		model.addAttribute("qna", qnaService.findByQnaNo(no));
		return "qna/qna-edit";
	}

	// 질문글, 답글 수정 처리
	@PostMapping("/update")
	public String update(@ModelAttribute Qna qna) {
		qnaService.updateQna(qna);
		return "redirect:/qna/qna-list";
	}

	// 답글 삭제
	@GetMapping("/delete/{no}")
	public String deleteAnswer(@PathVariable("no") Long no) {
		qnaService.deleteQna(no);
		return "redirect:/qna/qna-list";
	}

	// 질문 삭제 시 해당 답글'도' 삭제
	@GetMapping("/delete/{parno}")
	public String deleteQna(@PathVariable("parno") Long parno) {
		qnaService.deleteQna(parno);
		return "redirect:/qna/qna-list";
	}
}
