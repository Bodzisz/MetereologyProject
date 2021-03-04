package tests;

import org.junit.Assert;
import org.junit.Test;
import weather.*;


import java.util.Arrays;
import java.util.Iterator;
import java.util.*;

import static org.junit.Assert.*;

public class UserCSITests {

    User user = new User();
    CSI csi = new CSI("Locations.json");

    @Test
    public void addRemoveUserTest()
    {
        csi.addUser(user);
        assertTrue(csi.getUsersLocations().containsKey(user));

        csi.removeObserver(user);
        assertFalse(csi.getUsersLocations().containsKey(user));
    }

    @Test
    public void notifyObserversTest()
    {
        csi.addUser(user);
        csi.addUserLocation(user, csi.getLocations().get(0));
        Weather newWeather = new Weather(10,10,10);
        csi.getLocations().get(0).updateWeather(newWeather);
        csi.notifyObservers();

        Iterator<Location> setIterator = user.getSubs().iterator();
        assertEquals(csi.getLocations().get(0).getWeather(), setIterator.next().getWeather());
        assertTrue(user.getWeatherHistory().get(csi.getLocations().get(0).getCity()).contains(newWeather));
    }

    @Test
    public void addLocationTest()
    {
        Location l = csi.getLocations().get(1);
        user.addLocation(l);
        user.addLocation(l);

        assertTrue(user.getSubs().contains(l));
        Assert.assertEquals(user.getSubs().size(), 1);
        assertTrue(user.getWeatherHistory().get(l.getCity()).contains(l.getWeather()));
    }

    @Test
    public void addRemoveUserLocationTest()
    {
        User user2 = new User();

        csi.addUser(user2);
        csi.addUserLocation(user2, csi.getLocations().get(0));

        assertTrue(csi.getUsersLocations().get(user2).contains(csi.getLocations().get(0)));

        csi.removeUserLocation(user2, csi.getLocations().get(1));
        assertTrue(csi.getUsersLocations().get(user2).contains(csi.getLocations().get(0)));

        csi.removeUserLocation(user2, csi.getLocations().get(0));
        assertFalse(csi.getUsersLocations().get(user2).contains(csi.getLocations().get(0)));
    }


    @Test
    public void getAnalyzedDataTest()
    {
        User user2 = new User();
        Location l = new Location("TestCity", new Weather(20,50,1000));

        user2.addLocation(l);

        l.updateWeather(new Weather(10,10,900));
        user2.update(l);

        Assert.assertEquals(user2.getAnalyzedData(l.getCity(),1), new ArrayList<Double>(Arrays.asList(10.0, 20.0, 15.0)));
        Assert.assertEquals(user2.getAnalyzedData(l.getCity(),2), new ArrayList<Double>(Arrays.asList(10.0, 50.0, 30.0)));
        Assert.assertEquals(user2.getAnalyzedData(l.getCity(),3), new ArrayList<Double>(Arrays.asList(900.0, 1000.0, 950.0)));
    }

    @Test
    public void testAnalyzedDataWrongInput()
    {
        assertNull(user.getAnalyzedData("Not a city", 1));
        assertNull(user.getAnalyzedData("Not a city", 2));
        assertNull(user.getAnalyzedData("Not a city", 3));
        assertNull(user.getAnalyzedData("Not a city", 1234));
    }


}
