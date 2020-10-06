package com.harshit.onlineshopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

	@RequestMapping(value = {"/", "/home", "/index"})
	public ModelAndView index() {		
		ModelAndView mv = new ModelAndView("page");		
		mv.addObject("greeting","Welcome to Spring Web MVC");
		return mv;
		
	}
	
//	@RequestMapping("/test")
//	public ModelAndView test(@RequestParam(value="greetings", required=false) String greeting) {
//		if(greeting == null) {
//			greeting = "hello there";
//		}
//		
//		ModelAndView mv = new ModelAndView("page");		
//		mv.addObject("greeting",greeting);
//		return mv;
//		
//	}
	
//	@RequestMapping("/test/{greetings}")
//	public ModelAndView test(@PathVariable("greetings") String greeting) {
//		if(greeting == null) {
//			greeting = "hello there";
//		}
//		
//		ModelAndView mv = new ModelAndView("page");		
//		mv.addObject("greeting",greeting);
//		return mv;
//		
//	}
}
