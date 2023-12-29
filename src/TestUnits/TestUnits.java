package TestUnits;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import Car.Car;
import ChargingStation.ChargingStation;
import ChargingStation.EnergySource;
import ChargingStation.Location;
import Weather.WeatherCondition;

public class TestUnits {
    
    @Test  
    public void CarCreation(){  
        var car = new Car(101, 3, 2);
        assertEquals(101, car.get_id());  
    }  

    @Test  
    public void CarCharging(){  
        var car = new Car(11, 3, 2);
        car.set_charging(false);
        assertEquals(false, car.is_charging());  
    }  

    @Test  
    public void CarWaitingTime(){  
        var car = new Car(11, 3, 2);
        assertEquals(2, car.get_waintingTime());  
    }  

    
    //-------------------------------
    
        
    @Test  
    public void ChargingStationCreation(){  
        var chargingStation =  new ChargingStation(5, 1111);
        assertEquals(1111, chargingStation.get_id());  
    }  

    @Test  
    public void ChargingStationLocations(){  
        var chargingStation =  new ChargingStation(5, 1111);
        var locations = chargingStation.get_locations();
        assertEquals(5, locations.length);  
    }  

    @Test  //will fail (Expected [RAINING] but was [CLOUDY])
    public void ChargingStationWeather(){  
        var chargingStation =  new ChargingStation(5, 1111);
        chargingStation.set_currentWeather(WeatherCondition.CLOUDY);
        assertEquals(WeatherCondition.RAINING, chargingStation.get_currentWeather());  
    }  
    
    //-------------------------------

    @Test  
    public void LocationEnergySource(){  
        var chargingStation =  new ChargingStation(5, 1111);
        var location1 = chargingStation.get_locations()[0];
        location1.set_energySource(EnergySource.ELECTRICITY);
        assertEquals(EnergySource.ELECTRICITY, location1.get_energySource());  
    }  

    @Test  //will fail (Expected [false] but was [true])
    public void LocationInUse(){  
        var chargingStation =  new ChargingStation(5, 1111);
        var location1 = chargingStation.get_locations()[0];
        location1.set_inUse(true);
        assertEquals(false, location1.is_inUse());  
    }  

    @Test 
    public void LocationChargingTime(){  
        var chargingStation =  new ChargingStation(5, 1111);
        var location1 = chargingStation.get_locations()[0];
        location1.set_startChargingTime(200);
        assertEquals(200, location1.get_startChargingTime());  
    }  

    @Test  
    public void LocationCar(){  
        var chargingStation =  new ChargingStation(5, 1111);
        var location1 = chargingStation.get_locations()[0];
        var car1 = new Car(101, 3, 2);
        location1.set_carInLocation(car1);
        assertEquals(101, location1.get_carInLocation().get_id());  
    }  
    
}
