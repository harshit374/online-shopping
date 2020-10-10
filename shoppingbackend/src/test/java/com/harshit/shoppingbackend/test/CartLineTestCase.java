package com.harshit.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.harshit.shoppingbackend.dao.CartLineDAO;
import com.harshit.shoppingbackend.dao.ProductDAO;
import com.harshit.shoppingbackend.dao.UserDAO;
import com.harshit.shoppingbackend.dto.Cart;
import com.harshit.shoppingbackend.dto.CartLine;
import com.harshit.shoppingbackend.dto.Product;
import com.harshit.shoppingbackend.dto.User;


public class CartLineTestCase {

private static AnnotationConfigApplicationContext context;
	
	
	private static CartLineDAO cartLineDAO;
	private static ProductDAO productDAO;
	private static UserDAO userDAO;
	
	
	private CartLine cartLine = null;
	private User user = null;
	private Cart cart = null;
	private Product product = null;
	
	
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.harshit.shoppingbackend");
		context.refresh();
		productDAO = (ProductDAO)context.getBean("productDAO");
		userDAO = (UserDAO)context.getBean("userDAO");
		cartLineDAO = (CartLineDAO) context.getBean("cartLineDAO");
		
	}
	
	
	@Test
	public void testAddCartLine() {
		
		// fetch the user and then cart of that user
		user = userDAO.getByEmail("test@gmail.com");		
		cart = user.getCart();
		
		// fetch the product 
		product = productDAO.get(4);
		
		// Create a new CartLine
		cartLine = new CartLine();
		cartLine.setBuyingPrice(product.getUnitPrice());
		cartLine.setProductCount(cartLine.getProductCount()+1);
		cartLine.setAvailable(true);
		cartLine.setTotal(product.getUnitPrice()* product.getQuantity());
		cartLine.setCartId(cart.getId());
		cartLine.setProduct(product);
		cartLine.setProductCount(1);
		

		assertEquals("Failed to add the CartLine!",true, cartLineDAO.add(cartLine));
					
		cart.setCartLines(cart.getCartLines() + 1);
		cart.setGrandTotal(cart.getGrandTotal() + cartLine.getTotal());
		
		assertEquals("Failed to update the cart!",true, cartLineDAO.updateCart(cart));
		
	}
	
	
}
