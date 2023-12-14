import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        ChargingStation[] chargingStations = {
                new ChargingStation(5, 1111),
                new ChargingStation(7, 2222),
                new ChargingStation(4, 3333)
        };

        Car[] cars = {
                new Car(11, 3, 2),
                new Car(12, 1, 1),
                new Car(13, 4, 3),
                new Car(14, 2, 3),
                new Car(15, 5, 5)
        };

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
