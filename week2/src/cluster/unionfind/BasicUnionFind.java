package cluster.unionfind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BasicUnionFind implements UnionFind {
    
    private int[] leaders;
    private int count;
    
    
    public BasicUnionFind(int N) {
        count = N;
        leaders = new int[N];
        for (int i = 0; i < N; ++i) {
            leaders[i] = i;
        }
    }


    @Override
    public int find(int x) {
        validate(x);
        return leaders[x];
    }


    @Override
    public void union(int p, int q) {
        validate(p);
        validate(q);
        int pLeader = leaders[p];
        int qLeader = leaders[q];
        
        if (pLeader == qLeader) { return; }
        
        for (int i = 0; i < leaders.length; ++i) {
            if (leaders[i] == pLeader) leaders[i] = qLeader;
        }
        --count;
    }
    
    @Override
    public boolean connected(int p, int q) {
        validate(p);
        validate(q);
        return leaders[p] == leaders[q];
    }

    @Override
    public List<List<Integer>> getClusters() {
        HashMap<Integer, List<Integer>> clusterMap = new HashMap<>();
        for (int i = 0; i < leaders.length; ++i) {
            int leader = leaders[i];
            if (!clusterMap.containsKey(leader)) {
                clusterMap.put(leader, new ArrayList<>());
            }
            List<Integer> cluster = clusterMap.get(leader);
            cluster.add(i);
        }
        return clusterMap.values().stream().collect(Collectors.toList());
    }


    @Override
    public int count() {
        return count;
    }
    
    private void validate(int x) {
        if (x < 0 || x > leaders.length) {
            throw new IndexOutOfBoundsException();
        }
    }


    
    

}
