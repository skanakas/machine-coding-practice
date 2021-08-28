package com.mp.service;

import java.util.List;

import com.mp.model.product.Product;
import com.mp.repository.ProductCatalogRepo;

public class InMemorySearchService implements SearchService {
	
	public ProductCatalogRepo productCatalogRepo;
	public InMemorySearchService(ProductCatalogRepo productCatalogRepo) {
		this.productCatalogRepo = productCatalogRepo;
	}

	@Override
	public List<Product> searchByProductName(String productName) {
		return null;
	}

	@Override
	public List<Product> searchByProductCategory(String prodCategory) {
		// TODO Auto-generated method stub
		return null;
	}

}
