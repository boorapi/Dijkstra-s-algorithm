import java.awt.Color;

public class Edges {
    private Nodes startNode;
    private Nodes endNode;
    private int weight;
    private Color color;

    public Edges(Nodes start, Nodes end, int weight) {
        this.startNode = start;
        this.endNode = end;
        this.weight = weight;
        this.color = new Color(230, 240, 240); // default color for edges
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

    public Color getColor() {
        return color;
    }

    public void setColor(int r, int g, int b) {
        this.color = new Color(r, g, b);
    }
}
