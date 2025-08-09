
/**
 * Write a description of class Load_flie here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
public class Graph{
    private ArrayList<String> nodesArray = new ArrayList<String>();// to store node name and location
    private ArrayList<String> edgesArray = new ArrayList<String>();// to store edges infomation
    
    private int nodesAmount;//amout of all the nodes from the file
    private int edgesAmount;//amout of all the adges from the file
    
    private ArrayList<String> nodesName = new ArrayList<String>();// to store names of the nodes
    
    private ArrayList<Nodes> nodesList = new ArrayList<Nodes>();// to store all the nodes objects
    private ArrayList<Edges> edgesList = new ArrayList<Edges>();// to store all the edges objects
    // to store nodes with their names as keys (easier and faster to access nodes by their names)
    private HashMap<String, Nodes> nodesMap = new HashMap<String, Nodes>();
    private HashMap<String, Edges> edgesMap = new HashMap<String, Edges>();

    String fileName;
    public String load_data(){
        File dataFile = new File(fileName); // create new file object called data file.
        try{
            Scanner readFile = new Scanner(dataFile);// use Scanner to read the data file
            String x = readFile.nextLine();
            this.nodesAmount = Integer.parseInt(x);  
            //get nodes info          
            for(int i=0; i<nodesAmount; i++){
                nodesArray.add(readFile.nextLine());
            }
            //get edges info
            String y = readFile.nextLine();
            this.edgesAmount = Integer.parseInt(y);
            for(int i=0; i<edgesAmount; i++){
                edgesArray.add(readFile.nextLine());
            }
            readFile.close();
        }catch (IOException e){
            e.printStackTrace();
            return "File not found";
        }

        //add nodes' name and location to their own ArrayList
        for(String node : nodesArray){
            String[] value = node.split(",");
            String name = value[0];
            nodesName.add(name);

            Nodes nodeObject = new Nodes(Integer.parseInt(value[1]), Integer.parseInt(value[2]), value[0]);
            nodesList.add(nodeObject);// add the node to the nodesList
            nodesMap.put(value[0], nodeObject);// add the node to the nodesMap with its name as a key
        }

        
        //add links to the nodes and create edges
        for(String edge : edgesArray){
            String[] value = edge.split(",");
            String fromNodeName = value[0];
            String toNodeName = value[1];
            Nodes fromNode = nodesMap.get(fromNodeName);// get the node object by its name
            Nodes toNode = nodesMap.get(toNodeName);// get the node object by its name 
            int weight = Integer.parseInt(value[2]);// get the weight of the edge
            if(weight<0){
                return "Weight less then 0";
            }
            Edges edgeForward = new Edges(fromNode, toNode, weight);// create a new edge object
            edgesMap.put(fromNodeName + "-" + toNodeName, edgeForward);// add the edge to the edgesMap with its name as a key
            edgesMap.put(toNodeName + "-" + fromNodeName, edgeForward);//same object under different key for undirected graph
            edgesList.add(edgeForward);//edges list olny contains edges in one direction
            //if both nodes exist, create an edge and add it to the nodes   
            if(fromNode != null && toNode != null){
                fromNode.addLink(toNode, weight);// add link to the node 
                toNode.addLink(fromNode, weight);// add link to the node in the opposite direction 
            }
        }

        return "";
    }

    public int nodesAmount(){
        return nodesAmount;
    }

    public int edgesAmount(){
        return edgesAmount;
    }
    
    public ArrayList<Nodes> getNodesList(){
        return nodesList;
    }

    public ArrayList<Edges> getEdgesList(){
        return edgesList;
    }

    public HashMap<String, Nodes> getNodesMap(){
        return nodesMap;
    }

    public HashMap<String, Edges> getEdgesMap(){
        return edgesMap;
    }
    
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }

    //method to clear everything for new graph
    public void reset(){
    // Clear all ArrayLists
    nodesArray.clear();
    edgesArray.clear();
    nodesName.clear();
    nodesList.clear();
    edgesList.clear();

    // Clear all HashMaps
    nodesMap.clear();
    edgesMap.clear();

    // Optionally reset counters
    nodesAmount = 0;
    edgesAmount = 0;
    }

}
