
/**
 * Write a description of class Nodes here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.Color;
import java.util.HashMap;
public class Nodes
{
    private String name;
    //x and y location of the node on the screen
    private int x;
    private int y;
    private HashMap<Nodes, Integer> linkTo;// neighboring nodes and their weights
    private Color color;
    private int cost;
    private boolean visited; // to check if the node has been visited in the algorithm
   
    public Nodes (int x, int y , String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.color = new Color(50, 143, 168);
        this.cost = Integer.MAX_VALUE;
        this.visited = false; // initially, the node is not visite
        this.linkTo = new HashMap<Nodes, Integer>();
        
    }

    public void addLink(Nodes node, int weight) {
        linkTo.put(node, weight);
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

    public void setColorStatus(String status){
        if(status.equals("useNode")){
            this.color = new Color(71, 158, 78);
        } else if(status.equals("processing")){
            this.color = new Color(196, 47, 47);
        }else if(status.equals("source")){
            this.color = new Color(214, 187, 32);
        }else if (status.equals("destination")){
            this.color = new Color(59, 163, 86);
        } else {
            this.color = new Color(50, 143, 168);
        }
    }

    public void setCost(int n){
        this.cost = n;
    }

    public int getCost(){
        return cost;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean getStatus(){
        return visited;
    }

    public HashMap<Nodes, Integer> getLinks() {
        return linkTo;
    }
}


