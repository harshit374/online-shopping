package com.harshit.onlineshopping.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.harshit.shoppingbackend.dao.ProductDAO;
import com.harshit.shoppingbackend.dto.Product;

@Controller
@RequestMapping("/json/data")
public class JsonDataController {

	public static Logger logger = LoggerFactory.getLogger(JsonDataController.class);

	@Autowired
	private ProductDAO productDAO;
	

	@RequestMapping("/admin/all/products")
	@ResponseBody
	public List<Product> getAllProductsList() {	
		return productDAO.list();
				
	}	
	
	
	@RequestMapping("/all/products")
	@ResponseBody
	public List<Product> getAllProducts() {
		return productDAO.listActiveProducts();
				
	}
	
	@RequestMapping("/category/{id}/products")
	@ResponseBody
	public List<Product> getProductsByCategory(@PathVariable int id) {
		
		return productDAO.listActiveProductsByCategory(id);
				
	}
}
