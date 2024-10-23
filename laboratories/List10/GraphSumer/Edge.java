package GraphSumer;

public class Edge {
    private final Vertex destination;
    private final int weight;
    
    public Edge(final Vertex destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }   
        
    public Edge(final Vertex destination) {
        this(destination, 1);
    }

    public Vertex getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }
}
