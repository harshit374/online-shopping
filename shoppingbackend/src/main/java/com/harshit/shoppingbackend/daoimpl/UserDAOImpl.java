package com.harshit.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.harshit.shoppingbackend.dao.UserDAO;
import com.harshit.shoppingbackend.dto.Address;
import com.harshit.shoppingbackend.dto.Cart;
import com.harshit.shoppingbackend.dto.User;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User getByEmail(String email) {
		String selectQuery = "FROM User WHERE email = :email";
		try {
			return sessionFactory.getCurrentSession().createQuery(selectQuery, User.class).setParameter("email", email)
					.getSingleResult();
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public User get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addUser(User user) {
		try {
			sessionFactory.getCurrentSession().persist(user);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean updateCart(Cart cart) {
		try {
			sessionFactory.getCurrentSession().update(cart);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean addAddress(Address address) {
		try {
			sessionFactory.getCurrentSession().persist(address);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public Address getAddress(int addressId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateAddress(Address address) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Address getBillingAddress(int userId) {
		String selectQuery = "FROM Address WHERE userId = :userId AND billing = :isBilling";
		try {
			return sessionFactory.getCurrentSession().createQuery(selectQuery, Address.class)
					.setParameter("userId", userId).setParameter("isBilling", true).getSingleResult();
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public List<Address> listShippingAddresses(int userId) {
		String selectQuery = "FROM Address WHERE userId = :userId AND shipping = :isShipping ORDER BY id DESC";
		return sessionFactory.getCurrentSession().createQuery(selectQuery, Address.class).setParameter("userId", userId)
				.setParameter("isShipping", true).getResultList();

	}
}
