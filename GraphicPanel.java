import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class GraphicPanel extends JPanel{
    
    final int radius = 25;// radius of the node

    //This method will draw the node on the canvas using the x and y coordinates
    public void drawNode(Graphics2D g2d, int x, int y, int number){
        g2d.setColor(new Color(50, 143, 168));// set the color of the node
        g2d.fillOval(x-radius, y-radius, radius*2, radius*2);//draw the node
        g2d.setColor(new Color(32, 38, 77));
        g2d.setStroke(new BasicStroke(3));// set the stroke of the node
        g2d.drawOval(x-radius, y-radius, radius*2, radius*2);// draw the outline of the node
        g2d.setColor(new Color(255, 255, 255));
        // draw the number inside the node
        g2d.setFont(new Font("Arial", Font.BOLD, 14));// set the font of the number inside the node
        g2d.drawString(number+"", x-4, y+5);// draw the number inside the node)
        }

    public void drawEdge(Graphics2D g2d, Nodes startNode, Nodes endNode,){
        int xStart = startNode.locationX();
        int yStart = startNode.locationY();
        int xEnd = endNode.locationX();
        int yEnd = endNode.locationY();
        g2d.setColor(new Color(230, 240, 240));// set the color of the edge
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(xStart, yStart, xEnd, yEnd);// draw the line between the two nodes 
        // draw the weight of the edge in the middle of the line
        g2d.setColor(new Color(0, 0, 0));// set the color of the edge
        g2d.setFont(new Font("Arial", Font.BOLD, 16));// set the font for the edge weight
        g2d.drawString(weight+"",((xStart + xEnd)/2), ((yStart + yEnd)/2));// draw the weight of the edge in the middle of the line
    }

    //I need this method to draw the weight of the edge in the middle of the line
    //I need a separate method to draw the weight of the edges because I have to draw all the edges first
    //and then draw the weight so the text won't overlap with the edges
    public void drawWeight(Graphics2D g2d, int weight, int x, int y){
        g2d.setColor(new Color(0, 0, 0));// set the color of the edge
        g2d.setFont(new Font("Arial", Font.BOLD, 16));// set the font for the edge weight
        g2d.drawString(weight+"", x, y);// draw the weight of the edge in the middle of the line
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);// call the paint method to paint
        Graphics2D g2d = (Graphics2D) g;// cast the graphics to Graphics2D

        Graph graph = new Graph();// create a new graph object
        graph.load_data();// load the data from the file

        //draw edges
        for(int i=0; i<graph.edgesAmount(); i++){
            Edges edge = graph.getEdgesList().get(i);// get the edge from the edgesList
            Nodes startNode = edge.getStartNode();// get the start node of the edge
            Nodes endNode = edge.getEndNode();// get the end node of the edge
            int weight = edge.getWeight();// get the weight of the edge
            drawEdge(g2d, startNode, endNode, weight);// draw the edge on the canvas
        }

        // draw node
        for(int i=0; i<graph.nodesAmount(); i++){
            Nodes node = graph.getNodesList().get(i);// get the node from the nodesList
            drawNode(g2d, node.locationX(), node.locationY(), i+1);// draw the node on the canvas
        
        }

    }

    public GraphicPanel(){
        setBackground(new Color(152, 163, 170));// set the background color of the panel
     }


}