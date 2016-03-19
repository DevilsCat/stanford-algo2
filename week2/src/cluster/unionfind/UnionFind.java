package cluster.unionfind;

import java.util.List;

public interface UnionFind {
    public int find(int x);
    public void union(int p, int q);
    public boolean connected(int p, int q);
    public int count();
    public List<List<Integer>> getClusters();
}
