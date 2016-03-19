package hammingdist;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class HammingDisCluster {
    private final int NumNodes;
    private final int NumBits;
    
    private Set<Integer> nodes;
    
    private final List<Integer> oneBitCombines;
    private final List<Integer> twoBitCombines;
    private final List<Integer> combines;
    
    private List<List<Integer>> clusters;
    
    private int count = 0;
    
    public HammingDisCluster(BufferedReader reader) throws Exception {
        String[] summary = reader.readLine().split(" ", 2);
        
        NumNodes = Integer.parseInt(summary[0]);
        NumBits = Integer.parseInt(summary[1]);
        
        // Prepares all combines
        oneBitCombines = BitSetGenerator.generateOneBitCombines(NumBits);
        twoBitCombines = BitSetGenerator.generateTwoBitsCombines(NumBits);
        combines = new ArrayList<>(oneBitCombines);
        combines.addAll(twoBitCombines);
        
        // Populate all nodes.
        nodes = new HashSet<>(reader.lines().map(line -> Integer.parseInt(join(line.split(" ")), 2)).collect(Collectors.toList()));
        
        clusters = new ArrayList<>();
    }
    
    public void solve() {
        while (nodes.iterator().hasNext()) {
            Queue<Integer> newLeaders = new LinkedList<>();
            Integer leader = nodes.iterator().next();
            nodes.remove(leader);
            newLeaders.add(leader);
            
            while (!newLeaders.isEmpty()) {
                leader = newLeaders.poll();
                for (Integer comb : combines) {
                    Integer xored = leader ^ comb;
                    if (nodes.contains(xored)) {
                        nodes.remove(xored);
                        newLeaders.add(xored);
                    }
                }
            }
            ++count;
        }
        System.out.println(clusters.size());
        System.out.println(count);
    }
        
    private String join(String[] strings) {
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string);
        }
        return sb.toString();
    }
    
    public static void main(String[] args) throws Exception {
        FileReader fileReader = new FileReader("resources/clustering_big.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        new HammingDisCluster(bufferedReader).solve();
    }
    
}
