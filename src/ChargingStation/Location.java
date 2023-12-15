package ChargingStation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Car.Car;

public class Location {
	private static final Logger logger = LogManager.getLogger("EnergySourceLogger");


    private EnergySource _energySource;
    private boolean _inUse;
    private Car _carInLocation;
    private double _startChargingTime;

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
        logEnergySourceChange();
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

    private void logEnergySourceChange() {
        logger.info("EnergySource changed to: {}", _energySource);
    }
}
