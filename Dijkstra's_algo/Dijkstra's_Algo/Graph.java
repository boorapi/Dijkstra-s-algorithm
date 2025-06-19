
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
    
    public void load_data(){
        File dataFile = new File("simulation_data.csv"); // create new file object called data file.
        try{
            Scanner readFile = new Scanner(dataFile);// use Scanner to read the data file
            String x = readFile.nextLine();
            this.nodesAmount = Integer.parseInt(x);            
            for(int i=0; i<nodesAmount; i++){
                nodesArray.add(readFile.nextLine());
            }
            
            String y = readFile.nextLine();
            this.edgesAmount = Integer.parseInt(y);
            for(int i=0; i<edgesAmount; i++){
                edgesArray.add(readFile.nextLine());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public ArrayList<String> nodes(){
        return nodesArray;
    }
    
    public ArrayList<String> edges(){
        return edgesArray;
    }
}
