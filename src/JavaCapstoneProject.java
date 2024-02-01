import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Services.ChargingQueue;

import java.util.Random;
import java.util.Scanner;

import Car.Car;
import ChargingStation.ChargingStation;
import LogFileManager.LogFileManager;
import Weather.WeatherCondition;

public class JavaCapstoneProject {
    // Add a logger for the main class
    private static final Logger logger = LogManager.getLogger("SystemLogger");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create an instance of the LogFileManager
        LogFileManager logFileManager = new LogFileManager();

        // Log system start
        logger.info("JavaCapstoneProject started");

        // A Place with 3 ChargingStations
        ChargingStation[] chargingStations = {
                new ChargingStation(01),
                new ChargingStation(02),
                new ChargingStation(03)
        };

        // Different sets of cars for each charging station
        Car[][] carsForStations = {
                // Cars for ChargingStation 01
                {
                        new Car(11, 12000),
                        new Car(12, 600000),
                        new Car(13, 1500000), // exceeds 15 mins
                        new Car(14, 1200000), // exceeds 15 mins
                        new Car(15, 300000)
                },
                // Cars for ChargingStation 02
                {
                        new Car(21, 50000),
                        new Car(22, 800000),
                        new Car(23, 1200000),
                        new Car(24, 200000),
                        new Car(25, 600000)
                },
                // Cars for ChargingStation 03
                {
                        new Car(31, 200000),
                        new Car(32, 300000),
                        new Car(33, 400000),
                        new Car(34, 1000000),
                        new Car(35, 150000)
                }
        };

        // Create an array to store threads for each charging station
        Thread[] carEnteringThreads = new Thread[chargingStations.length];
        Thread[] carsLeavingThreads = new Thread[chargingStations.length];

        // Create and start threads for every Charging Station
        for (int i = 0; i < chargingStations.length; i++) {
            System.out.println("Starting Threads for Charging Station " + chargingStations[i].get_id());
            final int chargingQueueSize = 3;
            ChargingQueue chargingQueue = new ChargingQueue(chargingQueueSize);
            Random random = new Random();

            final int stationIndex = i;  // Create a separate variable for each thread

            carEnteringThreads[i] = new Thread(() -> {
                // Iterate over the cars for every car to charge
                for (int j = 0; j < carsForStations[stationIndex].length; j++) {
                    chargingQueue.charge(carsForStations[stationIndex][j]);

                    try {
                        // random interval to wait for the next coming car
                        long randomInterval = random.nextInt(7000) + 1000;
                        Thread.sleep(randomInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            carsLeavingThreads[i] = new Thread(() -> {
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
        }

        // Start all car entering threads
        for (Thread carEnteringThread : carEnteringThreads) {
            carEnteringThread.start();
        }

        // Start all cars leaving threads
        for (Thread carsLeavingThread : carsLeavingThreads) {
            carsLeavingThread.start();
        }

        // Wait for all the threads to finish
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

        for (ChargingStation chargingStation : chargingStations) {
            WeatherCondition newWeather = changeWeather();
            chargingStation.set_currentWeather(newWeather);
            logger.info("Charging Station {} - Current Weather: {}", chargingStation.get_id(), newWeather);
        }

        // Interactive console for user input
        while (true) {
            System.out.println("Enter a command ('openLogFile' to open a log file, 'exit' to exit): ");
            String command = scanner.nextLine();

            if ("exit".equalsIgnoreCase(command)) {
                break;
            }

            try {
                handleCommand(command, logFileManager);
            } catch (Exception e) {
                logger.error("Error handling command: " + command, e);
            }
        }

        // Log system end
        logger.info("JavaCapstoneProject ended");
    }

    private static void handleCommand(String command, LogFileManager logFileManager) {
        if ("openLogFile".equalsIgnoreCase(command)) {
            System.out.println("Enter the name of the equipment or date to open the log file:");
            String userInput = new Scanner(System.in).nextLine();
            System.out.println("Enter the type of equipment ('charging_station', 'energy_source', 'system'):");
            String equipmentType = new Scanner(System.in).nextLine();

            logFileManager.logFileExistsAndOpen(userInput, equipmentType);
        } else {
            System.out.println("Unknown command: " + command);
        }
    }

    static WeatherCondition changeWeather() {
        // Create a Random object
        Random random = new Random();

        // Generate a random number between 1 and 5 (inclusive)
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

        // Log the weather change
        logger.info("Weather changed to: {}", newWeather);

        return newWeather;
    }
}
