package com.ll.medium.domail.home.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

	@GetMapping("/")
	public String showMain() {
		return "redirect:/post/list/latest";
	}
}
