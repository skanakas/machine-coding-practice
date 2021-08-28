package com.mp.account.fundingSource;

import com.mp.model.account.Member;

public class NetBanking extends FundingInstrument {

	public NetBanking(Member member, String accountNumber) {
		super(member);
		this.actNumber = accountNumber;
	}

	public String actNumber;
}
