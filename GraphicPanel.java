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

    public void paintComponent(Graphics g){
        super.paintComponent(g);// call the paint method to paint
        Graphics2D g2d = (Graphics2D) g;// cast the graphics to Graphics2D

        Graph graph = new Graph();// create a new graph object
        graph.load_data();// load the data from the file
        ArrayList<Integer> nodesX = graph.getNodesX();// get the x coordinates of the nodes
        ArrayList<Integer> nodesY = graph.getNodesY();// get the y coordinates of the

        for(int i=0; i<graph.nodesAmount(); i++){
            int x = nodesX.get(i);// get the x coordinate of the node
            int y = nodesY.get(i);// get the y coordinate of the node
            drawNode(g2d, x, y, i+1);// draw the node on the canvas
        
    }
    }

    public GraphicPanel(){
        setBackground(new Color(204, 212, 224));// set the background color of the panel
     }


}