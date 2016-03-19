package huffman.algorithms;

import java.util.List;

import huffman.datastructures.HuffmanTreeNode;
import huffman.datastructures.MinPQ;

public class PQHuffmanTreeAlgorithm implements HuffmanTreeAlgorithm {
    
    @Override
    public HuffmanTreeNode makeTree(List<HuffmanTreeNode> nodes) {
        if (nodes == null || nodes.isEmpty()) { return null; }  // error check
        
        MinPQ<HuffmanTreeNode> pq = new MinPQ<>(nodes.toArray(new HuffmanTreeNode[nodes.size()]));
        
        while (pq.size() != 1) {
            HuffmanTreeNode left = pq.delMin();
            HuffmanTreeNode right = pq.delMin();
            HuffmanTreeNode merged = left.merge(right);
            pq.insert(merged);
        }
        
        return pq.min();
    }

}
