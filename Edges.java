public class Edges {
    private Nodes startNode;
    private Nodes endNode;
    private int weight;

    public Edges(Nodes start, Nodes end, int weight) {
        this.startNode = start;
        this.endNode = end;
        this.weight = weight;
    }

    public Nodes getStartNode() {
        return startNode;
    }

    public Nodes getEndNode() {
        return endNode;
    }

    public int getWeight() {
        return weight;
    }
}
