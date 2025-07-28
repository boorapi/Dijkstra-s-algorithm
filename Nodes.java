
/**
 * Write a description of class Nodes here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.awt.Color;
public class Nodes
{
    private String name;
    //x and y location of the node on the screen
    private int x;
    private int y;
    private ArrayList<Nodes> linkTo;
    private Color color;
   
    public Nodes (int x, int y , String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.color = new Color(50, 143, 168);
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

    public Color getColor(){
        return color;
    }

    public void setColor(int r, int g,int b){
        this.color = new Color(r, g, b);
    }

}


