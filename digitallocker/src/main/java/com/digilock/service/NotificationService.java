package com.digilock.service;

import com.digilock.model.LockerInstance;
import com.digilock.model.UserLockerNotification;
import com.digilock.repo.UserLockerNotificationRepo;

public class NotificationService {
	
	AccountService accountService = new AccountService();
	
	public void notifyCustomerOrder(LockerInstance lockerInstance) {
		String customerId = accountService.getUserIdForOrder(lockerInstance.getOrderID());
		UserLockerNotification notification = new UserLockerNotification(customerId, lockerInstance.getOrderID(),
				lockerInstance.getLockerID(), lockerInstance.getOtp());
		UserLockerNotificationRepo.userLockerMap.put(lockerInstance.getOrderID(), notification);
		
		System.out.println(String.format("Notification sent to user %s on order %s with OTP %s",
				customerId, 
				lockerInstance.getOrderID(),
				lockerInstance.getOtp()));
	}
}
