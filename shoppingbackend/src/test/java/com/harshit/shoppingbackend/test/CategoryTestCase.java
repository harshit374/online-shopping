package com.harshit.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.harshit.shoppingbackend.dao.CategoryDAO;
import com.harshit.shoppingbackend.dto.Category;

public class CategoryTestCase {

	private static AnnotationConfigApplicationContext context;
	
	private static CategoryDAO categoryDAO;
	
	private Category category;
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.harshit.shoppingbackend");
		context.refresh();
		categoryDAO = (CategoryDAO) context.getBean("categoryDAO");
	}
	
//	@Test
//	public void addTestCategory() {
//
//		category = new Category();
//		category.setName("Television");
//		category.setDescription("This is Description of Television");
//		category.setImageURL("CAT_1.png");
//		
//		assertEquals("Sucessfully inserted the Category inside the table!", true, categoryDAO.add(category));
//	}
	
//	@Test
//	public void addGetCategory() {
//
//		category = categoryDAO.get(3);
//		
//		assertEquals("Sucessfully fetched the single Category from the table!", "Mobile", category.getName());
//	}
	
//	@Test
//	public void addUpdateCategory() {
//
//		category = categoryDAO.get(4);
//		category.setName("TV");
//		
//		assertEquals("Sucessfully updated the single Category in the table!", true, categoryDAO.update(category));
//	}
	
//	@Test
//	public void addDeleteCategory() {
//
//		category = categoryDAO.get(4);
//		
//		assertEquals("Sucessfully deleted the single Category from the table!", true, categoryDAO.delete(category));
//	}
	
	@Test
	public void addDeleteCategory() {
		
		assertEquals("Sucessfully fetched the list of Category from the table!", 3, categoryDAO.list().size());
	}
	
	
}
