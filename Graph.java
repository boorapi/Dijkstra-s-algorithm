/**
 * The Graph class is responsible for loading and storing graph data from a file.
 * It maintains lists and maps of nodes and edges for easy access during graph operations.
 * This class supports resetting all stored data so a new graph can be loaded. 
 */

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    // Stores raw node data from file before creating node objects
    private ArrayList<String> nodesArray = new ArrayList<String>();
    // Stores raw edge data from file before creating edge objects
    private ArrayList<String> edgesArray = new ArrayList<String>();

    // Number of nodes and edges in the graph
    private int nodesAmount;
    private int edgesAmount;

    // Stores names of all nodes for easy access
    private ArrayList<String> nodesName = new ArrayList<String>();

    // List of all node objects in the graph
    private ArrayList<Nodes> nodesList = new ArrayList<Nodes>();
    // List of all edge objects in the graph
    private ArrayList<Edges> edgesList = new ArrayList<Edges>();

    // Map of node names to their corresponding node objects
    private HashMap<String, Nodes> nodesMap = new HashMap<String, Nodes>();
    // Map of edge identifiers to their corresponding edge objects
    private HashMap<String, Edges> edgesMap = new HashMap<String, Edges>();

    // Name of the file containing graph data
    String fileName;


     //Loads graph data from the file set in fileName.
     //Creates node and edge objects and stores them in lists and maps.
     //return Empty string if successful, or error message if file not found or invalid data.
    public String load_data() {
        File dataFile = new File(fileName); // Create new File object for the data file.
        try {
            Scanner readFile = new Scanner(dataFile); // Use Scanner to read the file

            // Read number of nodes
            String x = readFile.nextLine();
            this.nodesAmount = Integer.parseInt(x);

            // Read node information
            for (int i = 0; i < nodesAmount; i++) {
                nodesArray.add(readFile.nextLine());
            }

            // Read number of edges
            String y = readFile.nextLine();
            this.edgesAmount = Integer.parseInt(y);

            // Read edge information
            for (int i = 0; i < edgesAmount; i++) {
                edgesArray.add(readFile.nextLine());
            }

            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "File not found";
        }

        // Create node objects and store in lists/maps
        for (String node : nodesArray) {
            String[] value = node.split(",");//Split by ","
            String name = value[0];//first element is name
            nodesName.add(name);
            //create the object
            Nodes nodeObject = new Nodes(Integer.parseInt(value[1]), Integer.parseInt(value[2]), value[0]);
            nodesList.add(nodeObject);
            //add to hashmap.Use name as key
            nodesMap.put(value[0], nodeObject);
        }

        // Create edge objects and link nodes
        for (String edge : edgesArray) {
            String[] value = edge.split(",");//split echa value by ","
            String fromNodeName = value[0];
            String toNodeName = value[1];
            //get nodes from and node to from hashmap
            Nodes fromNode = nodesMap.get(fromNodeName);
            Nodes toNode = nodesMap.get(toNodeName);
            int weight = Integer.parseInt(value[2]);

            // Check for invalid weight
            if (weight < 0) {
                return "Weight less then 0";
            }

            // Create edge object
            Edges edgeForward = new Edges(fromNode, toNode, weight);

            // Add edge to map (both directions)
            edgesMap.put(fromNodeName + "-" + toNodeName, edgeForward);
            edgesMap.put(toNodeName + "-" + fromNodeName, edgeForward);

            // Add edge to list (only one direction stored in list)
            edgesList.add(edgeForward);

            // Add egdes to Like list in both directions
            if (fromNode != null && toNode != null) {
                fromNode.addLink(toNode, weight);
                toNode.addLink(fromNode, weight);
            }
        }

        return "";
    }

    // Getters for graph data
    public int nodesAmount() {
        return nodesAmount;
    }
    //getters for egdes amount
    public int edgesAmount() {
        return edgesAmount;
    }
    //Getters for nodesList
    public ArrayList<Nodes> getNodesList() {
        return nodesList;
    }
    //Getter for edgesList
    public ArrayList<Edges> getEdgesList() {
        return edgesList;
    }
    //Getters for NodesMap
    public HashMap<String, Nodes> getNodesMap() {
        return nodesMap;
    }
    //Getter for edgesMap
    public HashMap<String, Edges> getEdgesMap() {
        return edgesMap;
    }
    //Set file name (when openning new file)
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    //Getters for file name
    public String getFileName() {
        return fileName;
    }

    //Clears all lists, maps, and counters so a new graph can be loaded.
    public void reset() {
        // Clear all ArrayLists
        nodesArray.clear();
        edgesArray.clear();
        nodesName.clear();
        nodesList.clear();
        edgesList.clear();

        // Clear all HashMaps
        nodesMap.clear();
        edgesMap.clear();

        // Reset counters
        nodesAmount = 0;
        edgesAmount = 0;
    }
}
