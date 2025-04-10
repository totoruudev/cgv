package com.cgv.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cgv.app.entity.Member;
import com.cgv.app.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor // final 필드를 자동으로 생성자 주입(memberService 자동 주입됨)
@RequestMapping("/api/member")
@CrossOrigin(origins = "*") //CORS 허용(다른 도메인에서 이 API를 호출해도 차단 되지 않음)

// CORS란? Cross-Origin Resource Sharing
// 다른 출처(도메인, 포트, 프로토콜)에서 자원을 공유할 수 있도록 허용하는 웹 브라우저 보안 정책
public class MemberRestController {
	public final MemberService memberService;
	
	@PostMapping("/join")
	public Member joinMember(@RequestBody Member member) {
		return memberService.join(member);
	}
	
	@PostMapping("/login")
	public boolean login(@RequestParam("id") String id, @RequestParam("pw") String Pw) {
		Member member = memberService.login(id, Pw);
		boolean ps = false;
		if(member!=null) {
			ps = true;
		}
		return ps;
	}
	
	// 회원 번호로 회원 조회
	@GetMapping("/detail/{no}")
	public Member getMemberByNo(@PathVariable("no") Long no) {
		return memberService.getMemberByNo(no);
	}
	
	// ID로 회원 조회
	@GetMapping("/id/{id}")
	public Member getMemberByid(@PathVariable("id") String id) {
		return memberService.getMember(id);
	}
	
	// 회원 목록 조회
	@GetMapping("/list")
	public List<Member> getAllMembers() {
		return memberService.getMemberList();
	}
	
	// 업데이트 된 회원 정보를 DB로 전송
	@PutMapping("/update")
	public Member updateMember(@RequestBody Member member) {
		return memberService.update(member);
	}
	
	// 회원 번호로 회원을 조회하고 해당 회원 삭제
	@DeleteMapping("/delete/{no}")
	public void deleteMember(@PathVariable("no") Long no) {
		memberService.delete(no);
	}
	
	// ID 중복 조회
	@GetMapping("/checkId")
	public boolean checkDuplicateId(@RequestParam("id") String id) {
		return memberService.idCheck(id);
	}
}
