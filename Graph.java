
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
public class Graph{
    private ArrayList<String> nodesArray = new ArrayList<String>();// to store node name and location
    private ArrayList<String> edgesArray = new ArrayList<String>();// to store edges infomation
    
    private int nodesAmount;//amout of all the nodes from the file
    private int edgesAmount;//amout of all the adges from the file
    
    private ArrayList<String> nodesName = new ArrayList<String>();// to store names of the nodes
    private ArrayList<Integer> nodesX = new ArrayList<Integer>();// to store x coordinates of the nodes
    private ArrayList<Integer> nodesY = new ArrayList<Integer>();// to store y coordinates of the nodes
    
    public void load_data(){
        File dataFile = new File("simulation_data.csv"); // create new file object called data file.
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
        }

        //add nodes' name and location to their own ArrayList
        for(String n : nodesArray){
            String[] value = n.split(",");
            String name = value[0];
            nodesName.add(name);
            String X = value[1];
            String Y = value[2];
            int x = Integer.parseInt(X);
            int y = Integer.parseInt(Y);
            nodesX.add(x);
            nodesY.add(y);
        }
    }
    
    public void Graph(){
        
    }
    
    public int nodesAmount(){
        return nodesAmount;
    }
    
    public int edgesAmount(){
        return edgesAmount;
    }
    
    public ArrayList<Integer> getNodesX(){
        return nodesX;
    }
    
    public ArrayList<Integer> getNodesY(){
        return nodesY;
    }
    
    public ArrayList<String> nodesName(){
        return nodesName;
    }
    
    

}
