package com.cgv.app.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cgv.app.entity.Member;
import com.cgv.app.repository.MemberRepository;

@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.memberRepository = memberRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	// 회원가입
	public Member join(Member member) {
		member.setPw(bCryptPasswordEncoder.encode(member.getPw()));
		return memberRepository.save(member);
	}

	// 로그인
	public Member login(String id, String pw) {
		return memberRepository.findById(id).filter(m -> bCryptPasswordEncoder.matches(pw, m.getPw())).orElse(null);
	}

	// 마이페이지
	public Member getMember(String id) {
		return memberRepository.findById(id).orElse(null);
	}

	// 회원 번호 검색
	public Member getMemberByNo(Long no) {
		return memberRepository.findByNo(no).orElse(null);
	}

	// 회원 정보 변경
	public Member update(Member member) {
		return memberRepository.save(member);
	}

	// 비밀번호 변경
	public void updatePassword(Member member) {
		member.setPw(bCryptPasswordEncoder.encode(member.getPw()));
		memberRepository.save(member);
	}

	// 회원 탈퇴
	public void delete(Long no) {
		memberRepository.deleteById(no);
	}

	public List<Member> getMemberList() {
		return memberRepository.findAll();
	}

	public boolean idCheck(String id) {
		return memberRepository.existsById(id);
	}
}
