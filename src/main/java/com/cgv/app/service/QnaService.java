package com.cgv.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cgv.app.entity.Qna;
import com.cgv.app.repository.QnaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaService {

	private final QnaRepository qnaRepository;

	// Q&A 리스트
	public List<Qna> getAllQna() {
		return qnaRepository.findByOrderByParnoDescNoAsc();
	}

	// 답글 자세히 보기
	public Qna findByQnaNo(Long no) {
		return qnaRepository.findById(no).orElse(null);
	}

	// 질문글, 답글 저장
	public Qna saveQna(Qna qna) {
		Qna q = qnaRepository.save(qna);
		if (q.getLevel() == 1) {
			q.setParno(q.getNo());
		}
		return qnaRepository.save(qna);
	}

	// 질문,답글 수정
	public Qna updateQna(Qna qna) {
		return qnaRepository.save(qna);
	}

	// 답글만 삭제
	public void deleteQna(Long no) {
		qnaRepository.deleteById(no);
	}

	// 질문 삭제 시 답글도 같이 삭제
	public void deleteByParno(Long parno) {
		qnaRepository.deleteByParno(parno);
	}

	// 질문 자세히 보기 + 답글도 같이 호출
	public List<Qna> findAnswers(Long no) {
		return qnaRepository.findByParno(no);
	}

	public void increaseHits(Long no) {
		Qna qna = findByQnaNo(no);
		qna.setHits(qna.getHits() + 1);
		qnaRepository.save(qna);
	}
}
