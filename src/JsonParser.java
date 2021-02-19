import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {

    public static List<Location> getLocations(String path)
    {
        Gson gson = new Gson();
        String lines = "";

        try(BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            while(br.ready())
            {
                lines += br.readLine();
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        Type listType = new TypeToken<ArrayList<Location>>(){}.getType();
        return gson.fromJson(lines, listType);
    }

    public static void serializeHashMap(HashMap<String, ArrayList<Weather>> weatherHistory, String path)
    {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        Type mapType = new TypeToken<HashMap<String, Number>>() {}.getType();
        String json = gson.toJson(weatherHistory, mapType);

        try( BufferedWriter bw = new BufferedWriter(new FileWriter(path)))
        {
            bw.write(json);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
