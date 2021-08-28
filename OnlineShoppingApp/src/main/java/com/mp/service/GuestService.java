package com.mp.service;

import com.mp.context.SessionContext;
import com.mp.model.account.Account;
import com.mp.model.account.Member;
import com.mp.repository.AccountRepo;

public class GuestService extends CustomerService {
	
	public GuestService(AccountRepo accountRepo, SearchService searchService) {
		super(accountRepo, searchService);
	}

	public SessionContext registerAccount(Account account) {
		
		Member member = accountRepo.createAccount(account);
		SessionContext sessionContext = SessionContext
				.builder()
				.account(member)
				.build();
		return sessionContext;
	}

}
