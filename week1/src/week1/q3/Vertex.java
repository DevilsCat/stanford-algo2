package week1.q3;

import java.util.LinkedList;
import java.util.List;

public class Vertex {
    private List<Edge> edges;
    
    public Vertex() {
        this.edges = new LinkedList<>();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
    
}
