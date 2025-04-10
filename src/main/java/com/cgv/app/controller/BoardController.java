package com.cgv.app.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cgv.app.entity.Board;
import com.cgv.app.entity.Member;
import com.cgv.app.service.BoardService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController { // Thymeleaf 용

	private final BoardService boardService;

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	// 게시글 목록 페이징 처리
	@GetMapping("/list")
	public String getAllBoards(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Page<Board> boards = boardService.getBoardList(PageRequest.of(page, 10));
		model.addAttribute("boards", boards);
		model.addAttribute("currentPage", page); // 현재 페이지 리턴
		return "board-list";
	}

	// 쿠키 조회와 선언
	@GetMapping("/detail/{no}")
	public String getBoard(@PathVariable("no") Long no, Model model, HttpServletRequest req, HttpServletResponse res) {

		Cookie[] cookies = req.getCookies(); // 사용자 컴퓨터의 쿠키 선언
		boolean isViewed = false;
		String cookieName = "board_view_" + no;

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
			boardService.increaseHits(no);
			Cookie newCookie = new Cookie(cookieName, "viewed"); // 쿠키 생성
			newCookie.setMaxAge(60 * 60 * 24); // 24시간
			newCookie.setPath("/");
			res.addCookie(newCookie);
		}

		model.addAttribute("board", boardService.getBoardByID(no));
		return "board-detail";
	}

	@GetMapping("/ins")
	public String boardForm(Model model, HttpSession session) {
		Board board = new Board();
		Member member = (Member) session.getAttribute("loginMember");
		if (member != null) {
			board.setAuthor(member.getId());
		}
		model.addAttribute("board", new Board());
		return "board-form"; // board-form.html <form th:object="board">
	}

	@PostMapping("/save")
	public String saveBoard(@ModelAttribute Board board) {
		boardService.saveBoard(board);
		return "redirect:/board/list";
	}

	@GetMapping("/edit/{no}")
	public String editForm(@PathVariable("no") Long no, Model model) {
		model.addAttribute("board", boardService.getBoardByID(no));
		return "board-edit"; // board-edit.html <form th:object="board">
	}

	@PostMapping("/update/{no}")
	public String updateBoard(@PathVariable("no") Long no, @ModelAttribute Board board) {
		board.setNo(no);
		boardService.updateBoard(board);
		return "redirect:/board/list";
	}

	@GetMapping("/delete/{no}")
	public String deleteBoard(@PathVariable("no") Long no) {
		boardService.deleteBoard(no);
		return "redirect:/board/list";
	}

}
