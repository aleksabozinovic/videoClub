package com.videoclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("index")
	public String showIndexPage() {
		return "index";
	}
	
	
}
