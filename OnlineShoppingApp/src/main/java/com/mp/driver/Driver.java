package com.mp.driver;

import java.util.List;

import com.mp.context.SessionContext;
import com.mp.model.account.Account;
import com.mp.model.product.Product;
import com.mp.repository.AccountRepo;
import com.mp.repository.InMemoryAccountRepo;
import com.mp.repository.InMemoryProductCatelog;
import com.mp.repository.ProductCatalogRepo;
import com.mp.service.GuestService;
import com.mp.service.InMemorySearchService;
import com.mp.service.MemberService;
import com.mp.service.SearchService;

public class Driver {

	public static void main(String[] args) {

		ProductCatalogRepo productCatalogRepo = new InMemoryProductCatelog();
		
		AccountRepo accountRepo = new InMemoryAccountRepo();
		SearchService searchService = new InMemorySearchService(productCatalogRepo);
		
		GuestService guestService = new GuestService(accountRepo, searchService);
		/**
		 * Browse for products
		 */
		List<Product> products = guestService.searchOfProduct("MAC");
		
		/**
		 * Select product
		 */
		Account account = Account.builder().userName("u1").build();
		SessionContext sessionContext = guestService.registerAccount(account);
		MemberService memberService = new MemberService(accountRepo, searchService, productCatalogRepo, null)
		
	}

}
