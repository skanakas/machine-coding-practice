package com.mp.repository;

import java.util.List;

import com.mp.model.account.Account;
import com.mp.model.account.Customer;
import com.mp.model.account.Member;
import com.mp.model.account.ShoppingCart;
import com.mp.model.order.Order;

public interface AccountRepo {
	
	Customer findByAccountId(String accountId);
	ShoppingCart getShoppingCart(String accountId);
	List<Order> getOrderHistory(String accountId);
	Member createAccount(Account account);

}
