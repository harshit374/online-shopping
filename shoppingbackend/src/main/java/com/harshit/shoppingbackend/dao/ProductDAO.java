package com.harshit.shoppingbackend.dao;

import java.util.List;

import com.harshit.shoppingbackend.dto.Product;

public interface ProductDAO {

	Product get(int id);
	List<Product> list();
	boolean add(Product product);
	boolean update(Product product);
	boolean delete(Product product);
	
	//business methods
	List<Product> listActiveProducts();
	List<Product> listActiveProductsByCategory(int categoryId);
	List<Product> getLatestActiveProducts(int count);
	
	
}
