package com.mp.repository;

import java.util.List;
import java.util.Map;

import com.mp.model.product.Product;

public class InMemoryProductCatelog implements ProductCatalogRepo {
	Map<String, Product> productMap;
	Map<String, List<Product>> categoryMap;
	
	@Override
	public List<Product> findByPrductName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Product> findByProductCategory(String cat) {
		// TODO Auto-generated method stub
		return null;
	}
}
