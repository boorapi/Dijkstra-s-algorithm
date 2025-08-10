import javax.swing.*;
import java.awt.*;

public class GraphicPanel extends JPanel{
    
    final int RADIUS = 25;// radius of the node
    private Graph graph;

    //set the graph object only once so when repaint the method don't reset all the data
    public void setGraph(Graph graph){
        this.graph = graph;// set the graph object
    }

    //This method will draw the node on the canvas by passing the node and its number
    public void drawNode(Graphics2D g2d, Nodes node, int number){
        int x = node.locationX();// get the x coordinate of the node
        int y = node.locationY();// get the y coordinate of the node
        g2d.setColor(node.getColor());// set the color of the node
        g2d.fillOval(x-RADIUS, y-RADIUS, RADIUS*2, RADIUS*2);//draw the node
        //draw outline ring around the node
        g2d.setColor(node.getOutline());
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(x-RADIUS, y-RADIUS, RADIUS*2, RADIUS*2);
        // draw the number inside the node
        g2d.setColor(new Color(255, 255, 255));
        g2d.setFont(new Font("Arial", Font.BOLD, 14));// set the font of the number inside the node
        //If node number is two digits chage to offset si they are in the middle of the node
        if(number < 10){
            g2d.drawString(number+"", x-4, y+5);// draw the number inside the node)
        } else{
            g2d.drawString(number+"", x-8, y+5);// draw the number inside the node)
        }

    }
    //This method will draw edegs on to canvas by giving egde object
    public void drawEdge(Edges edge, Graphics2D g2d){
        Nodes startNode = edge.getStartNode();// get the start node of the edge
        Nodes endNode = edge.getEndNode();// get the end node of the edge
        //start coordinate
        int xStart = startNode.locationX();
        int yStart = startNode.locationY();
        //end coordinate
        int xEnd = endNode.locationX();
        int yEnd = endNode.locationY();
        g2d.setColor(edge.getColor());// set the color of the edge
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(xStart, yStart, xEnd, yEnd);// draw the line between the two nodes 
    }

    //I need this method to draw the weight of the edge in the middle of the line
    //I need a separate method to draw the weight of the edges because I have to draw all the edges first
    //and then draw the weight so the text won't overlap with the edges
    public void drawWeight(Graphics2D g2d, int weight, Nodes start, Nodes end){
        int xStart = start.locationX();
        int yStart = start.locationY();
        int xEnd = end.locationX();
        int yEnd = end.locationY();
        g2d.setColor(new Color(0, 0, 0));// set the color of the edge
        g2d.setFont(new Font("Arial", Font.BOLD, 16));// set the font for the edge weight
        g2d.drawString(weight+"",(xStart + xEnd)/2, (yStart + yEnd)/2);// draw the weight of the edge in the middle of the line
    }
    //This method will paint(draws) all the nodes and edges onto the canvas
    public void paintComponent(Graphics g){
        super.paintComponent(g);// call the paint method to paint
        Graphics2D g2d = (Graphics2D) g;// cast the graphics to Graphics2D

        if(graph == null) return; // if graph is not set, return

        //draw edges
        for(Edges edge : graph.getEdgesList()){
            drawEdge(edge, g2d);// draw the edge on the canvas
        }

        // draw node
        int number = 1;
        for(Nodes node : graph.getNodesList()){
            drawNode(g2d, node, number);// draw the node on the canvas
            number++;// increment the number for the next node
        
        }

        // draw weight
        for(int i=0; i<graph.edgesAmount(); i++){
            Edges edge = graph.getEdgesList().get(i);// get the edge from the edgesList
            Nodes startNode = edge.getStartNode();// get the start node of the edge
            Nodes endNode = edge.getEndNode();// get the end node of the edge
            int weight = edge.getWeight();// get the weight of the edge
            drawWeight(g2d, weight, startNode, endNode);// draw the weight of the edge on the canvas
        }

    }
    //Constructor, set the canvas background.
    public GraphicPanel(){
        setBackground(new Color(152, 163, 170));// set the background color of the panel
     }

}