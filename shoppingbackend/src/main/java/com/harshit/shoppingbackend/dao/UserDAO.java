package com.harshit.shoppingbackend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.harshit.shoppingbackend.dto.Address;
import com.harshit.shoppingbackend.dto.Cart;
import com.harshit.shoppingbackend.dto.User;


public interface UserDAO {
	
	// user related operation
	User getByEmail(String email);
	User get(int id);

	boolean addUser(User user);
	boolean updateCart(Cart cart);
	
	
	// adding and updating a new address
	boolean addAddress(Address address);
	Address getAddress(int addressId);
	boolean updateAddress(Address address);
	Address getBillingAddress(int userId);
	List<Address> listShippingAddresses(int userId);
	
}
