package com.cgv.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cgv.app.entity.Qna;

public interface QnaRepository extends JpaRepository<Qna, Long> {
	List<Qna> findByLevel(int level);
	List<Qna> findByParno(Long parno);
	List<Qna> findByOrderByParnoDescNoAsc();
	List<Qna> deleteByParno(Long parno);
}
