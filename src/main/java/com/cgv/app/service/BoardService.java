package com.cgv.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cgv.app.entity.Board;
import com.cgv.app.repository.BoardRepository;

@Service
public class BoardService {

	private final BoardRepository boardRepository; // << 생성자 주입 // 필드명은 무조건 앞자리가 소문자여야함

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	// 게시글 목록
	public List<Board> getAllBoards() {
		return boardRepository.findAll();
	}

	// 페이징 처리
	public Page<Board> getBoardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	// 게시글 건당 조회
	public Board getBoardByID(Long no) {
		Board board = boardRepository.findById(no).orElse(null);
		return board;
	}

	// 조회수 증가
	public void increaseHits(Long no) {
		Board board = getBoardByID(no);
		board.setHits(board.getHits() + 1);
		boardRepository.save(board);
	}

	// 게시판 자세히 조회
	public Optional<Board> getBoardByNo(Long no) {
		return boardRepository.findById(no);
	}

	// 게시글 작성
	// board.setResdate(LocalDateTime.now());
	public Board saveBoard(Board board) {
		return boardRepository.save(board);
	}

	// 게시글 수정
	public Board updateBoard(Board board) {
		return boardRepository.save(board);
	}

	// 게시글 삭제
	public void deleteBoard(Long no) {
		boardRepository.deleteById(no);
	}
}
