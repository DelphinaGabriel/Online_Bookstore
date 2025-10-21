package com.example.bookservice.book.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@CrossOrigin("*")
public class BookController {

	@GetMapping("/msg")
	public String msg() {
		return "Test message in Book service";
	}

}
