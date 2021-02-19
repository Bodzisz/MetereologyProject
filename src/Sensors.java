import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sensors implements  Runnable {

    CSI csi;
    private AtomicBoolean exit = new AtomicBoolean(false);
    WeatherFactory weatherFactory;

    public Sensors(CSI csi, WeatherFactory weatherFactory) {
        this.csi = csi;
        this.weatherFactory = weatherFactory;
    }

    @Override
    public void run() {
        while (!exit.get()) {
            try {
                Thread.sleep(5000);
            }
            catch(InterruptedException ie)
            {
                ie.printStackTrace();
            }

            for (Location l : csi.getLocations()) {
                l.updateWeather(weatherFactory.getNewWeather(l.getWeather()));
            }
            csi.notifyObservers();

        }
    }

    void exit()
    {
        exit.set(true);
        System.out.println("Getting sensor's measurments stopped");
    }
}
