package com.harshit.onlineshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.harshit.shoppingbackend.dao.CategoryDAO;
import com.harshit.shoppingbackend.dto.Category;

@Controller
public class PageController {

	@Autowired
	private CategoryDAO categoryDAO;
	
	
	@RequestMapping(value = {"/", "/home", "/index"})
	public ModelAndView index() {		
		ModelAndView mv = new ModelAndView("page");		
		mv.addObject("title","Home");
		mv.addObject("userClickHome",true);
		
		mv.addObject("categories",categoryDAO.list());
		
		return mv;
	}
	
	@RequestMapping(value = "/about")
	public ModelAndView about() {		
		ModelAndView mv = new ModelAndView("page");		
		mv.addObject("title","About Us");
		mv.addObject("userClickAbout",true);
		return mv;				
	}	
	
	@RequestMapping(value = "/contact")
	public ModelAndView contact() {		
		ModelAndView mv = new ModelAndView("page");		
		mv.addObject("title","Contact Us");
		mv.addObject("userClickContact",true);
		return mv;				
	}
	
	@RequestMapping(value = {"/show/all/products"})
	public ModelAndView showAllProducts() {	
		
		ModelAndView mv = new ModelAndView("page");		
		mv.addObject("title","All Products");
		mv.addObject("userClickAllProducts",true);
		
		mv.addObject("categories",categoryDAO.list());
		
		return mv;
	}
	
	@RequestMapping(value = {"/show/category/{id}/products"})
	public ModelAndView showCategoryProducts(@PathVariable("id") int id) {	
		
		ModelAndView mv = new ModelAndView("page");	
		
		// categoryDAO to fetch a single category
		Category category = categoryDAO.get(id);
		
		mv.addObject("title", category.getName());
		mv.addObject("userClickCategoryProducts",true);
		
		mv.addObject("categories",categoryDAO.list());
		mv.addObject("category",category);
		
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
