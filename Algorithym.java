
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;

public class Algorithym{
    HashMap<Nodes, Nodes> previousNode;

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

        //Priority queue. LOwest cost will have highest priority
        PriorityQueue<Nodes> pq = new PriorityQueue<>((n1, n2) -> Integer.compare(n1.getCost(), n2.getCost()));
        pq.add(source);//Add the source node to the priority queue
        previousNode = new HashMap<>();//keep track of previous nodes and backtrack to find the shortest path

        while(!pq.isEmpty()) {
            Nodes currentNode = pq.poll();//get the first node in the queue

            if(currentNode.getStatus()) {
                continue; // If the node has already been visited, skip to next iteration
            }
            else if(source == destination) {
                return;// If we reach the destination, we can stop the algorithm
            }

            for(Map.Entry<Nodes, Integer> entry : currentNode.getLinks().entrySet()){
                Nodes neighborNode = entry.getKey();
                int weight = entry.getValue();
                int newCost = currentNode.getCost() + weight;

                if(newCost < neighborNode.getCost()){
                    neighborNode.setCost(newCost);
                    previousNode.put(neighborNode, currentNode);
                }
                pq.add(neighborNode);
            }
            currentNode.setVisited(true); //Mark cuurecnt node as visited after go through all the links
        }
        
    }

    public void writeToFile(Nodes source, Nodes destiantion){
        
    }


}