import java.util.Random;

import Car.Car;
import ChargingStation.ChargingStation;
import Weather.WeatherCondition;

public class JavaCapstoneProject {

	public static void main(String[] args) {
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
		for(ChargingStation chargingStation: chargingStations) {
			chargingStation.set_currentWeather(changeWeather());
		}
		
		//TODO Car is using a Location of a CHargingStation
	}
	
	static WeatherCondition changeWeather() {
		 // Create a Random object
        Random random = new Random();

        // Generate a random number between 1 and 5 (inclusive)
        int randomNumber = random.nextInt(5) + 1; 
        try {
        	 switch(randomNumber) {
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
             default: throw new Exception("Something went wrong");
             }
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
		return WeatherCondition.SUNNY;
       
	}

}
