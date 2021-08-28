package com.mp.service;

import java.util.List;

import com.mp.context.ShoppingContext;
import com.mp.model.account.Customer;
import com.mp.model.account.ShoppingCart;
import com.mp.model.product.Product;
import com.mp.model.product.ProductItem;
import com.mp.repository.AccountRepo;

public abstract class CustomerService {
	
	AccountRepo accountRepo;
	SearchService searchService;
	
	
	public CustomerService(AccountRepo accountRepo, SearchService searchService) {
		this.accountRepo = accountRepo;
		this.searchService = searchService;
	}
	
	public ShoppingCart readShopingCartDetails(Customer user) {
		return accountRepo.getShoppingCart(user.getAccountId());
	}
	
	public ShoppingContext addItemToShoppingCart(Customer user, ProductItem items) {
		accountRepo.getShoppingCart(user.getAccountId());
		return null;
	}

	public boolean removeItemToShoppingCart(Customer user, ProductItem items) {
		return false;
	}
	
	public List<Product> searchOfProduct(String productName){
		return searchService.searchByProductName(productName);
	}

}
