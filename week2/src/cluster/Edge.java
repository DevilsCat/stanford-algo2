package cluster;

public class Edge {
    private int weight;
    private Vertex v1;
    private Vertex v2;
    
    public Edge(int weight, Vertex v1, Vertex v2) {
        super();
        this.weight = weight;
        this.v1 = v1;
        this.v2 = v2;
    }
    
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public Vertex getV1() {
        return v1;
    }
    public void setV1(Vertex v1) {
        this.v1 = v1;
    }
    public Vertex getV2() {
        return v2;
    }
    public void setV2(Vertex v2) {
        this.v2 = v2;
    }
    
    @Override
    public String toString() {
        return "Edge [weight=" + weight + ", v1=" + v1 + ", v2=" + v2 + "]";
    }
    
}
