package com.mp.repository;

import java.util.List;

import com.mp.model.product.Product;

public interface ProductCatalogRepo {
	
	List<Product> findByPrductName(String name);
	List<Product> findByProductCategory(String cat);
	
	boolean addProductToCatalog(Product product);
	boolean removeProductFromCatalog(Product product);
	
}
