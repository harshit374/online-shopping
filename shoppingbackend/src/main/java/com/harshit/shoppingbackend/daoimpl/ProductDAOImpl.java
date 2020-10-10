package com.harshit.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.harshit.shoppingbackend.dao.ProductDAO;
import com.harshit.shoppingbackend.dto.Product;

@Repository("productDAO")
@Transactional
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Product get(int id) {
		return sessionFactory.getCurrentSession().get(Product.class, Integer.valueOf(id));
	}

	@Override
	public List<Product> list() {
		return sessionFactory
				.getCurrentSession()
				  .createQuery("from Product", Product.class)
				  	.getResultList();
	}

	@Override
	public boolean add(Product product) {
		try {
			sessionFactory.getCurrentSession().persist(product);
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Product product) {
		try {
			sessionFactory.getCurrentSession().update(product);
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Product product) {
		product.setActive(false);
		try {
			sessionFactory.getCurrentSession().update(product);
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Product> listActiveProducts() {

		String selectActiveProduct = "from Product where active = :active";
		return sessionFactory
				.getCurrentSession()
				  .createQuery(selectActiveProduct,Product.class)
				  	.setParameter("active", true)
				  		.getResultList();
	}

	@Override
	public List<Product> listActiveProductsByCategory(int categoryId) {
		String selectActiveProductByCategory = "from Product where active = :active "
				+ " AND categoryId = :categoryId ";
		return sessionFactory
				.getCurrentSession()
				  .createQuery(selectActiveProductByCategory,Product.class)
				  	.setParameter("active", true)
				  	.setParameter("categoryId",categoryId)
				  		.getResultList();
	}

	@Override
	public List<Product> getLatestActiveProducts(int count) {
		return sessionFactory
				.getCurrentSession()
				  .createQuery("From Product where active = :active order by id",Product.class)
				  	.setParameter("active", true)
				  		.setFirstResult(0)
				  		.setMaxResults(count)
				  			.getResultList();
	}

}
