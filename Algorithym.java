
import java.util.PriorityQueue;
import java.util.HashMap;

public class Algorithym{

    public Algorithym(){
        // Constructor for Algorithm class
        // This class can be used to implement the Dijkstra's algorithm or any other algorithm related to the graph
    }

    public void runDijkstra(Nodes source, Nodes destination, Graph graph){
        //Initail setup for every node
        for(Nodes n : graph.getNodesList()){
            n.setCost(Integer.MAX_VALUE);//Distance to all node are infinity
            n.setVisited(false);
        }
        source.setCost(0);//Set the distance to source node to 0

        PriorityQueue<Nodes> pq = new PriorityQueue<>();
        pq.add(source);//Add the source node to the priority queue
        HashMap<Nodes, Nodes> previousNode = new HashMap<>();//keep track of previous nodes and backtrack to find the shortest path







        
    }





}