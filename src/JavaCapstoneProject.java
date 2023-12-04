import java.util.Random;

import Car.Car;
import Services.ChargingQueue;

public class JavaCapstoneProject {

	public static void main(String[] args) {

		Car[] cars = {
				new Car(11, 10, 12000),
				new Car(12, 10, 600000),
				new Car(13, 10, 1500000), // exceeds 15 mins
				new Car(14, 10, 1200000), // exceeds 15 mins
				new Car(15, 10, 300000)
		};

		final int chargingQueueSize = 3;
		ChargingQueue chargingQueue = new ChargingQueue(chargingQueueSize);
		Random random = new Random();

		Thread carEnteringThread = new Thread(() -> {
			for (int i = 0; i < 5; i++) {

				chargingQueue.charge(cars[i]);

				try {
					// random interval to wait for the next coming car
					long randomInterval = random.nextInt(7000) + 1000;
					Thread.sleep(randomInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread carsLeavingThread = new Thread(() -> {
			try {
				Thread.sleep(10000); // 10 seconds wait
				while (!chargingQueue.isEmpty()) {
					var outCar = chargingQueue.leave();
					if (outCar != null) {
						System.out.println("Car ID#" + outCar.get_id() + " is leaving now...");
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		carEnteringThread.start();
		carsLeavingThread.start();

		// Wait for the threads to finish
		try {
			carEnteringThread.join();
			carsLeavingThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
