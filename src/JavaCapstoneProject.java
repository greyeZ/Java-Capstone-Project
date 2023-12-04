import java.util.Random;

import Car.Car;
import ChargingStation.ChargingStation;
import ChargingStation.Location;
import Weather.WeatherCondition;

public class JavaCapstoneProject {

    public static void main(String[] args) {
        ChargingStation[] chargingStations = {
            new ChargingStation(5, 1111),
        };

        for (ChargingStation chargingStation : chargingStations) {
            chargingStation.set_currentWeather(changeWeather());
        }

        Car[] cars = {
            new Car(11, 3, 2),
            new Car(12, 1, 1),
            new Car(13, 4, 3),
            new Car(14, 2, 3),
            new Car(15, 5, 5),
            new Car(16, 4, 3),
            new Car(17, 2, 3),
            new Car(18, 5, 5),
            new Car(19, 3, 2),
            new Car(20, 1, 1),
            new Car(21, 4, 3),
            new Car(22, 2, 3),
            new Car(23, 5, 5),
            new Car(24, 4, 3),
            new Car(25, 2, 3),
        };

        // Simulate cars using the charging stations
        for (Car car : cars) {
            ChargingStation selectedStation = findAvailableChargingStation(chargingStations);
            if (selectedStation != null) {
                selectedStation.useChargingLocation(car);
                System.out.println("Car " + car.get_id() + " is charging at ChargingStation " + selectedStation.get_id());
            } else {
                System.out.println("No available charging station for Car " + car.get_id());
            }
        }
    }

    static WeatherCondition changeWeather() {
        // Create a Random object
        Random random = new Random();

        // Generate a random number between 1 and 5 (inclusive)
        int randomNumber = random.nextInt(5) + 1;
        switch (randomNumber) {
            case 1:
                return WeatherCondition.CLOUDY;
            case 2:
                return WeatherCondition.RAINING;
            case 3:
                return WeatherCondition.SNOWING;
            case 4:
                return WeatherCondition.SUNNY;
            case 5:
                return WeatherCondition.WINDY;
            default:
                return WeatherCondition.SUNNY;
        }
    }

    static ChargingStation findAvailableChargingStation(ChargingStation[] chargingStations) {
        for (ChargingStation chargingStation : chargingStations) {
            for (Location location : chargingStation.get_locations()) {
                if (!location.is_inUse()) {
                    return chargingStation;
                }
            }
        }
        return null;
    }
}
