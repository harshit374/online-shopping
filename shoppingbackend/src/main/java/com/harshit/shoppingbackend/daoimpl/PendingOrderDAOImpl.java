package com.harshit.shoppingbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.harshit.shoppingbackend.dao.PendingOrderDAO;
import com.harshit.shoppingbackend.dto.PendingOrder;

@Repository("pendingOrderDAO")
@Transactional
public class PendingOrderDAOImpl implements PendingOrderDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean addOrder(PendingOrder pendingOrder) {
		try {
			sessionFactory.getCurrentSession().persist(pendingOrder);
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List<PendingOrder> listPendingOrders(int userId) {
		String query = "FROM PendingOrder WHERE userId = :userId";
		return sessionFactory.getCurrentSession()
								.createQuery(query, PendingOrder.class)
									.setParameter("userId", userId)
										.getResultList();
	}

}
