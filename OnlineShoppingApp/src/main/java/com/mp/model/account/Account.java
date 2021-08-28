package com.mp.model.account;

import java.util.List;

import com.mp.account.fundingSource.CreditCard;
import com.mp.account.fundingSource.FundingInstrument;
import com.mp.account.fundingSource.NetBanking;
import com.mp.model.common.Address;
import com.mp.model.constants.AccountStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Account {

	private String userName;
	private String password;
	private AccountStatus status;
	private String name;
	private Address shippingAddress;
	private String email;
	private String phone;

	private List<FundingInstrument> creditCards;
	private List<FundingInstrument> bankAccounts;
	
	public void resetPassword(String newPassword) {
		this.password = newPassword;
	}
	
	public void addCC(CreditCard card) {
		this.creditCards.add(card);
	}
	public void addBank(NetBanking netBanking) {
		this.bankAccounts.add(netBanking);
	}

}
