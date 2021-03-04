package weather;

import com.google.gson.annotations.Expose;
import weather.Location;
import weather.Observer;
import weather.Weather;
import json.JsonParser;

import java.util.*;

public class User implements Observer {

    private final LinkedHashSet<Location> subs = new LinkedHashSet<>();
    @Expose
    private final LinkedHashMap<String, ArrayList<Weather>> weatherHistory = new LinkedHashMap<>();


    @Override
    public void update(Location location) {
        weatherHistory.get(location.getCity()).add(location.getWeather());
        JsonParser.serializeHashMap(weatherHistory,"dataRegister.json");
    }

    public void addLocation(Location l)
    {
        subs.add(l);
        if(!weatherHistory.containsKey(l.getCity()))
        {
            weatherHistory.put(l.getCity(), new ArrayList<>());
            weatherHistory.get(l.getCity()).add(l.getWeather());
        }
    }

    public void removeLocation(Location l)
    {
        subs.remove(l);
    }

    public HashSet<Location> getSubs() {
        return subs;
    }

    public HashMap<String, ArrayList<Weather>> getWeatherHistory() {
        return weatherHistory;
    }


    public ArrayList<Double> getAnalyzedData(String city, int option) // 1 - temp, 2 - humidity, 3 - pressure
    {
        if(weatherHistory.containsKey(city)) {
            ArrayList<Weather> weathers = weatherHistory.get(city);
            double min = 2000;
            double max = -100;
            double avg = 0;
            double sum = 0;

            ArrayList<Double> tmp = new ArrayList<>();
            switch (option)
            {
                case 1 -> weathers.forEach(w -> tmp.add(w.getTemperature()));
                case 2 -> weathers.forEach(w -> tmp.add(w.getHumidity()));
                case 3 -> weathers.forEach(w -> tmp.add(w.getPressure()));
                default -> { return null; }
            }
            for (double var : tmp) {
                if (var < min) {
                    min = var;
                }
                if (var > max) {
                    max = var;
                }
                sum += var;
            }
            avg = sum / (weathers.size());
            System.out.printf("Max: %2.2f\n", max);
            System.out.printf("Min: %2.2f\n", min);
            System.out.printf("Avg: %2.2f\n", avg);
            return new ArrayList<Double>(Arrays.asList(min, max, avg));
        }
        return null;
    }
}
