import java.util.Random;
import java.math.*;

public class WeatherFactory {

    Random random = new Random();

    public Weather getNewWeather(Weather w)
    {
        double t = w.getTemperature() +  Math.cos(3.14 * random.nextDouble());
        double h = 100 * random.nextDouble();
        double p = 800 + (1200 - 800) * random.nextDouble();

        return new Weather(t,h,p);
    }
}
