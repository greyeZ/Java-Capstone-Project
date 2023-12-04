package Services;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import Car.Car;

public class ChargingQueue {

    public ChargingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    private final int maxSize;

    final static long allowedTime = 900000; // 15 mins = 900,000 milliseconds

    private Queue<ChargingQueueCar> carsQueue = new LinkedList<>();
    private Timer timer = new Timer();

    private final Object lock = new Object(); // for multithreading purpose

    public void charge(Car car) {
        synchronized (lock) {

            while (carsQueue.size() >= maxSize) {
                try {
                    System.out.println("No charging spot is available now, waiting....");
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }

            ChargingQueueCar chargingQueueCar = new ChargingQueueCar(car);
            var response = carsQueue.add(chargingQueueCar);
            if (response) {
                System.out.println("Car ID#" + chargingQueueCar.getCar().get_id() + " is charging!");
            }

            // remove the car after 15 mins
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    leave();
                }
            }, allowedTime);

            // notify other threads
            lock.notifyAll();
        }
    }

    public Car leave() {
        synchronized (lock) {
            while (carsQueue.isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }

            ChargingQueueCar queueCar = carsQueue.peek();
            if (queueCar.isFinished()) {
                Car car = carsQueue.poll().getCar();
                // notify other threads
                lock.notifyAll();
                return car;
            } else {
                    // If there is no out of time cars, we wait for the next one to leave
                try {
                    lock.wait(queueCar.getExpirationTime() - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    public boolean isEmpty() {
        synchronized (lock) {
            return carsQueue.isEmpty();
        }
    }

    private static class ChargingQueueCar {
        private Car car;
        private long expirationTime;

        public ChargingQueueCar(Car car) {
            this.car = car;
            // If the car required waiting time should not exceeds the 15 mins allowed time
            long waitingTime = this.car.get_waitingTime() > allowedTime ? allowedTime : this.car.get_waitingTime();
            this.expirationTime = System.currentTimeMillis() + waitingTime;
        }

        public Car getCar() {
            return car;
        }

        public boolean isFinished() {
            return System.currentTimeMillis() >= expirationTime;
        }

        public long getExpirationTime() {
            return expirationTime;
        }
    }

}