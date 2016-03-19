package lecture;

import java.util.ArrayList;
import java.util.List;

/**
 * The Knapsack Problem
 * Input: n items. Each has a value:
 * <p>
 * <li>Value vi (non-negative)</li>
 * <li>Size wi (non-negative, integral)</li>
 * <li>Capacity W (a non-negative integer)</li>
 * <p>
 * Output: A subset S in {1,2,...,n} that maximizes sum of vi's value, subject to capacity W constraint.
 * <p>
 * <p>
 * Solution: Dynamic Programming:
 * <pre><code>
 * Initialization: A[0, x] = 0 for all x
 * 
 * Main Loop:
 * for i = 1 ... n
 *   for x = 0 ... W
 *     A[i, x] := max{A[i-1, x], A[i-1, x-wi] + vi}
 * </code></pre>
 * @author yxiao
 *
 */
public class Knapsack {

    public static int solve(List<Vertex> vertices, int totalWeight) {
        
        if (totalWeight <= 0 || vertices == null || vertices.isEmpty()) {
            return Integer.MIN_VALUE;
        }
        
        int[][] dp = new int[vertices.size() + 1][totalWeight + 1];
        
        // initialize dp for first column to zero.
        for (int x = 0; x < dp[0].length; ++x) {
            dp[0][x] = 0;
        }
        
        // dynamic programming...
        for (int i = 1; i < dp.length; ++i) {
            Vertex vertex = vertices.get(i - 1);
            for (int x = 0; x < dp[0].length; ++x) {
                if (x < vertex.getWeight()) {  // we have no weight left to include this vertex.
                    dp[i][x] = dp[i - 1][x];
                } else {
                    dp[i][x] = Math.max(dp[i - 1][x], dp[i - 1][x - vertex.getWeight()] + vertex.getValue());
                }
            }
        }
        
        return dp[dp.length - 1][dp[0].length - 1];  // return the value of entire solution.
    }
    
    public static void main(String[] args) {
        final int W = 6;
        List<Vertex> vertices = new ArrayList<>();
        vertices.add(new Vertex(3, 4));
        vertices.add(new Vertex(2, 3));
        vertices.add(new Vertex(4, 2));
        vertices.add(new Vertex(4, 3));
        
        System.out.println(Knapsack.solve(vertices, W));
    }
}
