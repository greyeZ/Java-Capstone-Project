package ChargingStation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Car.Car;
import Weather.WeatherCondition;

public class ChargingStation {
    private Location[] _locations;
    private int _id;
    private WeatherCondition _currentWeather;
    private final BlockingQueue<Car> queue = new LinkedBlockingQueue<>();

    public ChargingStation(int amountOfLocations, int id) {
        this._locations = new Location[amountOfLocations];
        fillLocationArray(amountOfLocations);
        this._id = id;
        createLogFile();
        startCharging();
    }

    private void fillLocationArray(int amountOfLocations) {
        for (int i = 0; i < amountOfLocations; i += 1) {
            get_locations()[i] = new Location();
        }
    }

    public Location[] get_locations() {
        return _locations;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public WeatherCondition get_currentWeather() {
        return _currentWeather;
    }

    public void set_currentWeather(WeatherCondition _currentWeather) {
        this._currentWeather = _currentWeather;
        changeEnergySource();
        logChanges("Weather changed to: " + _currentWeather);
    }

    public void useChargingLocation(Car car) {
        for (int i = 0; i < get_locations().length; i += 1) {
            if (!get_locations()[i].is_inUse()) {
                get_locations()[i].set_inUse(true);
                get_locations()[i].set_carInLocation(car);
                car.set_charging(true);
                return; // Exit the method if a location is successfully assigned
            }
        }
        addVehicleToQueue(car); // No available location, add to the queue
    }

    private void addVehicleToQueue(Car car) {
        queue.add(car);
    }

    private void startCharging() {
        for (int i = 0; i < get_locations().length; i += 1) {
            final int stationIndex = i;
            new Thread(() -> {
                while (true) {
                    try {
                        Car car = queue.take(); 
                        get_locations()[stationIndex].set_inUse(true);
                        get_locations()[stationIndex].set_carInLocation(car);
                        car.set_charging(true);
                        chargeVehicle(car);
                        get_locations()[stationIndex].set_inUse(false);
                        get_locations()[stationIndex].set_carInLocation(null);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }).start();
        }
    }


    private void chargeVehicle(Car car) throws InterruptedException {
        Thread.sleep((long) car.getChargingTime()); 
        System.out.println("Vehicle " + car.get_id() + " charged at ChargingStation " + get_id());
        car.set_charging(false);
    }

    private void changeEnergySource() {
        switch (get_currentWeather()) {
            case SUNNY:
                updateEnergySourceForLocation(EnergySource.SOLAR);
                break;
            case WINDY:
                updateEnergySourceForLocation(EnergySource.WIND);
                break;
            case RAINING:
                updateEnergySourceForLocation(EnergySource.ELECTRICITY);
                break;
            case CLOUDY:
                updateEnergySourceForLocation(EnergySource.WIND);
                break;
            case SNOWING:
                updateEnergySourceForLocation(EnergySource.GAS);
                break;
            default:
                updateEnergySourceForLocation(EnergySource.SOLAR);
        }
    }

    private void updateEnergySourceForLocation(EnergySource newSource) {
        for (Location location : get_locations()) {
            location.set_energySource(newSource);
        }
        logChanges("EnergySource changed to: " + newSource);
    }

    private void logChanges(String text) {
        System.out.println("Writing to file...");
        // TODO Logic for writing to existing file, currently overwriting the old one
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("ChargingStation" + get_id() + "log.txt", true));
            writer.newLine();
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to file");
        }
    }

    private void createLogFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("ChargingStation" + get_id() + "log.txt"));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
