
/**
 * Write a description of class GUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class GUI extends JFrame implements ActionListener
{

    Graph graph = new Graph();// create a new graph object
    // menu bar
    JMenuBar menuBar;
    //Menu
    JMenu optionsMenu;// each menu
    JMenu nodesMenu;//nodes menu
    JMenu simulationMenu;//simulation menu
    JMenu fileMenu;//File menu
    //Sub menus
    JMenu sourceNodesMenu;// sub menu for source nodes
    JMenu destinationNodesMenu;// sub menu for destination nodes
    
    JMenuItem helpItem;// element in the menu
    JMenuItem exitItem;

    JMenuItem openItem;//To open and selcet simulation file
    JMenuItem saveItem;

    JMenuItem startItem;// item to start the simulation
    JMenuItem resetItem;//item to reset the simulation

    JMenuItem addNodeItem;// item to add a new node
    JMenuItem addEdgeItem;// item to add a new edge
    JMenuItem removedEdgeItem;// item to remove an edge

    //Nodes menu items
    ArrayList<JMenuItem> nodeItems = new ArrayList<JMenuItem>();// to store all the nodes menu items

    int width = 1600;// width of the window/jframe
    int height = 900;// height of the window/jframe

    GraphicPanel myGraphic;
    Algorithym algorithym;

    Nodes sourceNode = null;// to store the previous node that was selected
    Nodes destinationNode = null;// to store the destination node
    boolean hasRun = false;// to check if the algorithm has been run

    public GUI(){
        setTitle("Dijkstra's Algorithm");
        setupWindow();
        setupGraph();
        setupMenu();
        this.pack();
        this.setVisible(true);
    }


    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        if(cmd.equals("help")){ 
            createDialogBoxFromFile("help.txt", false);
        }
        else if(cmd.equals("exit")){
            System.exit(0);
        }
        else if(cmd.equals("Start")||cmd.equals("Reset")){
            Simulation(cmd);
        }
        else if(cmd.equals("Open")){
            JOptionPane.showMessageDialog(this, "This opption is currently unavaililable\n Plese go to help menu to find out more");
        }
        else if(cmd.equals("Save" )){
            if(hasRun){
                algorithym.writeToFile(sourceNode, destinationNode);// write the data to a file
            }
            else{
                JOptionPane.showMessageDialog(this, "Please run the simulation first!");
            }
        }
        else if(cmd.equals("Add Edge")||cmd.equals("Add Node")){
            JOptionPane.showMessageDialog(this, "This opption is currently unavaililable sorry\n To find out how to add nodes or edges please see more in help menu");
        }
        else if(isInterger(cmd)){//If cmd is integer, it means the user is trying to select a destination node
            setDestination(cmd);
        }
        else{
            setSource(cmd);
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
    public void createDialogBoxFromFile(String filePath, boolean edit){
        JDialog box = new JDialog(this);
        box.setBounds(750, 380, 600, 400);
        TextArea area = new TextArea();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            StringBuilder content = new StringBuilder();
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
            area.setText(content.toString());
            scanner.close();
        } catch (FileNotFoundException e) {
            area.setText("Help file not found: " + filePath);
        }

        area.setEditable(edit);
        box.add(area);
        box.toFront();
        box.setVisible(true);
        box.setTitle("How does it work?");
    }

    private void setupWindow(){
        setTitle("Dijkstra's Algorithm");// set the title of the window
        this.getContentPane().setPreferredSize(new Dimension(width, height));//Set the lenght and width of the window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//Exit when close the window

        this.myGraphic = new GraphicPanel();//Create a new graphic panel
        this.getContentPane().add(this.myGraphic, BorderLayout.CENTER);//Add the graphic panel to the center of the window
    }

    private void setupMenu(){
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
        simulationMenu = new JMenu("Simulation");
        menuBar.add(simulationMenu);
        startItem = new JMenuItem("Start");
        resetItem = new JMenuItem("Reset");
        startItem.addActionListener(this);//Link item to action listener
        simulationMenu.add(startItem);
        startItem.setToolTipText("Click to start the simulation");// set the tooltip so the user knows what it does
        resetItem = new JMenuItem("Reset");
        resetItem.addActionListener(this);//Link item to action listener
        simulationMenu.add(resetItem);
        resetItem.setToolTipText("Click to reset the graph");// set the tooltip to give user info

        //menu for file
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        openItem = new JMenuItem("Open");
        openItem.addActionListener(this);
        openItem.setToolTipText("Click here to open new simulation file");
        fileMenu.add(openItem);
        menuBar.add(fileMenu);
        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this);
        saveItem.setToolTipText("Click here to save simulation data");
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);


        //menu and menu and menu items for Nodes menu
        JMenu nodesMenu = new JMenu("Nodes");
        menuBar.add(nodesMenu);
        JMenu sourceNodesMenu = new JMenu("Source Nodes");
        nodesMenu.add(sourceNodesMenu);// add the source nodes menu to the nodes menu
        JMenu destinationNodesMenu = new JMenu("Destination");
        nodesMenu.add(destinationNodesMenu);// add the destination nodes menu to the nodes menu
        
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
    }

    private void setupGraph(){
        String name = JOptionPane.showInputDialog(null, "Enter file name:");
        graph.setFileName(name);// set the file name to load the data from
        graph.load_data();// load the data from the file
        myGraphic.setGraph(graph);// set the graph object in the graphic panel
        algorithym = new Algorithym(myGraphic);
    }




    private void Simulation(String cmd){
        if(cmd.equals("Start")){
            if(sourceNode == null && destinationNode == null){
                    // show message dialog if no source node and destination node is selected using JOptionPane
                    JOptionPane.showMessageDialog(this, "Please select a source and destination node!");
                }
                else if(sourceNode == null){
                    // show message dialog if no source node is selected using JOptionPane
                    JOptionPane.showMessageDialog(this, "Please select a source node!");
                }
                else if(destinationNode == null){
                    JOptionPane.showMessageDialog(this, "Please select a destination node!");
                }
                //This is where the magic happens
                else{
                    // run Dijkstra's algorithm with the source node and the graph
                    Thread dijkstraThread = new Thread(() -> {
                    algorithym.runDijkstra(sourceNode, destinationNode, graph, 700);  // 200 ms sleep for visualization
                    hasRun = true; // set hasRun to true after the algorithm has been run
                    });
                    dijkstraThread.start(); // start the thread to run the algorithm
            }
        }
        else{
            algorithym.reset(graph);
        }
    }

    private void setSource(String cmd){
        Nodes selectedNode = graph.getNodesMap().get(cmd);
            if (sourceNode != null && sourceNode != selectedNode) {
                sourceNode.setColorStatus("default");// reset the color of the previous node to default
            }
            selectedNode.setColorStatus("source");
            sourceNode = selectedNode;
            myGraphic.repaint();
    }

    private void setDestination(String cmd){
        int index = Integer.parseInt(cmd) - 1;// convert the string to an integer and subtract 1 to get the index
            Nodes destination = graph.getNodesList().get(index);// get the node from the list using the index
            if(destinationNode != null && destinationNode != destination){
                destinationNode.setColorStatus("default");// reset the color of the previous node to default
            }
            destination.setColorStatus("destination");// change the color of the selected node
            destinationNode = destination;// set the destination node to the selected node
            myGraphic.repaint();// repaint the graphic panel to show the changes
    }
        
}
