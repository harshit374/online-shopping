package com.harshit.shoppingbackend.dao;

import java.util.List;

import com.harshit.shoppingbackend.dto.Category;

public interface CategoryDAO {

	List<Category> list();
	Category get(int id);
}
