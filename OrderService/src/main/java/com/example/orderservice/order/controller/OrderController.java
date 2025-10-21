package com.example.orderservice.order.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {
	
	@GetMapping("/msg")
	public String msg() {
		return "Test message in Order service";
	}

}
