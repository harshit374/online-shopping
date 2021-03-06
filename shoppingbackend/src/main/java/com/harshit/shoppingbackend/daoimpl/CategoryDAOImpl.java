package com.harshit.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.harshit.shoppingbackend.dao.CategoryDAO;
import com.harshit.shoppingbackend.dto.Category;

@Repository("categoryDAO")
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

	
	@Autowired
	private SessionFactory sessionFactory;
	

	@Override
	public List<Category> list() {
		String selectActiveCategory = "from Category where active = :active";
		return sessionFactory
				.getCurrentSession()
				  .createQuery(selectActiveCategory,Category.class)
				  	.setParameter("active", true)
				  		.getResultList();
	}

	@Override
	public Category get(int id) {
		
		return sessionFactory.getCurrentSession().get(Category.class, Integer.valueOf(id));
	}

	@Override
	public boolean add(Category category) {
		try {
			sessionFactory.getCurrentSession().persist(category);
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Category category) {
		try {
			sessionFactory.getCurrentSession().update(category);
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Category category) {
		category.setActive(false);
		try {
			sessionFactory.getCurrentSession().update(category);
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
