package com.n26.transaction.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionsCacheObject {
	
	private final Long cacheKey;
	private final List<Transaction> refObject;
	private final Long expireAt;
	
}
