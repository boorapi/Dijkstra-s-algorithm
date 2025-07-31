
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
    Algorithym algorithym = new Algorithym();// create a new algorithm object

    JMenuBar menuBar;// menu bar
    JMenu optionsMenu;// each menu
    JMenu nodesMenu;//nodes menu
    JMenu SimulationMenu;//simulation menu
    //Sub menus
    JMenu sourceNodesMenu;// sub menu for source nodes
    JMenu destinationNodesMenu;// sub menu for destination nodes
    
    JMenuItem helpItem;// element in the menu
    JMenuItem exitItem;

    JMenuItem startSimulationItem;// item to start the simulation
    JMenuItem saveDataItem;// item to save thecalculation data

    JMenuItem addNodeItem;// item to add a new node
    JMenuItem addEdgeItem;// item to add a new edge
    JMenuItem removedEdgeItem;// item to remove an edge

    //Nodes menu items
    ArrayList<JMenuItem> nodeItems = new ArrayList<JMenuItem>();// to store all the nodes menu items

    int width = 1600;// width of the window/jframe
    int height = 900;// height of the window/jframe

    GraphicPanel myGraphic;

    Nodes sourceNode = null;// to store the previous node that was selected
    Nodes destinationNode = null;// to store the destination node


    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        if(cmd.equals("help")){ 
            createDialogBox("This is how the program works...", false);
        }
        else if(cmd.equals("exit")){
            System.exit(0);
        }
        else if(cmd.equals("Start Simulation")){
            if(sourceNode == null && destinationNode == null){
                // show message dialog if no source node and destination node is selected using JOptionPane
                JOptionPane.showMessageDialog(this, "Please select a source and destination node!");
            }
            else if(sourceNode == null){
                // show message dialog if no source node is selected using JOptionPane
                JOptionPane.showMessageDialog(this, "Please select a source node !");
            }
            else if(destinationNode == null){
                JOptionPane.showMessageDialog(this, "Please select a destination node!");
            }
            else{
                // run Dijkstra's algorithm with the source node and the graph
                algorithym.runDijkstra(sourceNode, destinationNode, graph);
                createDialogBox(sourceNode.getName()+" will be the soucre node, but I can not run the algo yet, will be able sonn!", false);
            }
        }
        else if(cmd.equals("Save Data")){
            // save the data to a file
        }
        //If cmd is integer, it means the user is trying to select a destination node
        else if(isInterger(cmd)){
            int index = Integer.parseInt(cmd) - 1;// convert the string to an integer and subtract 1 to get the index
            Nodes destination = graph.getNodesList().get(index);// get the node from the list using the index
            if(destinationNode != null && destinationNode != destination){
                destinationNode.setColor(50, 143, 168);// reset the color of the previous node to default
            }
            destination.setColor(59, 163, 86);// change the color of the selected node
            destinationNode = destination;// set the destination node to the selected node
            myGraphic.repaint();// repaint the graphic panel to show the changes
        }
        else{
            for(JMenuItem item:nodeItems){
                if(item.getActionCommand().equals(cmd)){
                    // find node from nodesMap using the name form action command
                    Nodes selectedNode = graph.getNodesMap().get(item.getActionCommand());
                    // if there already a source node selected, reset its color
                    if(sourceNode != null && sourceNode != selectedNode){
                        sourceNode.setColor(50, 143, 168);// reset the color of the previous node to default
                    }
                    selectedNode.setColor(214, 187, 32);// change the color of the selected node
                    sourceNode = selectedNode;// set the previous node to the selected node
                    myGraphic.repaint();// repaint the graphic panel to show the changes
                    return;
                }
            }
        }
        
    }

    public boolean isInterger(String str){
        try {
            Integer.parseInt(str);
            return true; // if the string can be parsed to an integer, return true
        } catch (NumberFormatException e) {
            return false; // if it can't be parsed, return false
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
        //add simulation menu
        SimulationMenu = new JMenu("Simulation");
        menuBar.add(SimulationMenu);
        startSimulationItem = new JMenuItem("Start Simulation");
        startSimulationItem.addActionListener(this);//Link item to action listener
        SimulationMenu.add(startSimulationItem);
        startSimulationItem.setToolTipText("Click to start the simulation");// set the tooltip so the user knows what it does
        saveDataItem = new JMenuItem("Save Data");
        saveDataItem.addActionListener(this);//Link item to action listener
        SimulationMenu.add(saveDataItem);
        saveDataItem.setToolTipText("Click to save the calculation data to a file");// set the tooltip to give user info

        //menu and menu and menu items for Nodes menu
        JMenu nodesMenu = new JMenu("Nodes");
        menuBar.add(nodesMenu);
        JMenu sourceNodesMenu = new JMenu("Source Nodes");
        nodesMenu.add(sourceNodesMenu);// add the source nodes menu to the nodes menu
        JMenu destinationNodesMenu = new JMenu("Destination");
        nodesMenu.add(destinationNodesMenu);// add the destination nodes menu to the nodes menu
        //add nodes to the menu
        graph.load_data();// load the data from the file
        myGraphic.setGraph(graph);// set the graph object in the graphic panel
        int i = 1;
        for(Nodes name:graph.getNodesList()){
            JMenuItem item = new JMenuItem(name.getName() + "    " + i);
            item.setActionCommand(name.getName());// set the action command to the name of the node
            item.setToolTipText("Click to select this node as a source node");// set the tooltip text
            item.addActionListener(this);//Link item to action listener
            sourceNodesMenu.add(item);
            nodeItems.add(item);// add the item to the list of nodes so it can be accessed later (class variable)
            i++;
        }
        int j = 1;
        for(Nodes name:graph.getNodesList()){
            JMenuItem item = new JMenuItem(name.getName() + "    " + j);
            item.setActionCommand(""+j);// set the action command to the name of the node
            item.setToolTipText("Click to select this node as a destination node");// set the tooltip text
            item.addActionListener(this);//Link item to action listener
            destinationNodesMenu.add(item);// add the same item to the destination nodes menu
            //nodeItems.add(item);// add the item to the list of nodes so it can be accessed later (class variable)
            j++;
        }
        JMenuItem addNodesItem = new JMenuItem("Add Node");
        addNodesItem.setActionCommand("Add Node");  
        addNodesItem.addActionListener(this);//Link item to action listener
        nodesMenu.add(addNodesItem);// add the item to the nodes menu
        JMenuItem addEdgeItem = new JMenuItem("Add Edge");
        addEdgeItem.setActionCommand("Add Edge");   
        addEdgeItem.addActionListener(this);//Link item to action listener
        nodesMenu.add(addEdgeItem);// add the item to the nodes menu


        this.pack();
    }
}
