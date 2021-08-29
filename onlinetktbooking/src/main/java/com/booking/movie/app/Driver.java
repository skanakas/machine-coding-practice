package com.booking.movie.app;

import java.util.ArrayList;
import java.util.List;

import com.booking.movei.cache.InMemorySeatLockCache;
import com.booking.movei.cache.SeatLockProvider;
import com.booking.movie.model.Screen;
import com.booking.movie.model.Seat;
import com.booking.movie.model.Show;
import com.booking.movie.model.Theater;
import com.booking.movie.model.User;
import com.booking.movie.repo.TheaterRepository;

public class Driver {

	public static void main(String[] args) throws InterruptedException {
		SeatLockProvider lockProvider = new InMemorySeatLockCache(10);
		Thread autoEvictionService = new Thread(() -> {
			try {
				lockProvider.executeAutoEvictPolicy();
			} catch (InterruptedException e) {
			}
		});
		
		autoEvictionService.setDaemon(true);
		autoEvictionService.start();
		
		List<Thread> clients = new ArrayList<>();
		
		Theater theater = TheaterRepository.INSTANCE.createTheater("Theater 1");
		Screen screen = TheaterRepository.INSTANCE.createScreenInTheater("Screen1", theater);
		
		List<Seat> seats = new ArrayList<>();
		for(int i = 1 ; i<=20; i++) {
			Seat seat = TheaterRepository.INSTANCE.createSeatInScreen("A", i, screen);
			seats.add(seat);
		}
		
		User user = new User("SRK", "1");
		Show show = new Show(null, screen, null, null);
		
		Thread thread = new Thread(() ->{
			lockProvider.lockSeats(show, seats, user);
		});
		thread.start();
		
		clients.add(thread);
		clients.add(autoEvictionService);

		Thread.sleep(2000);
		List<Seat> subSet = seats.subList(0, 3);
		lockProvider.unlockSeats(show, subSet, user);
		
		for(Thread t : clients) {
			t.join();
		}
	}

}
