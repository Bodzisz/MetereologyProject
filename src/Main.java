
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    public static void clearScreen()
    {
        for(int i = 0; i < 30; i++)
        {
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        User user = new User();
        CSI csi = new CSI("Locations.json");
        csi.addUser(user);
        Sensors sensors = new Sensors(csi, new WeatherFactory());
        Scanner scanner = new Scanner(System.in);


        Thread weather = new Thread(sensors);
        weather.start();

        MenuCreator mainMenu = new MenuCreator(new ArrayList<String>(
            Arrays.asList("Subscribe location", "Unsubscribe location",
                "Data preview", "Analyze data", "Exit")));

        ArrayList<String> allLocationsArray = new ArrayList();
        for(Location l : csi.getLocations())
        {
            allLocationsArray.add(l.getCity());
        }
        MenuCreator allLocationsMenu = new MenuCreator(allLocationsArray);
        MenuCreator weatherHistoryMenu;


        boolean exit = false;
        while(!exit)
        {
            mainMenu.printLines();
            System.out.print("\n");
            int choice = mainMenu.getChoice();
            clearScreen();

            switch (choice)
            {
                case 1 -> {
                    allLocationsMenu.printLines();
                    choice = allLocationsMenu.getChoice();

                    try {
                        if (user.getSubs().contains(csi.getLocations().get(choice - 1))) {
                            System.out.println("You've already subscribed this location");
                            Thread.sleep(2000);
                        } else {
                            csi.addUserLocation(user, csi.getLocations().get(choice - 1));
                        }
                    } catch(IndexOutOfBoundsException e)
                    {
                        System.out.println("Wrong input");
                        Thread.sleep(1000);
                    }
                    clearScreen();
                }
                case 2 -> {
                    int i = 1;
                    for(Location l : user.getSubs())
                    {
                        System.out.println(i + ". " + l.getCity());
                        i++;
                    }
                    choice = allLocationsMenu.getChoice();
                    try {
                        Location chosen = (Location)user.getSubs().toArray()[choice-1];
                        csi.removeUserLocation(user, chosen);
                    } catch (IndexOutOfBoundsException e)
                    {
                        System.out.println("Wrong input");
                        Thread.sleep(1500);
                    }
                    clearScreen();
                }
                case 3 -> {
                    System.out.println("1. Actual weather data");
                    System.out.println("2. All the weather data");
                    choice = mainMenu.getChoice();
                    clearScreen();

                    switch (choice)
                    {
                        case 1 -> user.getSubs().forEach((System.out::println));
                        case 2 -> {
                            user.getWeatherHistory().forEach((city, weathers) ->
                            {
                                System.out.println(city + ":");
                                for(Weather w : weathers)
                                {
                                    System.out.println(w);
                                }
                                System.out.println();
                            });
                        }
                    }
                    System.out.println("\nType 'q' and press ENTER to go back");
                    scanner.nextLine();
                    clearScreen();
                }
                case 4 -> {
                    ArrayList<String> cities = new ArrayList<>(user.getWeatherHistory().keySet());
                    weatherHistoryMenu = new MenuCreator(cities);
                    weatherHistoryMenu.printLines();
                    choice = weatherHistoryMenu.getChoice();
                    clearScreen();

                    System.out.println("Which value to analyze: ");
                    System.out.println("1. Temperature");
                    System.out.println("2. Humidity");
                    System.out.println("3. Pressure");

                    int choice2 = weatherHistoryMenu.getChoice();
                    System.out.println();

                    try {
                        user.getAnalyzedData(weatherHistoryMenu.getLines().get(choice - 1), choice2);
                    } catch(IndexOutOfBoundsException e)
                    {
                        System.out.println("Wrong input");
                        Thread.sleep(1500);
                    }
                    finally {
                        System.out.println("\nType 'q' and press ENTER to go back");
                        scanner.nextLine();
                        clearScreen();
                    }
                }
                case 5 -> { sensors.exit(); exit = true; }
            }
        }
    }
}
