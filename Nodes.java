
/**
 * This class repesent a single node in a graph, with position, name, color, 
 * connection information, and state tracking for pathfinding algorithms.
 * 
 * Each node stores its coordinates for display, links to neighboring nodes 
 * with edge weights, visual colors for rendering when animating and selecting node, and status variables 
 * such as cost and visited state.
 */
import java.awt.Color;//to handle color variable
import java.util.HashMap;

public class Nodes
{
    private String name;
    //x and y location of the node on the screen
    private int x;
    private int y;
    private HashMap<Nodes, Integer> linkTo;// neighboring nodes and their weights
    private Color color;
    private Color outline;
    private int cost;
    private boolean visited; // to check if the node has been visited in the algorithm

    
    //This constructior creates a new node with the given position and name.
    //Default colors are set, cost is set to infinity, and visited is set to false.
    public Nodes (int x, int y , String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.color = new Color(50, 143, 168);
        this.outline = new Color(10, 72, 130);
        this.cost = Integer.MAX_VALUE;// initially, cost ti reach nodee is infinite
        this.visited = false; // initially, the node is not visite
        this.linkTo = new HashMap<Nodes, Integer>();
        
    }
    //add link to node by giving the node that they are link to and weight
    public void addLink(Nodes node, int weight) {
        linkTo.put(node, weight);
    }
    //return the name of the node
    public String getName() {
        return name;
    }
    //return x location
    public int locationX() {
        return x;
    }
    //return y location
    public int locationY() {
        return y;
    }
    //return current color state
    public Color getColor(){
        return color;
    }
    //return current outline color
    public Color getOutline(){
        return outline;
    }
    //This method set the color of nodes acoording to the given String command 
    public void setColorStatus(String status){
        if(status.equals("useNode")){
            this.color = new Color(71, 158, 78);
            this.outline = new Color(16, 97, 20);
        } else if(status.equals("processing")){
            this.color = new Color(196, 47, 47);
            this.outline = new Color(112, 3, 7);
        }else if(status.equals("source")){
            this.color = new Color(214, 187, 32);
            this.outline = new Color(10, 72, 130);
        }else if (status.equals("destination")){
            this.color = new Color(59, 163, 86);
            this.outline = new Color(10, 72, 130);
        } else {
            this.color = new Color(50, 143, 168);
            this.outline = new Color(10, 72, 130);
        }
    }
    //this method will set the cost to reach this node to the given value
    public void setCost(int n){
        this.cost = n;
    }
    //return the cost
    public int getCost(){
        return cost;
    }
    //set visited status 
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    //get the visit status
    public boolean getStatus(){
        return visited;
    }
    //return the link to HashMap 
    public HashMap<Nodes, Integer> getLinks() {
        return linkTo;
    }
}


