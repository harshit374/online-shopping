package com.harshit.shoppingbackend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.harshit.shoppingbackend.dto.Cart;
import com.harshit.shoppingbackend.dto.CartLine;
import com.harshit.shoppingbackend.dto.OrderDetail;

public interface CartLineDAO {

	public CartLine get(int id);
	public List<CartLine> list(int cartId, int status);
	public boolean add(CartLine cartLine);
	public boolean update(CartLine cartLine);
	public boolean remove(CartLine cartLine);
	
	// fetch the CartLine based on cartId and productId
	public CartLine getByCartAndProduct(int cartId, int productId);		
		
	// updating the cart
	boolean updateCart(Cart cart);
	
	// list of available cartLine
	public List<CartLine> listAvailable(int cartId);
	
	// adding order details
	boolean addOrderDetail(OrderDetail orderDetail);
}
