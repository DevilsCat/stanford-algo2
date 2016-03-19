package huffman.datastructures;

import java.util.Arrays;

public class MinPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N;
    
    public MinPQ() {
        this(1);
    }
    
    @SuppressWarnings("unchecked")
    public MinPQ(int initCapacity) {
        pq = (Key[]) new Comparable[initCapacity + 1];
        N = 0;  
    }

    
    @SuppressWarnings("unchecked")
    public MinPQ(Key[] keys) {
        N = keys.length;
        pq = (Key[]) new Comparable[N + 1];
        for (int i = 0; i < N; ++i) {
            pq[i + 1] = keys[i];
        }
        for (int k = N / 2; k >= 1; --k) {
            sink(k);
        }
        assert(isMinHeap());
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }
    
    public Key min() {
        return pq[1];
    }
    
    public void insert(Key x) {
        if (N == pq.length - 1) { resize(); }
        pq[++N] = x;      
        swim(N);
        assert(isMinHeap());
    }
    
    public Key delMin() {
        if (N == 0) {
            throw new IndexOutOfBoundsException("PQ is empty");
        }
        Key res = pq[1];
        swap(1, N);
        pq[N--] = null;
        sink(1);
        return res;
    }
    
    private void swim(int k) {
        while (k != 1 && pq[k / 2].compareTo(pq[k]) > 0) {
            swap(k / 2, k);
            k /= 2;
        }
    }
    
    

    private void sink(int k) {
        while (2 * k <= N) {
            int minIdx;
            if (k * 2 + 1 > N) {
                minIdx = k * 2;
            } else {
                minIdx = pq[k * 2].compareTo(pq[k * 2 + 1]) < 0 ? k * 2 : k * 2 + 1;
            }
            if (pq[k].compareTo(pq[minIdx]) > 0) {
                swap(k, minIdx);
            }
            k = minIdx;
        }
    }
    
    private boolean isMinHeap() {
        return isMinHeap(1);
    }
    
    private boolean isMinHeap(int k) {
        if (2 * k > N) { return true; }
        boolean res = false;
        if (2 * k == N) {
            return pq[k].compareTo(pq[2 * k]) <= 0;
        }
        Key parent = pq[k];
        Key lhs = pq[2 * k];
        Key rhs = pq[2 * k + 1];
        res = parent.compareTo(lhs) <= 0 && parent.compareTo(rhs) <= 0;
        
        return res && isMinHeap(2 * k) && isMinHeap(2 * k + 1);
    }
    
    private void resize() {
        pq = Arrays.copyOf(pq, pq.length * 2);
    }
    
    private void swap(int x, int y) {
        Key tmp = pq[x];
        pq[x] = pq[y];
        pq[y] = tmp;
    }

    @Override
    public String toString() {
        return "MinPQ [pq=" + Arrays.toString(pq) + ", N=" + N + "]";
    }
    
    
    
}
