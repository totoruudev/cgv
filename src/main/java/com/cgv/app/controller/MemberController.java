package com.cgv.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cgv.app.entity.Member;
import com.cgv.app.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;
	
	// 회원가입 페이지
	// member라는 빈 객체 생성 후 정보(속성)이 입력 되면 model에 addAttribute(속성 추가)
	@GetMapping("/join")
	public String joinForm(Model model) {
		model.addAttribute("member", new Member());
		return "member/join";
	}
	
	// 회원가입 완료 되면 로그인 페이지로 리다이렉트
	@PostMapping("/join")
	public String join(@ModelAttribute Member member) {
		memberService.join(member);
		return "redirect:/member/login";
	}

	// 로그인 페이지
	@GetMapping("/login")
	public String loginForm() {
		return "member/login";
	}

	// Java의 객체
	// 현재 페이지 내에서만 인지 가능한 객체: page(Page)
	// Session: 웹 브라우저(클라이언트)와 서버 사이의 "하나의 연결된 사용자 상태"를 저장하는 수단
	
	// HttpSession: 사용자가 로그인 되어있는 동안 데이터를 보관해두는 저장소(로그아웃 하면 초기화됨)
	// 요청한 곳으로만 보내는 객체: request(HttpServletRequest)
	// 보내지는 곳에서만 알 수 있는 객체: response(HttpServletResponse)
	@PostMapping("/login")
	public String login(@RequestParam("id") String id,
						@RequestParam("pw") String pw, HttpSession session) {
		Member member = memberService.login(id, pw);
		if (member != null) {
			session.setAttribute("loginMember", member); // 로그인 성공 시 loginMember 저장
			return "redirect:/";
		} else {
			return "redirect:/member/login";
		}
	}
	
	// 로그아웃(HttpSession에서 데이터를 꺼내와 세션 무효화)
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	// loginMember에서 정보를 받아 마이페이지 구현
	@GetMapping("/mypage")
	public String myPage(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("loginMember");
		model.addAttribute("member", member);
		return "member/my-page";
	}

	// 회원정보 수정 폼 실행
	@GetMapping("/edit")
	public String editForm(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("loginMember");
		model.addAttribute("member", member);
		return "member/member-edit";
	}

	// 위의(앞서) 받은 수정된 정보를 업데이트 하고 마이페이지로 리다이렉트
	@PostMapping("/edit")
	public String edit(Member member, HttpSession session) {
		memberService.update(member);
		session.setAttribute("loginMember", member); // 갱신
		return "redirect:/member/mypage";
	}

	// 회원 탈퇴(아래는 처리 순서)
	// 1. Member member = (Member) session.getAttribute("loginMember"); -> 세션에서 사용자 정보를 받고
	// 2. memberService.delete(member.getNo()); -> 해당하는 정보를 DB에서 삭제
	// 3. session.invalidate(); -> 세션 초기화
	@GetMapping("/delete")
	public String delete(HttpSession session) {
		Member member = (Member) session.getAttribute("loginMember");
		memberService.delete(member.getNo());
		session.invalidate();
		return "redirect:/";
	}

	// 회원 목록 조회
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("members", memberService.getMemberList());
		return "member/member-list";
	}

	// AJAX용 아이디 중복 확인
	// @ResponseBody로 JSON으로 응답 받음
	@GetMapping("/checkId")
	@ResponseBody
	public boolean checkDuplicateId(@RequestParam("id") String id) {
		return memberService.idCheck(id); // true이면 이미 존재함
	}
}
