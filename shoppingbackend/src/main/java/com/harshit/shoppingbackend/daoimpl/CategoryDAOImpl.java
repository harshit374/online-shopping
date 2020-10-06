package com.harshit.shoppingbackend.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.harshit.shoppingbackend.dao.CategoryDAO;
import com.harshit.shoppingbackend.dto.Category;

@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {

	private static List<Category> categories = new ArrayList<>();

	static {
		Category category = new Category();
		category.setId(1);
		category.setName("Television");
		category.setDescription("This is Description of Television");
		category.setImageURL("CAT_1.png");

		categories.add(category);

		// 2nd category
		category = new Category();
		category.setId(2);
		category.setName("Phone");
		category.setDescription("This is Description of Phone");
		category.setImageURL("CAT_2.png");

		categories.add(category);

		// 3rd category
		category = new Category();
		category.setId(2);
		category.setName("Laptop");
		category.setDescription("This is Description of Laptop");
		category.setImageURL("CAT_3.png");

		categories.add(category);
	}

	@Override
	public List<Category> list() {

		return categories;
	}

	@Override
	public Category get(int id) {
		for(Category category : categories) {
			if(category.getId() == id) return category;
		}
		return null;
	}

}
