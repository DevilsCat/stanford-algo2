package week1.q3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The pseudo code for Prim's Alogrithms O(mn) complexity.
 * <pre>
 *  - Initialize X = {s}
 *  - T = {empty}
 *  - While (X != V)
 *    - Let e = (u, v) be the cheapest edge of G with u in X, v NOT in X.
 *    - Add e to T, add v to X 
 * </pre>
 * @author xiaoy
 *
 */
public class PrimsAlgo {
    Map<Integer, Vertex> verticesMap;
    public PrimsAlgo(String filename) throws Exception {
        verticesMap = new HashMap<>();
        
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        int nEdges = Integer.parseInt(bufferedReader.readLine().split(" ")[1]);
        
        for (int i = 0; i < nEdges; ++i) {
            String[] components = bufferedReader.readLine().split(" ", 3);
            int v0Id = Integer.parseInt(components[0]);
            int v1Id = Integer.parseInt(components[1]);
            int weight = Integer.parseInt(components[2]);
            if (!verticesMap.containsKey(v0Id)) {
                verticesMap.put(v0Id, new Vertex());
            }
            if (!verticesMap.containsKey(v1Id)) {
                verticesMap.put(v1Id, new Vertex());
            }
            Vertex v0 = verticesMap.get(v0Id);
            Vertex v1 = verticesMap.get(v1Id);
            Edge edge = new Edge(weight, v0, v1);
            v0.getEdges().add(edge);
            v1.getEdges().add(edge);
        }
        
        bufferedReader.close();
    }
    
    public int run() {
        List<Vertex> X = new ArrayList<>();
        List<Edge> T = new ArrayList<>(); 
        int size = verticesMap.size();
        // Randomly choose a vertex to begin with
        int beginId = (Integer) verticesMap.keySet().toArray()[0];
        X.add(verticesMap.get(beginId));
        
        // Prim's Algorithm
        while(X.size() != size) {
            int minWeight = Integer.MAX_VALUE;
            Edge minEdge = null;
            Vertex minVertex = null;
            for (Vertex v : X) {
                for (Edge e : v.getEdges()) {
                    if (isInSameSubGraph(X, e.getV1(), e.getV2())) {
                        continue;
                    }
                    if (minWeight > e.getWeight()) {
                        minWeight = e.getWeight();
                        minEdge = e;
                        minVertex = X.contains(e.getV1()) ? e.getV2() : e.getV1();
                    }
                }
            }
            X.add(minVertex);
            T.add(minEdge);
        }
        
        // Sum weight
        int sum = 0;
        for (Edge e : T) {
            sum += e.getWeight();
        }
        
        return sum;
    }
    
    private boolean isInSameSubGraph(List<Vertex> x, Vertex v1, Vertex v2) {
        return (x.contains(v1) && x.contains(v2)) ||
               (x.contains(v2) && x.contains(v1));
    }

    public static void main(String[] args) {
        
        try {
            int res = new PrimsAlgo("resources/edges.txt").run();
            System.out.println("res: " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
