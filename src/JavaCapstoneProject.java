import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Services.ChargingQueue;

import java.util.Random;
import java.util.Scanner;

import Car.Car;
import ChargingStation.ChargingStation;
import Weather.WeatherCondition;
import LogFileManager.LogFileHandler;

public class JavaCapstoneProject {
    private static final Logger systemlogger = LogManager.getLogger("SystemLogger");
    private static final Logger stationlogger = LogManager.getLogger("ChargingStationLogger");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        systemlogger.info("JavaCapstoneProject started");

        ChargingStation[] chargingStations = {
                new ChargingStation(01),
                new ChargingStation(02),
                new ChargingStation(03)
        };

        Car[][] carsForStations = {
                {
                        new Car(11, 12000),
                        new Car(12, 600000),
                        new Car(13, 1500000),
                        new Car(14, 1200000),
                        new Car(15, 300000)
                },
                {
                        new Car(21, 50000),
                        new Car(22, 800000),
                        new Car(23, 1200000),
                        new Car(24, 200000),
                        new Car(25, 600000)
                },
                {
                        new Car(31, 200000),
                        new Car(32, 300000),
                        new Car(33, 400000),
                        new Car(34, 1000000),
                        new Car(35, 150000)
                }
        };

        Thread[] carEnteringThreads = new Thread[chargingStations.length];
        Thread[] carsLeavingThreads = new Thread[chargingStations.length];
        Thread[] weatherChangeThreads = new Thread[chargingStations.length];

        for (int i = 0; i < chargingStations.length; i++) {
        	systemlogger.info("Starting Threads for Charging Station " + chargingStations[i].get_id());
            final int chargingQueueSize = 3;
            ChargingQueue chargingQueue = new ChargingQueue(chargingQueueSize);
            Random random = new Random();

            final int stationIndex = i;

            weatherChangeThreads[i] = new Thread(() -> {
                while (true) {
                    try {
                        long randomInterval = random.nextInt(15000) + 5000;
                        Thread.sleep(randomInterval);

                        WeatherCondition newWeather = changeWeather();
                        chargingStations[stationIndex].set_currentWeather(newWeather);
                       
                        stationlogger.info("Charging Station {} - Current Weather: {}", chargingStations[stationIndex].get_id(), newWeather);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            weatherChangeThreads[i].start();

            carEnteringThreads[i] = new Thread(() -> {
                for (int j = 0; j < carsForStations[stationIndex].length; j++) {
                    chargingQueue.charge(carsForStations[stationIndex][j]);

                    try {
                        long randomInterval = random.nextInt(7000) + 1000;
                        Thread.sleep(randomInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            carsLeavingThreads[i] = new Thread(() -> {
                try {
                    Thread.sleep(10000);
                    while (!chargingQueue.isEmpty()) {
                        var outCar = chargingQueue.leave();
                        if (outCar != null) {
                        	stationlogger.info("Car ID#" + outCar.get_id() + " is leaving now...");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for (Thread carEnteringThread : carEnteringThreads) {
            carEnteringThread.start();
        }

        for (Thread carsLeavingThread : carsLeavingThreads) {
            carsLeavingThread.start();
        }

        Thread logFileThread = new Thread(LogFileHandler::startLogFileHandler);
        logFileThread.start();

        try {
            for (Thread carEnteringThread : carEnteringThreads) {
                carEnteringThread.join();
            }
            for (Thread carsLeavingThread : carsLeavingThreads) {
                carsLeavingThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Thread weatherChangeThread : weatherChangeThreads) {
            weatherChangeThread.interrupt();
        }

        systemlogger.info("JavaCapstoneProject ended");
    }

    static WeatherCondition changeWeather() {
        Random random = new Random();
        int randomNumber = random.nextInt(5) + 1;
        WeatherCondition newWeather;
        switch (randomNumber) {
            case 1:
                newWeather = WeatherCondition.CLOUDY;
                break;
            case 2:
                newWeather = WeatherCondition.RAINING;
                break;
            case 3:
                newWeather = WeatherCondition.SNOWING;
                break;
            case 4:
                newWeather = WeatherCondition.SUNNY;
                break;
            case 5:
                newWeather = WeatherCondition.WINDY;
                break;
            default:
                newWeather = WeatherCondition.SUNNY;
                break;
        }

        systemlogger.info("Weather changed to: {}", newWeather);

        return newWeather;
    }
}
