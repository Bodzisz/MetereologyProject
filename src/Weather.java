import com.google.gson.annotations.Expose;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Objects;

public class Weather {

    @Expose
    private double temperature;
    @Expose
    private double humidity;
    @Expose
    private double pressure;
    @Expose(serialize = false)
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Expose
    private String date = LocalDateTime.now().format(formatter);

    public Weather(double temperature, double humidity, double pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    @Override
    public String toString() {
        return String.format("Temperature: %2.2f oC, Humidity: %2.2f %%, Pressure: %3.2f hPa  ",
                temperature, humidity, pressure)  +  date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Double.compare(weather.temperature, temperature) == 0 &&
                Double.compare(weather.humidity, humidity) == 0 &&
                Double.compare(weather.pressure, pressure) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, humidity, pressure);
    }
}
