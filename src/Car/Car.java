package Car;

public class Car {

	private int _id;
	private double _chargingTime;
	private double _waintingTime;
	private boolean _charging;
	
	public Car(int id, double chargingTime, double waitingTime) {
		this._id = id;
		this._chargingTime = chargingTime;
		this._waintingTime = waitingTime;
		this.set_charging(false);
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public double getChargingTime() {
		return _chargingTime;
	}

	public void setChargingTime(double chargingTime) {
		this._chargingTime = chargingTime;
	}

	public double get_waintingTime() {
		return _waintingTime;
	}

	public void set_waintingTime(double _waintingTime) {
		this._waintingTime = _waintingTime;
	}

	public boolean is_charging() {
		return _charging;
	}

	public void set_charging(boolean _charging) {
		this._charging = _charging;
	}
}
