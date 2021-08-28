package com.mp.service;

import java.util.List;

import com.mp.model.product.Product;

public interface SearchService {
	
	List<Product> searchByProductName(String productName);
	
	List<Product> searchByProductCategory(String prodCategory);
}
