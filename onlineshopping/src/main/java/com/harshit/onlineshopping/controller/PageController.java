package com.harshit.onlineshopping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.harshit.onlineshopping.exception.ProductNotFoundException;
import com.harshit.shoppingbackend.dao.CategoryDAO;
import com.harshit.shoppingbackend.dao.ProductDAO;
import com.harshit.shoppingbackend.dto.Category;
import com.harshit.shoppingbackend.dto.Product;

@Controller
public class PageController {

	public static Logger logger = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	
	@RequestMapping(value = {"/", "/home", "/index"})
	public ModelAndView index() {
		logger.info("Inside PageController index method - level info");
		logger.debug("Inside PageController index method - level debug");
		
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
		logger.info("Inside PageController showAllPoducts method");
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
	
	/*
	 * Viewing a single product
	 * */
	
	@RequestMapping(value = "/show/{id}/product") 
	public ModelAndView showSingleProduct(@PathVariable int id) throws ProductNotFoundException 
	{
		
		ModelAndView mv = new ModelAndView("page");
		
		Product product = productDAO.get(id);
		
		if(product == null) throw new ProductNotFoundException();
		
		// update the view count
		product.setViews(product.getViews() + 1);
		productDAO.update(product);
		//---------------------------
		
		mv.addObject("title", product.getName());
		mv.addObject("product", product);
		
		mv.addObject("userClickShowProduct", true);
		
		
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
