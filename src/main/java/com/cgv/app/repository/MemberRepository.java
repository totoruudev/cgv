package com.cgv.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cgv.app.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	// 찾을 경우 find
	// 존재 유무는 exists를 접두어로 사용
	Optional<Member> findById(String id); // 로그인 체크용: Select * from member where id=?;
	boolean existsById(String id);	// 중복체크용 boolean Select * from member where id=?;
	Optional<Member> findByNo(long no);
}
