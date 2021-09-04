package com.digilock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.digilock.SampleTestData;
import com.digilock.exception.LockerNotFoundExeption;
import com.digilock.exception.NotAuthorizedException;
import com.digilock.model.LockerInstance;
import com.digilock.model.LockerLocation;
import com.digilock.model.UserLockerNotification;
import com.digilock.repo.LockerInstanceRepo;
import com.digilock.repo.LockerLocationRepo;
import com.digilock.repo.LockerRepo;
import com.digilock.repo.OrderRepo;
import com.digilock.repo.UserLockerNotificationRepo;

public class TestDeliveryAndLockService {

	private static DeliveryService deliveryService;
	private static LockerService lockerService;
	private static LockerRepo lockerRepo = null;

	@BeforeAll
	public static void preSetUp() {

		deliveryService = new DeliveryService();
		lockerRepo = new LockerRepo();
		GeoLocationService geoLocationService = new GeoLocationService();
		lockerService = new LockerService(lockerRepo, geoLocationService);

		for(LockerLocation lockerLocation : SampleTestData.createLockers(100)) {
			LockerLocationRepo.addLockerLocation(lockerLocation);
		}

		LockerLocationRepo.postConstructGrid();
	}

	/**
	 * Random order locations
	 * 
	 * @throws LockerNotFoundExeption
	 */
	@Test
	public void testDeliveryService() throws LockerNotFoundExeption {
		boolean lockNotFoundExceptionThrown = false;
		try {
			String order1 = UUID.randomUUID().toString();
			String order2 = UUID.randomUUID().toString();
			OrderRepo.orderMap.put(order1, SampleTestData.createSampleOrder());
			OrderRepo.orderMap.put(order2, SampleTestData.createSampleOrder());

			deliveryService.deliverOrder(order1);
			UserLockerNotification notification = UserLockerNotificationRepo.userLockerMap.get(order1);
			assertEquals(order1, notification.getOrderID());

			deliveryService.deliverOrder(order2);
			notification = UserLockerNotificationRepo.userLockerMap.get(order2);
			assertEquals(order2, notification.getOrderID());
		} catch (LockerNotFoundExeption e) {
			lockNotFoundExceptionThrown = true;
			assertTrue(lockNotFoundExceptionThrown, "Lock not found senario");
		}

	}

	/**
	 * Random order locations
	 * 
	 * @throws LockerNotFoundExeption
	 */
	@Test
	public void testDeliveryAndPickUpService() throws LockerNotFoundExeption {
		boolean lockNotFoundExceptionThrown = false;
		boolean lockerAuthFailure = false;
		boolean successSenario = false;

		while(true) {
			try {
				String order1 = UUID.randomUUID().toString();
				OrderRepo.orderMap.put(order1, SampleTestData.createSampleOrder());

				deliveryService.deliverOrder(order1);
				UserLockerNotification notification = UserLockerNotificationRepo.userLockerMap.get(order1);
				assertEquals(order1, notification.getOrderID());

				LockerInstance lockerInstance = LockerInstanceRepo.lockerInstances.stream()
						.filter(instance -> instance.getOrderID().equals(order1))
						.findFirst().get();
				lockerService.unlockLocker(lockerInstance.getLockerID(), lockerInstance.getOtp());
				successSenario = true;
				assertFalse(lockerRepo.getInUseLocker(lockerInstance.getLockerID()) != null);
			} catch (LockerNotFoundExeption e) {
				lockNotFoundExceptionThrown = true;
				System.out.println(e.getMessage());
			} catch (NotAuthorizedException e) {
				lockerAuthFailure = true;
				System.out.println(e.getMessage());
			}
			
			if(successSenario && lockNotFoundExceptionThrown && lockerAuthFailure) {
				break;
			}
			
		}

	}

}
