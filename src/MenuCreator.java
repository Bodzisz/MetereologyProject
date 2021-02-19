
import java.util.ArrayList;
import java.util.Scanner;

public class MenuCreator {

    private ArrayList<String> lines;

    MenuCreator(ArrayList<String> lines)
    {
        this.lines = lines;
    }

    public int getChoice()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Your choice: ");
        try
        {
            return scanner.nextInt();
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    void printLines()
    {
        for(int i = 0; i < lines.size(); i++)
        {
            System.out.println((i+1) + ". " + lines.get(i));
        }
    }

    void addLine(String line)
    {
        lines.add(line);
    }

    public ArrayList<String> getLines() {
        return lines;
    }
}
