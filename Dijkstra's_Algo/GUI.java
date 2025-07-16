
/**
 * Write a description of class GUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener
{

    JMenuBar menuBar;// menu bar
    JMenu optionsMenu;// each menu
    Canvas myCanvas;// canvas to draw on
    
    JMenuItem helpItem;// element in the menu
    JMenuItem exitItem;

    int width = 1800;// width of the window/jframe
    int height = 980;// height of the window/jframe
    int radius = 10;// radius of the node

    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        switch(cmd){
            case "help" : createDialogBox("This is how the program works...", false);
                break;
            case "exit" : System.exit(0);
        }
    }
    //This method will create dialog box 
    //it will take in the text that will be printed out in the box
    //and boolean to see if the box can be edit (add text)
    public void createDialogBox(String text, boolean edit){
        JDialog box = new JDialog(this);
        box.setBounds(750,380,400,200);
        TextArea area = new TextArea(text);
        if(!edit){
            area.setEditable(false);
        }
        box.add(area);
        box.toFront();
        box.setVisible(true);
        box.setTitle("How does it work?");
    }

    //This method will draw the node on the canvas using the x and y coordinates
    public void drawNode(Graphics g, int x, int y){
        g.setColor(new Color(50, 143, 168));// set the color of the node
        g.fillOval(x-radius, y-radius, radius*2, radius*2);//draw the node
         
        }

    public void paint(Graphics g){
        super.paint(g);// call the paint method to paint

        Graph graph = new Graph();// create a new graph object
        graph.load_data();// load the data from the file
        ArrayList<Integer> nodesX = graph.getNodesX();// get the x coordinates of the nodes
        ArrayList<Integer> nodesY = graph.getNodesY();// get the y coordinates of the

        for(int i=0; i<graph.nodesAmount(); i++){
            int x = nodesX.get(i);// get the x coordinate of the node
            int y = nodesY.get(i);// get the y coordinate of the node
            drawNode(g, x, y);// draw the node on the canvas
        }
    }

    public GUI(){

        setTitle("Dijkstra's Algorithm");// set the title of the window
        this.getContentPane().setPreferredSize(new Dimension(width, height));//Set the lenght and width of the window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//Exit when close the window

        JPanel panel = new JPanel();// create a new panel
        panel.setPreferredSize(new Dimension(width, height));
        myCanvas = new Canvas();// create a new canvas
        panel.add(myCanvas);// add the canvas to the panel
        panel.setBackground(Color.black);

        //Make the window visible
        this.pack();
        this.toFront();
        this.setVisible(true);

        //setup menu bar
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        //add menu on the menu bar
        optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);
        //add items on the menu
        helpItem = new JMenuItem("help");
        helpItem.addActionListener(this);//Link item to action listener
        optionsMenu.add(helpItem);
        exitItem = new JMenuItem("exit");
        exitItem.addActionListener(this);//Link item to acction listener
        optionsMenu.add(exitItem);
        

        this.pack();
    }
}
