package Models;

import Helpers.Logger;

public class ChargingStation {
	private Location[] _locations;
	private int _id; 
	private WeatherCondition _currentWeather;
	
	
	public ChargingStation(int amountOfLocations, int id) {
		this._locations = new Location[amountOfLocations];
		fillLocationArray(amountOfLocations);
		this._id = id;
	}

	private void fillLocationArray(int amountOfLocations) {
		for(int i=0; i < amountOfLocations; i+=1) {
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
		Logger.log("Weather changed to: " + _currentWeather);
	}
	
	public void useChargingLocation(Car car) {
		for(int i=0; i < get_locations().length; i+=1) {
			if(!get_locations()[i].is_inUse()) {
				get_locations()[i].set_inUse(true);
				get_locations()[i].set_carInLocation(car);
				car.set_charging(true);
			}
		}
		if(!car.is_charging()) {
			for(int i=0; i < get_locations().length; i+=1) {
				//TODO Logic for checking if there is an available Location
			}
		}
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
		for(Location location: get_locations()) {
			location.set_energySource(newSource);
		}
		Logger.log("EnergySource changed to: " + newSource);
	}

	
}
