package Models;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Enum.WeatherCondition;
import Services.ChargingQueue;

public class ChargingStation extends Thread {
	private static final Logger logger = LogManager.getLogger("ChargingStationLogger");

    private int _id;
    private WeatherCondition _currentWeather;
    private EnergySource _energySource;
    
    final int chargingQueueSize = 3;
	ChargingQueue chargingQueue = new ChargingQueue(chargingQueueSize);
	Random random = new Random();

    public ChargingStation(int id) {

        this._id = id;
    }
    
    public EnergySource get_energySource() {
        return _energySource;
    }

    public void set_energySource(EnergySource _energySource) {
        this._energySource = _energySource;
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
    }

    private void changeEnergySource() {
        switch (get_currentWeather()) {
            case SUNNY:
                set_energySource(EnergySource.SOLAR);
                break;
            case WINDY:
            	set_energySource(EnergySource.WIND);
                break;
            case RAINING:
            	set_energySource(EnergySource.ELECTRICITY);
                break;
            case CLOUDY:
            	set_energySource(EnergySource.WIND);
                break;
            case SNOWING:
            	set_energySource(EnergySource.GAS);
                break;
            default:
            	set_energySource(EnergySource.SOLAR);
        }
        logEnergySourceChange();
    }

    private void logChanges(String text) {
        logger.info(text);
    }
    

    private void logEnergySourceChange() {
    	logger.info("Charging Station " + get_id() + " now uses " + get_energySource() + " as energy source");
    }
    
}
