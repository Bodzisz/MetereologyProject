import java.util.*;

public class CSI implements Observable{

    private ArrayList<Location> locations;
    private final HashMap<User, HashSet<Location>> usersLocations = new HashMap<>();

    public CSI(String path)
    {
        this.locations = (ArrayList) JsonParser.getLocations(path);
    }

    @Override
    public void notifyObservers()
    {
        for(Location l : locations)
        {
            for(User u : usersLocations.keySet())
            {
                if(usersLocations.get(u).contains(l))
                {
                    u.update(l);
                }
            }
        }
    }

    public void addUser(User user)
    {
        usersLocations.put(user, new HashSet<>());
    }

    public void removeObserver(User user)
    {
        usersLocations.remove(user);
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void addUserLocation(User u, Location l)
    {
        usersLocations.get(u).add(l);
        u.addLocation(l);
    }

    public void removeUserLocation(User u, Location l)
    {
        usersLocations.get(u).remove(l);
        u.removeLocation(l);
    }

    public HashMap<User, HashSet<Location>> getUsersLocations() {
        return usersLocations;
    }
}
