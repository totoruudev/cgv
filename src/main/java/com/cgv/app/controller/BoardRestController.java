package com.cgv.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgv.app.entity.Board;
import com.cgv.app.service.BoardService;

@RestController
@RequestMapping("/board/api")
public class BoardRestController {

	private final BoardService boardService;

	public BoardRestController(BoardService boardService) {	//서비스 생성자 주입
		this.boardService = boardService;
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<Board>> getAllBoards(){
		List<Board> boards = boardService.getAllBoards();
		return ResponseEntity.ok(boards);
	}
	
	@GetMapping("/detail/{no}")
	public ResponseEntity<Board> getBoard(@PathVariable("no") Long no, Model model) {
		Board board = boardService.getBoardByID(no);
		return ResponseEntity.ok(board);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Board> saveBoard(@RequestBody Board board) {
		return ResponseEntity.ok(boardService.saveBoard(board));
	}
	
	//샘플 수정
	@PutMapping("/edit/{no}")	
	public ResponseEntity<Board> updateBoard(@PathVariable("no") Long no, @RequestBody Board board) {
		board.setNo(no);
		return ResponseEntity.ok(boardService.updateBoard(board));
	}
	
	//샘플 삭제
	@DeleteMapping("/delete/{no}")	
	public ResponseEntity<Void> deleteBoard(@PathVariable("no") Long no) {
		boardService.deleteBoard(no);
		return ResponseEntity.noContent().build();
	}
}

