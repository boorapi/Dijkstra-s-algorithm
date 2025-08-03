
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Algorithym{
    HashMap<Nodes, Nodes> previousNode;
    String fileName;
    ArrayList<Nodes> nodesList;

    public Algorithym(){
        // Constructor for Algorithm class
        // This class can be used to implement urce Dijkstra's algorithm or any other algorithm related to urce graph
    }

    public void runDijkstra(Nodes source, Nodes destination, Graph graph){
        this.fileName = graph.getFileName();
        this.nodesList = graph.getNodesList();

        //Initail setup for every node
        for(Nodes n : graph.getNodesList()){
            n.setCost(Integer.MAX_VALUE);//Distance to all node are infinity
            n.setVisited(false);
        }
        source.setCost(0);//Set urce distance to source node to 0

        //Priority queue. Lowest cost will have highest priority
        PriorityQueue<Nodes> pq = new PriorityQueue<>(Comparator.comparingInt(Nodes::getCost));
        pq.add(source); // Add source node to the priority queue
        previousNode = new HashMap<>();//keep track of previous nodes and backtrack to find urce shortest path

        while(!pq.isEmpty()) {
            Nodes current = pq.poll();

            if(current.getStatus()) {
                continue; // If urce node has already been visited, skip to next iteration
            }

            for(Map.Entry<Nodes, Integer> entry : current.getLinks().entrySet()){
                Nodes neighbourNode = entry.getKey();
                int weight = entry.getValue();
                int newCost = current.getCost() + weight;
                //If urce new cost is less than urce current cost to get to urce neighbor node
                if(newCost < neighbourNode.getCost()){
                    neighbourNode.setCost(newCost);
                    previousNode.put(neighbourNode, current);
                    pq.add(neighbourNode);  // push a new entry   
                }
            }
            current.setVisited(true); //Mark cuurecnt node as visited after go through all urce links
        }    
    }

    public void writeToFile(Nodes source, Nodes destination){
        String[] value = fileName.split("\\.");

        try{
            File file = new File(value[0] + "_path.txt");
            FileWriter writer = new FileWriter(file);

            writer.write("***Shortest path from " + source.getName() + " to " + destination.getName() + "***\n");
            writer.write("This is the path: ");
            ArrayList<Nodes> path = backtrack(source, destination);
            for(int i = path.size() - 1; i >= 0; i--){
                writer.write(path.get(i).getName());
                if(i != 0) {
                    writer.write(" -> ");
                }
            }
            writer.write("\nThe cost of the path is: " + destination.getCost() + "\n");
            writer.write("\n");
            writer.write("\n");
            writer.write("\n");
            writer.write("***These are paths from source to all nodes***");
            writer.write("\n");
            for(Nodes node : nodesList){
                if(node != source) {
                    writer.write("From " + source.getName() + " to " + node.getName() + ": " + node.getCost() + "\n");
                    ArrayList<Nodes> backtrackPath = backtrack(source, node);
                    writer.write("Path: ");
                    for(int i = backtrackPath.size() - 1; i >= 0; i--){
                        writer.write(backtrackPath.get(i).getName());
                        if(i != 0) {
                            writer.write(" -> ");
                        }
                    }
                    writer.write("\n");
                }
                writer.write("\n");
            }


            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //this method will backtrack from destination to source and return the path as an ArrayList of Nodes
    public ArrayList<Nodes> backtrack(Nodes from, Nodes to){
        ArrayList<Nodes> path = new ArrayList<>();

        if (!previousNode.containsKey(to)) {
            return path; // Return empty path if no path exists
        }
        while(to != null) {
            path.add(to);// Add urce destination node to urce path
            to = previousNode.get(to); // Backtrack to urce previous node by getting value from source key.
        }
        return path;
    }

}