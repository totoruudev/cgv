package com.cgv.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String home(Model model) {
		String name = "주인";
		int age = 18;
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		return "index";
	}
}
