package com.digilock.service;

import java.util.List;

import com.digilock.exception.LockerNotFoundExeption;
import com.digilock.model.Locker;
import com.digilock.model.LockerInstance;
import com.digilock.model.LockerSize;
import com.digilock.model.LockerStatus;
import com.digilock.model.Order;
import com.digilock.model.OrderItem;
import com.digilock.model.Pack;
import com.digilock.model.common.Mapper;
import com.digilock.repo.LockerInstanceRepo;
import com.digilock.repo.LockerRepo;
import com.digilock.service.common.SecretCodeGenerator;

public class DeliveryService {
	
	OrderService orderService = new OrderService();
	LockerRepo lockerRepo = new LockerRepo();
	GeoLocationService geoLocationService = new GeoLocationService();
	LockerService lockerService = new LockerService(lockerRepo, geoLocationService);
	NotificationService notificationService = new NotificationService();
	
	public void deliverOrder(String orderID) throws LockerNotFoundExeption {
		Order order = orderService.fetchOrderDetails(orderID);
		List<OrderItem> orderItems = order.getOrderItems();
		
		Pack pack = new Pack(orderID, orderItems, order.getDeliveryDetails());
		
		LockerSize desiredLockerSize = Mapper.getLockerSizeFor(pack.getSize());
		Locker locker = lockerService.getAvailableLocker(desiredLockerSize, pack.getDeliveryDetails().getDeliveryLocation());
		
		LockerInstance lockerInstance = new LockerInstance();
		lockerInstance.setOrderID(orderID);;
		lockerInstance.setLockerID(locker.getId());
		lockerInstance.setOtp(SecretCodeGenerator.generateSecretCode(8));
		LockerInstanceRepo.lockerInstances.add(lockerInstance);//Persist
		
		locker.setStatus(LockerStatus.CLOSED);
		lockerRepo.moveToInUse(locker);
		
		//NotifyUser
		notificationService.notifyCustomerOrder(lockerInstance);
	}

}
