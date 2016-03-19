package cluster;

import java.util.LinkedList;
import java.util.List;

public class Vertex {
    private final int id;
    private List<Edge> edges;
    
    public Vertex(int id) {
        this.id = id;
        this.edges = new LinkedList<>();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Vertex [id=" + id + "]";
    }
    
    
    
}
