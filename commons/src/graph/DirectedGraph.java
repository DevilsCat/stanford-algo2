package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DirectedGraph<V extends Comparable<V>, W extends Comparable<W>> {
    
    private final int vertexNo;
    private final int edgeNo;
    
    private List<Vertex<V>> vertices;
    private List<Edge<W>> edges;
    
    private List<Integer>[] adj;
    private List<Integer>[] indegree;
    
    
    @SuppressWarnings("unchecked")
    public DirectedGraph(int vertexNo, int edgeNo) {
        this.vertexNo = vertexNo;
        this.edgeNo   = edgeNo;
        
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        adj = new ArrayList[vertexNo];
        indegree = new ArrayList[vertexNo];
        
        for (int i = 0; i < vertexNo; ++i) {
            vertices.add(null);
            adj[i] = new ArrayList<>();
            indegree[i] = new ArrayList<>();
        }
        
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
        
        edges.add(newEdge);
        
        adj[from].add(edges.size() - 1);
        indegree[to].add(edges.size() - 1);
        
        // assert the size of the edges
        assert(edges.size() <= edgeNo);
    }
    
    public int getVertexNo() {
        return vertexNo;
    }
    
    public int getEdgeNo() {
        return edgeNo;
    }

    public List<Edge<W>> getIndgreeEdgeForVertex(int vid) {
        validateVertexId(vid);
        return indegree[vid].stream().map(i -> edges.get(i)).collect(Collectors.toList());
    }
    
    public List<Edge<W>> getEdges() {
        return edges;
    }
    
    public Optional<Edge<W>> getAdjEdge(int fromVid, int toVid) {
        validateVertexId(fromVid);
        validateVertexId(toVid);
        validateEdgeNo();
        List<Edge<W>> res = edges.stream().filter(edge -> {
            return edge.from.getId() == fromVid && edge.to.getId() == toVid;
        }).collect(Collectors.toList());
        
        if (res.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(res.get(0));  // always return the first edge matches the result
        }
    }
    
    private void validateEdgeNo() {
        if (edges.size() >= edgeNo) {
            throw new ArrayIndexOutOfBoundsException();
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
            List<Integer> vertexIndices = adj[i];
            vertexIndices.forEach(idx -> sb.append(vertices.get(idx)).append(" "));
            sb.append("\n");
        }
        
        sb.append("in-degree edge: \n");
        for (int i = 0; i < indegree.length; ++i) {
            Vertex<V> v = vertices.get(i);
            sb.append(v).append(": ");
            List<Integer> edgeIndices = indegree[i];
            edgeIndices.forEach(idx -> sb.append(edges.get(idx)).append(" "));
            sb.append("\n");
        }
        return sb.toString();
    }
    
}
