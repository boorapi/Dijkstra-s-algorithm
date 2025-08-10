/**
 * This class implements the dijkstra's algorithm, it also
 * visualizes the process on a graphic panel, and writes the results to a file.
 * It highlights node and edge status changes during execution for animation purposes.
 */
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Algorithym extends Thread{
    private GraphicPanel graphicPanel;// Add this field
    HashMap<Nodes, Nodes> previousNode;//To backtrack when finding path
    String fileName;
    ArrayList<Nodes> nodesList;
    HashMap<String, Nodes> nodesMap;
    HashMap<String, Edges> edgesMap;
    PriorityQueue<Nodes> pq;

    //Constructor
    public Algorithym(GraphicPanel graphicPanel) {
        this.graphicPanel = graphicPanel;
    }
    //Reset if the user want to rerun the algorithm
    public void reset(Graph graph){
        for(Nodes n : nodesList){
                n.setColorStatus("default");//Set all nodes color to blue
        }
        for(Edges e : graph.getEdgesList()){
            e.setColorStatus("default");//Set all edges color to light gray
        }
        for(Nodes n : graph.getNodesList()){
            n.setCost(Integer.MAX_VALUE);//Distance to all node are infinity
            n.setVisited(false);
        }
        pq.clear();//clear the priority queue just in case
        graphicPanel.repaint();

    }
    //This method will run the calculation and while its running it will also animate the algorithm
    //The method takes in source node, destination node, the graph, and the speed to determind how fast the algorithm runs
    public void runDijkstra(Nodes source, Nodes destination, Graph graph, int speed){
        //set these calss variable so the program can acces later from other method
        this.fileName = graph.getFileName();
        this.nodesList = graph.getNodesList();
        this.nodesMap = graph.getNodesMap();
        this.edgesMap = graph.getEdgesMap();

        for(Nodes n : nodesList){
                n.setColorStatus("default");//Set all nodes color to blue
        }
        for(Edges e : graph.getEdgesList()){
            e.setColorStatus("default");//Set all edges color to white
        }

        //Initail setup for every node
        for(Nodes n : graph.getNodesList()){
            n.setCost(Integer.MAX_VALUE);//Distance to all node are infinity
            n.setVisited(false);//All of the nodes are unvisited
        }
        source.setCost(0);//Set urce distance to source node to 0

        //Priority queue. Lowest cost will have highest priority
        pq = new PriorityQueue<>(Comparator.comparingInt(Nodes::getCost));
        pq.add(source); // Add source node to the priority queue
        previousNode = new HashMap<>();//keep track of previous nodes and backtrack to find urce shortest path
        //While the priority queue is not empty
         while (!pq.isEmpty()) {
            Nodes current = pq.poll(); // Get the node with the smallest cost
            current.setColorStatus("processing"); // Set the color of the current node to indicate it is being processed
            graphicPanel.repaint(); // Repaint the graphic panel to visualize the current state
            sleepFor(speed); // Pause for animation speed

            if (current.getStatus()) {
                continue; // If source node has already been visited, skip to next iteration
            }

            // Check all neighbors of the current node by looping through all of them
            for (Map.Entry<Nodes, Integer> entry : current.getLinks().entrySet()) {
                Nodes neighbourNode = entry.getKey();//Get the key
                int weight = entry.getValue();//get the value (weight)
                int newCost = current.getCost() + weight;//add the current cost to weight (new cost)

                // If the new cost is less than the current cost to get to neighbor
                if (newCost < neighbourNode.getCost()) {
                    neighbourNode.setCost(newCost); // Update cost
                    previousNode.put(neighbourNode, current); // Record path
                    pq.add(neighbourNode); // Push neighbor into queue

                    // Highlight the edge being processed
                    Edges edge = edgesMap.get(current.getName() + "-" + neighbourNode.getName());
                    if (edge != null) {
                        edge.setColorStatus("processing");
                    }

                    neighbourNode.setColorStatus("processing"); // Highlight neighbor
                    graphicPanel.repaint(); // Update visualization
                    sleepFor(speed); // Pause for animation
                }
            }
            current.setVisited(true); // Mark current node as visited after checking all links
        }

        // Backtrack and highlight the shortest path
        ArrayList<Nodes> path = backtrack(source, destination);
        for (int i = path.size() - 1; i >= 0; i--) {
            if (i > 0) {
                // Get the edge between two nodes in the path
                Edges edge = edgesMap.get(path.get(i).getName() + "-" + path.get(i - 1).getName());
                if (edge != null) {
                    edge.setColorStatus("usePath"); // change edge color as part of path
                }
            }
            path.get(i).setColorStatus("useNode"); // change node color as part of path
            graphicPanel.repaint();
            sleepFor(300); // Delay for visualization
        }
    }

    // Writes shortest path and distances to file by giving the method source and destination node.
    //And all the shortest path from source to every other node.
    public void writeToFile(Nodes source, Nodes destination) {
        String[] value = fileName.split("\\.");

        try {
            File file = new File(value[0] + "_path.txt"); // Create a .txt file with "_path" suffix
            FileWriter writer = new FileWriter(file);

            // Write main shortest path to the file
            writer.write("***Shortest path from " + source.getName() + " to " + destination.getName() + "***\n");
            writer.write("This is the path: ");
            ArrayList<Nodes> path = backtrack(source, destination);
            for (int i = path.size() - 1; i >= 0; i--) {
                writer.write(path.get(i).getName());
                if (i != 0) {
                    writer.write(" -> ");
                }
            }
            writer.write("\nThe cost of the path is: " + destination.getCost() + "\n\n\n");

            // Write all paths from source to every other node to file
            writer.write("***These are paths from source node to all other nodes***\n");
            for (Nodes node : nodesList) {
                if (node != source) {
                    writer.write("From " + source.getName() + " to " + node.getName() + ": " + node.getCost() + "\n");
                    ArrayList<Nodes> backtrackPath = backtrack(source, node);
                    writer.write("Path: ");
                    for (int i = backtrackPath.size() - 1; i >= 0; i--) {
                        writer.write(backtrackPath.get(i).getName());
                        if (i != 0) {
                            writer.write(" -> ");
                        }
                    }
                    writer.write("\n");
                }
                writer.write("\n");
            }

            writer.close(); // Close the writer

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // This method will backtrack from given destination to given source and return the path as an ArrayList of Nodes
    public ArrayList<Nodes> backtrack(Nodes from, Nodes to) {
        ArrayList<Nodes> path = new ArrayList<>();

        if (!previousNode.containsKey(to)) {
            return path; // Return empty path if no path exists
        }
        while (to != null) {
            path.add(to); // Add current node to path
            to = previousNode.get(to); // Move to previous node
        }
        return path;
    }

    // To pauses the algorithm for animation purposes
    private void sleepFor(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}