package cluster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import cluster.unionfind.BasicUnionFind;
import cluster.unionfind.UnionFind;

public class ClusterAlogrithm {
    
    private final int K;
    
    private int iterLeft;
    
    private Vertex[] vertices;
    
    private List<Edge> edges;
    
    public ClusterAlogrithm(BufferedReader reader, int K) throws Exception {
        this.K = K;
        
        // Get number of nodes.
        Integer nNodes = Integer.parseInt(reader.readLine());
        
        // Now we know how many iterations we need.
        iterLeft = nNodes - K;
        
        this.vertices = new Vertex[nNodes];
        
        this.edges = new ArrayList<>();
        
        reader.lines().forEach(line -> {
            String[] tokens = line.split(" ", 3);
            int v1Id = Integer.parseInt(tokens[0]) - 1;
            int v2Id = Integer.parseInt(tokens[1]) - 1;
            int weight = Integer.parseInt(tokens[2]);
            
            if (vertices[v1Id] == null) {
                vertices[v1Id] = new Vertex(v1Id);
            }
            
            if (vertices[v2Id] == null) {
                vertices[v2Id] = new Vertex(v2Id);
            }
            
            Edge edge = new Edge(weight, vertices[v1Id], vertices[v2Id]);
            vertices[v1Id].getEdges().add(edge);
            vertices[v2Id].getEdges().add(edge);
            
            edges.add(edge);
        });
        
        reader.close();
    }
    
    public void solve() throws Exception {
        edges.sort((lhs, rhs) -> { return lhs.getWeight() - rhs.getWeight(); });
        
        Iterator<Edge> edgeIter = edges.iterator();
        
        UnionFind uf = new BasicUnionFind(vertices.length);
        
        while(iterLeft-- != 0) {
            Vertex p, q;
            do {
                Edge e = edgeIter.next();
                p = e.getV1();
                q = e.getV2();
            } while (uf.find(p.getId()) == uf.find(q.getId()));
            uf.union(uf.find(p.getId()), uf.find(q.getId()));
        }
                
        // Calculate the minimum space
        int minSpace = Integer.MAX_VALUE;
        for (Vertex vertex : vertices) {
            List<Edge> edges = vertex.getEdges();
            Edge minEdge = edges.stream()
                .filter(e -> !uf.connected(e.getV1().getId(), e.getV2().getId()))
                .min((lhs, rhs) -> lhs.getWeight() - rhs.getWeight()).get();
            if (minEdge.getWeight() < minSpace) {
                minSpace = minEdge.getWeight();
            }
        }
        
        System.out.println(minSpace);
        
    }
    
    public static void main(String[] args) throws Exception{
        final Integer k = 4;
        
        FileReader fileReader = new FileReader("resources/clustering_big.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        ClusterAlogrithm algo = new ClusterAlogrithm(bufferedReader, k);
        
        algo.solve();
        
    }
}
