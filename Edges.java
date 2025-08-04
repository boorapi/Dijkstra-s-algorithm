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


    public void setColorStatus(String status){
        if(status.equals("usePath")){
            this.color = new Color(71, 158, 78);
        } else if(status.equals("processing")){
            this.color = new Color(196, 47, 47);
        } else {
            this.color = new Color(230, 240, 240);
        }
    }

}