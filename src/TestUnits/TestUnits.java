package TestUnits;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import Car.Car;
import ChargingStation.ChargingStation;
import ChargingStation.EnergySource;
import Weather.WeatherCondition;

public class TestUnits {
    
    @Test  
    public void CarCreation(){  
        var car = new Car(101, 2);
        assertEquals(101, car.get_id());  
    }  

    @Test  
    public void CarCharging(){  
        var car = new Car(11, 2);
        car.set_charging(false);
        assertEquals(false, car.is_charging());  
    }  

    
    //-------------------------------
    
        
    @Test  
    public void ChargingStationCreation(){  
        var chargingStation =  new ChargingStation(1111);
        assertEquals(1111, chargingStation.get_id());  
    }  


    @Test  //will fail (Expected [RAINING] but was [CLOUDY])
    public void ChargingStationWeather(){  
        var chargingStation =  new ChargingStation(1111);
        chargingStation.set_currentWeather(WeatherCondition.CLOUDY);
        assertEquals(WeatherCondition.RAINING, chargingStation.get_currentWeather());  
    }  
    
    //-------------------------------

    @Test  
    public void LocationEnergySource(){  
        var chargingStation =  new ChargingStation(1111);
     
        chargingStation.set_energySource(EnergySource.ELECTRICITY);
        assertEquals(EnergySource.ELECTRICITY, chargingStation.get_energySource());  
    }  

}
