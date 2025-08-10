
/**
 * GUI class to create the main user interface for Dijkstra's algorithm simulation.
 * Handles menu setup, user interactions, file loading, and starting/resetting simulation.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;


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

    final int WIDTH = 1600;// width of the window/jframe
    final int HEIGHT = 900;// height of the window/jframe

    GraphicPanel myGraphic;
    Algorithym algorithym;

    Nodes sourceNode = null;// to store the previous node that was selected
    Nodes destinationNode = null;// to store the destination node
    boolean hasRun = false;// to check if the algorithm has been run

    //This constructor method will setup defualt window with default simulation
    public GUI(){
        setTitle("Dijkstra's Algorithm");// Set window title
        setupWindow();// Setup frame and graphic panel
        setupGraph(true);// Load initial graph from default file
        setupMenu();// Setup menu bar and items
        this.pack();
        this.setVisible(true);// Make the window visible
    }
    // Handles menu and button actions
    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        if(cmd.equals("help")){ 
            createDialogBoxFromFile("help.txt", false);// Show help dialog
        }
        else if(cmd.equals("exit")){
            System.exit(0);//Exit the simulation
        }
        else if(cmd.equals("Start")||cmd.equals("Reset")){
            Simulation(cmd);// Start or reset simulation
        }
        else if(cmd.equals("Open")){
            open();// Open new simulation file
            sourceNode = null;
            destinationNode = null;
        }
        else if(cmd.equals("Save" )){
            //Check if the user has run the algorithm
            if(hasRun){
                algorithym.writeToFile(sourceNode, destinationNode);// write the data to a file
            }
            else{//if the algorithm has not been run yet. Tell user they have to run the algorithm first
                JOptionPane.showMessageDialog(this, "Please run the simulation first!");
            }
        }
        //Tell user add edge and add node is unavailable
        else if(cmd.equals("Add Edge")||cmd.equals("Add Node")){
            JOptionPane.showMessageDialog(this, "This opption is currently unavailable sorry\n"+
            " To find out how to add nodes or edges please see more in help menu");
        }
        // If cmd is integer, treat as selecting destination node
        else if(isInterger(cmd)){
            setDestination(cmd);
        }
        else{ // Otherwise, treat as selecting source node
            setSource(cmd);
        }

    }
    // Helper method to check if string can be parsed as integer
    public boolean isInterger(String str){
        try {
            Integer.parseInt(str);
            return true; // if the string can be parsed to an integer, return true
        } catch (NumberFormatException e) {
            return false; // if it can't be parsed, return false (not integer)
        }
    }

    // Creates a dialog box showing content of a file (e.g., help file)
    // 'edit' parameter specifies if text area should be editable
    public void createDialogBoxFromFile(String filePath, boolean edit){
        JDialog box = new JDialog(this);
        box.setBounds(750, 380, 600, 400); // Set dialog size and position
        TextArea area = new TextArea();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            StringBuilder content = new StringBuilder();
            //Write text from file to the panel
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
            area.setText(content.toString()); // Set text from file
            scanner.close();
        } catch (FileNotFoundException e) {
            area.setText("Help file not found: " + filePath);//If file not found
        }

        area.setEditable(edit); // Set if editable or not
        box.add(area);
        box.toFront();
        box.setVisible(true);
        box.setTitle("How does it work?");//set title 
    }
    // Setup the JFrame properties and add the GraphicPanel
    private void setupWindow(){
        setTitle("Dijkstra's Algorithm");// set the title of the window
        this.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));//Set the lenght and width of the window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//Exit when close the window

        this.myGraphic = new GraphicPanel();//Create a new graphic panel
        this.getContentPane().add(this.myGraphic, BorderLayout.CENTER);//Add the graphic panel to the center of the window
    }
     // Setup menu bar and ALL menus and menu items with action listeners
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


        // Nodes menu with Source and Destination submenus
        JMenu nodesMenu = new JMenu("Nodes");
        menuBar.add(nodesMenu);
        JMenu sourceNodesMenu = new JMenu("Source Nodes");
        nodesMenu.add(sourceNodesMenu);// add the source nodes menu to the nodes menu
        JMenu destinationNodesMenu = new JMenu("Destination");
        nodesMenu.add(destinationNodesMenu);// add the destination nodes menu to the nodes menu
        // Add all nodes as menu items for source nodes selection by looping through all the node
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
        // Add all graph nodes as menu items for destination nodes selection
        int j = 1;
        for(Nodes name:graph.getNodesList()){
            JMenuItem item = new JMenuItem(name.getName() + "    " + j);
            item.setActionCommand(""+j);// set the action command to the number represent the destination node
            item.setToolTipText("Click to select this node as a destination node");// set the tooltip text
            item.addActionListener(this);//Link item to action listener
            destinationNodesMenu.add(item);// add the same item to the destination nodes menu
            //nodeItems.add(item);// add the item to the list of nodes so it can be accessed later (class variable)
            j++;
        }
        //create add node and add edge menu
        //Will be use in the future (Currently doing nothing)
        JMenuItem addNodesItem = new JMenuItem("Add Node");
        addNodesItem.setActionCommand("Add Node");  
        addNodesItem.addActionListener(this);//Link item to action listener
        nodesMenu.add(addNodesItem);// add the item to the nodes menu
        JMenuItem addEdgeItem = new JMenuItem("Add Edge");
        addEdgeItem.setActionCommand("Add Edge");   
        addEdgeItem.addActionListener(this);//Link item to action listener
        nodesMenu.add(addEdgeItem);// add the item to the nodes menu

    }
    // Starts or resets the simulation depending on the cmd argument
    //The method will also check the source and destination node are selected.
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
                // run Dijkstra's algorithm with the source node and the graph on new thread
                Thread dijkstraThread = new Thread(() -> {
                algorithym.runDijkstra(sourceNode, destinationNode, graph, 400);  // 400 ms sleep for visualization
                hasRun = true; // set hasRun to true after the algorithm has been run
                });
                dijkstraThread.start(); // start the thread to run the algorithm
            }
        }
        else{//reset simulation
            algorithym.reset(graph);
            sourceNode = null;
            destinationNode = null;
        }
    }
    // Set the source node based on command string (node name)
    private void setSource(String cmd) {
    Nodes selectedNode = graph.getNodesMap().get(cmd);

        // Prevent selecting the destination node as source
        if (selectedNode == destinationNode) {
            JOptionPane.showMessageDialog(this, "This node is already selected as the destination!");
            return;
        }
        //IF the user reselect node
        if (sourceNode != null && sourceNode != selectedNode) {
            sourceNode.setColorStatus("default");// Reset old source node color
        }

        selectedNode.setColorStatus("source");// Highlight new source node
        sourceNode = selectedNode;
        myGraphic.repaint();
    }
    // Set the destination node based on command
    private void setDestination(String cmd) {
        int index = Integer.parseInt(cmd) - 1;
        Nodes destination = graph.getNodesList().get(index);

        // Prevent selecting the source node as destination
        if (destination == sourceNode) {
            JOptionPane.showMessageDialog(this, "This node is already selected as the source!");
            return;
        }
        //If the user reselect node
        if (destinationNode != null && destinationNode != destination) {
            destinationNode.setColorStatus("default");// Reset old destination node color
        }

        destination.setColorStatus("destination");// Highlight new destination node
        destinationNode = destination;
        myGraphic.repaint();
    }
    // Loads the graph from a file (default or user-specified)
    private void setupGraph(boolean initialSetUp) {
    String name;

    if (initialSetUp) {
        name = "simulation_data.csv";// default simulation data file
    } else {
        name = JOptionPane.showInputDialog(null, "Enter file name:");// ask user for file name
    }
    String previousFile = graph.getFileName();
    graph.reset(); // clear any old data before loading

    graph.setFileName(name);//set file name to load from
    String status = graph.load_data();//cast loading status to string
    //if somthing wrong
    if (!status.equals("")){
        //print out whats wrong
        if(status.equals("File not found")){
            JOptionPane.showMessageDialog(this, 
                "File not found. Please check if the file you wish to\n" +
                "open is in the same directory and retry.");
        }else if(status.equals("Weight less then 0")){
            JOptionPane.showMessageDialog(this, "File error, weight can not be negative amount.");
        }
        //revert to old file
        graph.reset();
        graph.setFileName(previousFile);
        graph.load_data();
        return; // don't continue if load failed
    }

    myGraphic.setGraph(graph);// attach the loaded graph
    algorithym = new Algorithym(myGraphic); // create new algorithm object
    }
    // Opens new simulation file and resets menus/graphics
    private void open() {
        setupGraph(false);// load userselected graph
        menuBar.removeAll();// clear current menu bar items
        menuBar.revalidate();
        menuBar.repaint();
        setupMenu();// rebuild menus
        myGraphic.repaint();
    
    }        
}
