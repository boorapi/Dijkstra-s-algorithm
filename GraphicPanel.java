import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class GraphicPanel extends JPanel{
    
    final int radius = 20;// radius of the node

    //This method will draw the node on the canvas using the x and y coordinates
    public void drawNode(Graphics2D g2d, int x, int y, int number){
        g2d.setColor(new Color(50, 143, 168));// set the color of the node
        g2d.fillOval(x-radius, y-radius, radius*2, radius*2);//draw the node
        g2d.setColor(new Color(32, 38, 77));
        g2d.setFont(new Font("Arial", Font.BOLD, 14));// set the font of the number inside the node
        g2d.drawString(number+"", x-4, y+5);// draw the number inside the node)
        }

    public void drawEdge(Graphics2D g2d, Nodes startNode, Nodes endNode, int weight){
        g2d.setColor(new Color(0, 0, 0));// set the color of the edge
        int xStart = startNode.locationX();
        int yStart = startNode.locationY();
        int xEnd = endNode.locationX();
        int yEnd = endNode.locationY();
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(xStart, yStart, xEnd, yEnd);// draw the line between the two nodes 
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));// set the font for the edge weight
        g2d.drawString(weight+"", (xStart + xEnd) / 2, (yStart + yEnd) / 2);// draw the weight of the edge in the middle of the line
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);// call the paint method to paint
        Graphics2D g2d = (Graphics2D) g;// cast the graphics to Graphics2D

        Graph graph = new Graph();// create a new graph object
        graph.load_data();// load the data from the file
        // draw node
        for(int i=0; i<graph.nodesAmount(); i++){
            Nodes node = graph.getNodesList().get(i);// get the node from the nodesList
            drawNode(g2d, node.locationX(), node.locationY(), i+1);// draw the node on the canvas
        
        }
        //draw edges
        for(int i=0; i<graph.edgesAmount(); i++){
            Edges edge = graph.getEdgesList().get(i);// get the edge from the edgesList
            Nodes startNode = edge.getStartNode();// get the start node of the edge
            Nodes endNode = edge.getEndNode();// get the end node of the edge
            int weight = edge.getWeight();// get the weight of the edge
            drawEdge(g2d, startNode, endNode, weight);// draw the edge on the canvas
        }

    }

    public GraphicPanel(){
        setBackground(new Color(204, 212, 224));// set the background color of the panel
     }


}