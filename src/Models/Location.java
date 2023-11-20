package Models;

public class Location {
	private EnergySource _energySource;
	private boolean _inUse;
	private Car _carInLocation;
	private double _startChargingTime; 
	private User _User;
	
	public User get_User() {
		return _User;
	}

	public void set_User(User _User) {
		this._User = _User;
	}

	Location() {
		this._inUse = false;
		this._carInLocation = null;
		this._startChargingTime = 0;
	}

	public EnergySource get_energySource() {
		return _energySource;
	}

	public void set_energySource(EnergySource _energySource) {
		this._energySource = _energySource;
	}

	public boolean is_inUse() {
		return _inUse;
	}

	public void set_inUse(boolean _inUse) {
		this._inUse = _inUse;
	}

	public Car get_carInLocation() {
		return _carInLocation;
	}

	public void set_carInLocation(Car _carInLocation) {
		this._carInLocation = _carInLocation;
	}

	public double get_startChargingTime() {
		return _startChargingTime;
	}

	public void set_startChargingTime(double _startChargingTime) {
		this._startChargingTime = _startChargingTime;
	}

}
