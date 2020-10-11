package com.harshit.shoppingbackend.dao;

import java.util.List;

import com.harshit.shoppingbackend.dto.PendingOrder;

public interface PendingOrderDAO {
		
		// adding and fetching PendingOrders
		boolean addOrder(PendingOrder pendingOrder);
		//boolean updatePendingOrder(PendingOrder pendingOrder);
		List<PendingOrder> listPendingOrders(int userId);
}
