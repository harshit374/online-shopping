package com.harshit.onlineshopping.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.harshit.onlineshopping.util.FileUploadUtility;
import com.harshit.onlineshopping.validator.ProductValidator;
import com.harshit.shoppingbackend.dao.CategoryDAO;
import com.harshit.shoppingbackend.dao.ProductDAO;
import com.harshit.shoppingbackend.dto.Category;
import com.harshit.shoppingbackend.dto.Product;

@Controller
@RequestMapping("/manage")
public class ManagementController {

	public static Logger logger = LoggerFactory.getLogger(ManagementController.class);

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private ProductDAO productDAO;

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ModelAndView showManagementProduct(@RequestParam(name = "operation", required = false) String success) {

		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Manage Products");
		mv.addObject("userClickManageProducts", true);

		Product nProduct = new Product();

		// assuming that the user is ADMIN
		// later we will fixed it based on user is SUPPLIER or ADMIN
		nProduct.setSupplierId(1);
		nProduct.setActive(true);

		mv.addObject("product", nProduct);

		if(success != null) {
			if(success.equals("product")){
				mv.addObject("message", "Product Added successfully!");
			}	
			else if (success.equals("category")) {
				mv.addObject("message", "Category Added successfully!");
			}
		}

		return mv;

	}

	// handling product submission
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public String handleProductSubmission(@Valid @ModelAttribute("product") Product mProduct, 
											BindingResult results,
											Model model, 
											HttpServletRequest request) {
		
		new ProductValidator().validate(mProduct, results);
		if(results.hasErrors()) {
			model.addAttribute("message", "Validation fails for adding the product!");
			model.addAttribute("userClickManageProducts",true);
			model.addAttribute("title", "Manage Products");
			return "page";
		}	

		
		productDAO.add(mProduct);
		
		if(!mProduct.getFile().getOriginalFilename().equals("")) {
			FileUploadUtility.upload(request, mProduct.getFile(), mProduct.getCode());
		}
		return "redirect:/manage/products?operation=product";
	}

	@ModelAttribute("categories")
	public List<Category> getCategories() {
		return categoryDAO.list();
	}
}
