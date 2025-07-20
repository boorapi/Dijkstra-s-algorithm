
/**
 * Write a description of class Nodes here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
public class Nodes
{
    private String name;
    //x and y location of the node on the screen
    private int x;
    private int y;
    private ArrayList<Nodes> linkTo;
    //Create new object from graph class to acess nodes arraylist
    
    public Nodes (int x, int y , String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        linkTo = new ArrayList<Nodes>();
        
    }

    public void addLink(Nodes node) {
        linkTo.add(node);
    }

    public String getName() {
        return name;
    }
    public int locationX() {
        return x;
    }
    public int locationY() {
        return y;
    }
}
