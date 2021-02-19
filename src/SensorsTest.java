
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;


public class SensorsTest {
    CSI csi = new CSI("Locations.json");

    @Test
    public void weatherChangeTest() throws InterruptedException
    {
        WeatherFactory weatherFactory = mock(WeatherFactory.class);
        Sensors sensor = new Sensors(csi, weatherFactory);
        Thread thread = new Thread(sensor);

        Weather weather = new Weather(10,10,10);
        for(Location l : csi.getLocations())
        {
            when(weatherFactory.getNewWeather(l.getWeather())).thenReturn(weather);
        }

        thread.start();

        Thread.sleep(5100); // first change of weather from sensors is after 5 sec
        sensor.exit();

        for(Location l : csi.getLocations()) {
            assertEquals(l.getWeather().getTemperature(), weather.getTemperature(), 0.01);
            assertEquals(l.getWeather().getHumidity(), weather.getHumidity(), 0.01);
            assertEquals(l.getWeather().getPressure(), weather.getPressure(), 0.01);
        }
    }

    @Test
    public void exitTest() throws InterruptedException
    {
        Sensors sensor = new Sensors(csi, new WeatherFactory());
        Thread thread = new Thread(sensor);
        thread.start();
        sensor.exit();

        ArrayList<Weather> weatherBefore = new ArrayList<>();
        for(Location l : csi.getLocations())
        {
            weatherBefore.add(l.getWeather());
        }

        Thread.sleep(5100); // checking if after 5 sec sensor won't send new weather info

        for(int i = 0; i < weatherBefore.size(); i++)
        {
            assertEquals(weatherBefore.get(i), csi.getLocations().get(i).getWeather());
        }
    }

}
