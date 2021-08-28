/**
 * 
 */
package com.mp.service;

import com.mp.account.fundingSource.FundingInstrument;
import com.mp.context.OrderContext;
import com.mp.context.SessionContext;
import com.mp.context.ShoppingContext;
import com.mp.model.order.Order;
import com.mp.model.payment.Payment;
import com.mp.model.product.Product;
import com.mp.model.product.ProductReview;
import com.mp.model.shipment.Shipment;
import com.mp.repository.AccountRepo;
import com.mp.repository.ProductCatalogRepo;

/**
 * @author arun
 *
 */
public class MemberService extends CustomerService {

	ProductCatalogRepo productCatalogRepo = null;

	public MemberService(AccountRepo accountRepo,
			SearchService searchService,
			ProductCatalogRepo productCatalog,
			PaymentService paymentServiceOrchestrator) {
		
		super(accountRepo, searchService);
		this.productCatalogRepo = productCatalog;
		
	}

	public boolean addProduct(SessionContext sessionContext, Product product) {
		return false;
	}

	public boolean addProductReview(SessionContext sessionContext, Product product, ProductReview review) {
		return false;
	}

	public Order placeOrder(ShoppingContext context, Shipment shipmentDetails, FundingInstrument fundingInstrument) {
		
		OrderContext orderContext = this.createOrder(context, shipmentDetails);
		this.processPayment(orderContext, fundingInstrument);
		return orderContext.getOrder();

	}

	private OrderContext createOrder(ShoppingContext shoppingContext, Shipment shipmentDetails) {
		OrderContext orderContext = new OrderContext();
		orderContext.setSessionContext(shoppingContext.getSessionContext());
		return null;
	}

	private void processPayment(OrderContext shoppingContext, FundingInstrument fundingInstrument) {
		
	}
}
