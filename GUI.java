
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

    Graph graph = new Graph();// create a new graph object

    JMenuBar menuBar;// menu bar
    JMenu optionsMenu;// each menu
    JMenu nodes;//nodes menu
    
    JMenuItem helpItem;// element in the menu
    JMenuItem exitItem;

    JButton runButton;// button to run the algorithm (not used in this version)

    //Nodes menu items
    ArrayList<JMenuItem> nodeItems = new ArrayList<JMenuItem>();// to store all the nodes menu items

    int width = 1600;// width of the window/jframe
    int height = 900;// height of the window/jframe

    GraphicPanel myGraphic;

    Nodes previousNode = null;// to store the previous node that was selected


    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        if(cmd.equals("help")){ 
            createDialogBox("This is how the program works...", false);
        }
        else if(cmd.equals("exit")){
            System.exit(0);
        }
        else{
            for(JMenuItem item:nodeItems){
                if(item.getActionCommand().equals(cmd)){
                    Nodes selectedNode = graph.getNodesMap().get(item.getActionCommand());// find node from nodesMap using the name form action command
                    // if there already a source node selected, reset its color
                    if(previousNode != null && previousNode != selectedNode){
                        previousNode.setColor(50, 143, 168);// reset the color of the previous node to default
                    }
                    selectedNode.setColor(214, 187, 32);// change the color of the selected node
                    previousNode = selectedNode;// set the previous node to the selected node
                    myGraphic.repaint();// repaint the graphic panel to show the changes
                    return;
                }
            }
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



    public GUI(){

        setTitle("Dijkstra's Algorithm");// set the title of the window
        this.getContentPane().setPreferredSize(new Dimension(width, height));//Set the lenght and width of the window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//Exit when close the window

        this.myGraphic = new GraphicPanel();//Create a new graphic panel
        this.getContentPane().add(this.myGraphic, BorderLayout.CENTER);//Add the graphic panel to the center of the window
        
        //Make the window visible
        this.pack();
        this.toFront();
        this.setVisible(true);
        
        //Button to run the algorithym
        //runButton = new JButton("Run Simulation");
        //runButton.addActionListener(this);//Link button to action listener
        //setLayout(new FlowLayout());
        //add(runButton);//Add the button to the window

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
        //menu and menu items for nodes
        nodes = new JMenu("Nodes");
        menuBar.add(nodes);
        //add nodes to the menu
        graph.load_data();// load the data from the file
        myGraphic.setGraph(graph);// set the graph object in the graphic panel
        int i = 1;
        for(Nodes name:graph.getNodesList()){
            JMenuItem item = new JMenuItem(name.getName() + "    " + i);
            item.setActionCommand(name.getName());// set the action command to the name of the node
            item.setToolTipText("Click to select this node as a source node");// set the tooltip text
            item.addActionListener(this);//Link item to action listener
            nodes.add(item);
            nodeItems.add(item);// add the item to the list of nodes so it can be accessed later (class variable)
            i++;
        }


        this.pack();
    }
}
