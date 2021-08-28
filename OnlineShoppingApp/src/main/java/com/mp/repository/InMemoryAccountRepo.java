package com.mp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mp.model.account.Account;
import com.mp.model.account.Customer;
import com.mp.model.account.Member;
import com.mp.model.account.ShoppingCart;
import com.mp.model.order.Order;

public class InMemoryAccountRepo implements AccountRepo{
	
	Map<String, Customer> accountRepo = new HashMap<>();
	Map<String, ShoppingCart> purchaceRepo = new HashMap<>();
	Map<String, List<Order>> orderContext = new HashMap<>();
	
	@Override
	public Customer findByAccountId(String accountId) {
		return accountRepo.get(accountId);
	}
	@Override
	public ShoppingCart getShoppingCart(String accountId) {
		return purchaceRepo.get(accountId);
	}
	@Override
	public List<Order> getOrderHistory(String accountId) {
		return orderContext.get(accountId);
	}
	@Override
	public Member createAccount(Account account) {
		Member member = new Member();
		member.setAccount(account);
		member.setAccountId(UUID.randomUUID().toString());
		accountRepo.put(member.getAccountId(), member);
		purchaceRepo.put(member.getAccountId(), new ShoppingCart());
		return member;
	}

}
