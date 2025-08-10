/**
 * This class repesent a single edge in a graph, with position(node start node end), name, and color 
 *
 */
import java.awt.Color;

public class Edges {
    private Nodes startNode;// start node of this edge
    private Nodes endNode;// end node of this edge
    private int weight;// weight or cost of this edge
    private Color color;// color used to display the edge

    public Edges(Nodes start, Nodes end, int weight) {
        this.startNode = start;// set start node
        this.endNode = end;// set end node
        this.weight = weight;// set weight of edge
        this.color = new Color(230, 240, 240); // default color for edges
    }
    //Return start node for this edge
    public Nodes getStartNode() {
        return startNode;  
    }
    //Return end node for this edge
    public Nodes getEndNode() {
        return endNode;
    }
    // Return weight of edge
    public int getWeight() {
        return weight;
    }
    //Retunr current color
    public Color getColor() {
        return color; 
    }

    // Set color based on edge status by passing string
    public void setColorStatus(String status){
        if(status.equals("usePath")){
            this.color = new Color(71, 158, 78);
        } else if(status.equals("processing")){
            this.color = new Color(196, 47, 47); 
        } else {
            this.color = new Color(230, 240, 240); 
        }
    }
}