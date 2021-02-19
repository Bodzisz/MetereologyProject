import java.util.ArrayList;
import java.util.Objects;

public class Location {

    private String city;
    private Weather weather;

    public Location(String city, Weather weather) {
        this.city = city;
        this.weather = weather;
    }

    public void updateWeather(Weather weather) {
        this.weather = weather;
    }

    public Weather getWeather() {
        return weather;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return city + ": " + weather;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(city, location.city);
    }
}

