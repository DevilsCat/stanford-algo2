package assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import lecture.Vertex;

/**
 * This optimized algorithm reduce the space usage of
 * Knapsack algorithm. Since we only care correct answer
 * itself, we only store previous and current subproblem
 * in our optimal data structure.
 * <p>
 * @author yxiao
 *
 */
public class Q2 {
    
    public static int solve(List<Vertex> vertices, int totalWeight) {
        if (totalWeight <= 0 || vertices == null || vertices.isEmpty()) {
            return Integer.MIN_VALUE;
        }
        
        final int N = vertices.size() + 1;
        final int W = totalWeight + 1;
        
        int[][] dp = new int[2][W];
        
        // initialize dp for first column setting to all zero.
        for (int x = 0; x < W; ++x) {
            dp[0][x] = 0;
        }
        
        // dynamic programming...
        for (int i = 1; i < N; ++i) {
            Vertex vertex = vertices.get(i - 1);
            for (int x = 0; x < W; ++x) {
                if (x < vertex.getWeight()) {
                    dp[1][x] = dp[0][x];
                } else {
                    dp[1][x] = Math.max(dp[0][x], dp[0][x - vertex.getWeight()] + vertex.getValue());
                }
            }
            // copy second dp row to first.
            for (int w = 0; w < W; ++w) {
                dp[0][w] = dp[1][w];
            }
            
            if (i % 50 == 0) {
                System.out.println("Progress: " + (double)i/N);
            }
            
        }
        
        return dp[0][W - 1];
    }

    public static void main(String[] args) throws Exception {
        FileReader fileReader = new FileReader("resources/knapsack_big.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        String[] summary = bufferedReader.readLine().split(" ", 2);
        final int totalWeight = Integer.parseInt(summary[0]);
        final int nVertices = Integer.parseInt(summary[1]);
        
        List<Vertex> vertices = new ArrayList<>(nVertices);
        
        bufferedReader.lines().forEach(line -> {
            String[] tokens = line.split(" ", 2);    
            vertices.add(new Vertex(
                    Integer.parseInt(tokens[0]), 
                    Integer.parseInt(tokens[1])));
        });
        
        System.out.println(Q2.solve(vertices, totalWeight));
        
        bufferedReader.close();
    }

}
