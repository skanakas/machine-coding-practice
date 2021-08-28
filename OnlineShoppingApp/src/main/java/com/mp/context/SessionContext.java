package com.mp.context;

import com.mp.model.account.Member;

import lombok.Builder;

@Builder
public class SessionContext {
	
	Member account;

}
