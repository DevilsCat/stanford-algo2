package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DirectedGraph<V extends Comparable<V>, W extends Comparable<W>> {
    
    private int vertexNo;
    
    private List<Vertex<V>> vertices;
    
    private List<Edge<W>>[] adj;
    private List<Edge<W>>[] indegree;
    private List<Edge<W>> edges;
    
    @SuppressWarnings("unchecked")
    public DirectedGraph(int vertexNo) {
        this.vertexNo = vertexNo;
        vertices = new ArrayList<>();
        adj = (ArrayList<Edge<W>>[]) new ArrayList[vertexNo];
        indegree = (ArrayList<Edge<W>>[]) new ArrayList[vertexNo];
        for (int i = 0; i < vertexNo; ++i) {
            vertices.add(null);
            adj[i] = new ArrayList<>();
            indegree[i] = new ArrayList<>();
        }
        edges = new ArrayList<>();
    }
    
    public void addEdge(int from, int to, W weight) {
        from -= 1;
        to -= 1;
        if (vertices.get(from) == null) {
            vertices.set(from, new Vertex<V>(from, null));
        }
        if (vertices.get(to) == null) {
            vertices.set(to, new Vertex<V>(to, null));
        }
        
        Edge<W> newEdge = new Edge<W>(vertices.get(from), vertices.get(to), weight);
        
        adj[from].add(newEdge);
        indegree[to].add(newEdge);
        edges.add(newEdge);
    }
    
    public int getVertexNo() {
        return vertexNo;
    }
    
    public List<Edge<W>> getIndgreeEdgeForVertex(int vid) {
        validateVertexId(vid);
        return indegree[vid];
    }
    
    public Optional<Edge<W>> getAdjEdge(int fromVid, int toVid) {
        validateVertexId(fromVid);
        validateVertexId(toVid);
        List<Edge<W>> res = edges.stream().filter(edge -> {
            return edge.from.getId() == fromVid && edge.to.getId() == toVid;
        }).collect(Collectors.toList());
        if (res.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(res.get(0));  // always return the first edge matche the result
        }
    }
    
    private void validateVertexId(int vid) {
        if (vid < 0 || vid >= indegree.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Adjacent List: \n");
        for (int i = 0; i < adj.length; ++i) {
            Vertex<V> v = vertices.get(i);
            sb.append(v).append(": ");
            List<Edge<W>> edges = adj[i];
            edges.forEach(edge -> sb.append(edge).append(" "));
            sb.append("\n");
        }
        
        sb.append("in-degree edge: \n");
        for (int i = 0; i < indegree.length; ++i) {
            Vertex<V> v = vertices.get(i);
            sb.append(v).append(": ");
            List<Edge<W>> edges = indegree[i];
            edges.forEach(edge -> sb.append(edge).append(" "));
            sb.append("\n");
        }
        return sb.toString();
    }
    
}
