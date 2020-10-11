package com.harshit.onlineshopping.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshit.onlineshopping.model.UserModel;
import com.harshit.shoppingbackend.dao.CartLineDAO;
import com.harshit.shoppingbackend.dao.PendingOrderDAO;
import com.harshit.shoppingbackend.dao.ProductDAO;
import com.harshit.shoppingbackend.dto.Cart;
import com.harshit.shoppingbackend.dto.CartLine;
import com.harshit.shoppingbackend.dto.PendingOrder;
import com.harshit.shoppingbackend.dto.Product;

@Service("cartService")
public class CartService {

	@Autowired
	private CartLineDAO cartLineDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private PendingOrderDAO pendingOrderDAO;

	@Autowired
	private HttpSession session;

	private Cart getCart() {
		return ((UserModel) session.getAttribute("userModel")).getCart();
	}

	public List<CartLine> getCartLines() {

		return cartLineDAO.list(this.getCart().getId(), 1);

	}

	/* to update the cart count */
	public String updateCartLine(int cartLineId, int count) {

		CartLine cartLine = cartLineDAO.get(cartLineId);

		if (cartLine == null) {
			return "result=error";
		} else {
			double oldTotal = cartLine.getTotal();

			Product product = cartLine.getProduct();

			// check if that much quantity is available or not
			if (product.getQuantity() < count) {
				return "result=unavailable";
			}

			// update the cart line
			cartLine.setProductCount(count);
			cartLine.setBuyingPrice(product.getUnitPrice());
			cartLine.setTotal(product.getUnitPrice() * count);
			cartLineDAO.update(cartLine);

			// update the cart
			Cart cart = this.getCart();
			cart.setGrandTotal(cart.getGrandTotal() - oldTotal + cartLine.getTotal());
			cartLineDAO.updateCart(cart);

			return "result=updated";
		}
	}

	public String removeCartLine(int cartLineId) {
		CartLine cartLine = cartLineDAO.get(cartLineId);
		if (cartLine == null) {
			return "result=error";
		} else {

			// deduct the cart
			// update the cart
			Cart cart = this.getCart();
			cart.setGrandTotal(cart.getGrandTotal() - cartLine.getTotal());
			cart.setCartLines(cart.getCartLines() - 1);
			cartLineDAO.updateCart(cart);

			// remove the cartLine
			cartLineDAO.remove(cartLine);

			return "result=deleted";
		}
	}

	public String addCartLine(int productId) {
		Cart cart = this.getCart();
		String response = null;
		CartLine cartLine = cartLineDAO.getByCartAndProduct(cart.getId(), productId);
		if (cartLine == null) {
			// add a new cartLine if a new product is getting added
			cartLine = new CartLine();
			Product product = productDAO.get(productId);
			// transfer the product details to cartLine
			cartLine.setCartId(cart.getId());
			cartLine.setProduct(product);
			cartLine.setProductCount(1);
			cartLine.setBuyingPrice(product.getUnitPrice());
			cartLine.setTotal(product.getUnitPrice());
			cartLine.setStatus(1);
			// insert a new cartLine
			cartLineDAO.add(cartLine);

			// update the cart
			cart.setGrandTotal(cart.getGrandTotal() + cartLine.getTotal());
			cart.setCartLines(cart.getCartLines() + 1);
			cartLineDAO.updateCart(cart);

			response = "result=added";
		} else {
			// check if the cartLine has been already reached to maximum count
			if (cartLine.getProductCount() < 3) {
				// call the manageCartLine method to increase the count
				cartLine.setStatus(1);
				response = this.updateCartLine(cartLine.getId(), cartLine.getProductCount() + 1);
			} else {
				response = "result=maximum";
			}
		}
		return response;
	}

	public String validateCartLine() {
		Cart cart = this.getCart();
		List<CartLine> cartLines = cartLineDAO.list(cart.getId(), 1);
		double grandTotal = 0.0;
		int lineCount = 0;
		String response = "result=success";
		boolean changed = false;
		Product product = null;
		for (CartLine cartLine : cartLines) {
			product = cartLine.getProduct();
			changed = false;
			// check if the product is active or not
			// if it is not active make the availability of cartLine as false
			if ((!product.isActive() && product.getQuantity() == 0) && cartLine.isAvailable()) {
				cartLine.setAvailable(false);
				changed = true;
			}
			// check if the cartLine is not available
			// check whether the product is active and has at least one quantity available
			if ((product.isActive() && product.getQuantity() > 0) && !(cartLine.isAvailable())) {
				cartLine.setAvailable(true);
				changed = true;
			}

			// check if the buying price of product has been changed
			if (cartLine.getBuyingPrice() != product.getUnitPrice()) {
				// set the buying price to the new price
				cartLine.setBuyingPrice(product.getUnitPrice());
				// calculate and set the new total
				cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
				changed = true;
			}

			// check if that much quantity of product is available or not
			if (cartLine.getProductCount() > product.getQuantity()) {
				cartLine.setProductCount(product.getQuantity());
				cartLine.setTotal(cartLine.getProductCount() * product.getUnitPrice());
				changed = true;

			}

			// changes has happened
			if (changed) {
				// update the cartLine
				cartLineDAO.update(cartLine);
				// set the result as modified
				response = "result=modified";
			}

			grandTotal += cartLine.getTotal();
			lineCount++;
		}

		cart.setCartLines(lineCount++);
		cart.setGrandTotal(grandTotal);
		cartLineDAO.updateCart(cart);

		return response;
	}

	public String addPendingOrder() {
		String response = "result=success";
		Cart cart = this.getCart();
		List<CartLine> cartLines = cartLineDAO.list(cart.getId(), 1);
		try {
			for (CartLine cartLine : cartLines) {
				PendingOrder pendingOrder = new PendingOrder();
				pendingOrder.setUserId(cart.getUser().getId());
				pendingOrder.setProductId(cartLine.getProduct().getId());
				pendingOrder.setProductCount(cartLine.getProductCount());
				pendingOrder.setBuyingPrice(cartLine.getBuyingPrice());
				pendingOrderDAO.addOrder(pendingOrder);
				
				// update quantity of product
				Product product = cartLine.getProduct();
				product.setQuantity(product.getQuantity()-cartLine.getProductCount());
				productDAO.update(product);
				
				// update status to pending
				cartLine.setStatus(2);
				cartLineDAO.update(cartLine);
			}
			return response;
		} catch (Exception e) {
			return "result=error";
		}
	}

	public List<CartLine> getAllPendingOrders() {
		Cart cart = this.getCart();
		List<CartLine> pendingOrders = cartLineDAO.list(cart.getId(), 2);
		return pendingOrders;
	}

}
