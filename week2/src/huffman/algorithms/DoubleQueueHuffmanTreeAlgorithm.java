package huffman.algorithms;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import huffman.datastructures.HuffmanTreeNode;

public class DoubleQueueHuffmanTreeAlgorithm implements HuffmanTreeAlgorithm {
    Queue<HuffmanTreeNode> queue1;
    Queue<HuffmanTreeNode> queue2;
    
    @Override
    public HuffmanTreeNode makeTree(List<HuffmanTreeNode> nodes) {
        Collections.sort(nodes);  // sort all nodes in ascending order.
        
        queue1 = new LinkedList<>(nodes);
        queue2 = new LinkedList<>();
        
        HuffmanTreeNode root = null;
        
        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            if (queue1.size() == 1 && queue2.isEmpty()) {
                root = queue1.poll();
            } else if (queue2.size() == 1 && queue1.isEmpty()) {
                root = queue2.poll();
            } else {
                int idx = 0;  // extract twice.
                HuffmanTreeNode[] children = new HuffmanTreeNode[2];
                while (idx != 2) {
                    if (queue1.isEmpty()) {
                        children[idx++] = queue2.poll();
                    } else if (queue2.isEmpty()) {
                        children[idx++] = queue1.poll();
                    } else {
                        children[idx++] = queue1.peek().compareTo(queue2.peek()) <= 0 ? queue1.poll() : queue2.poll();
                    }
                }
                
                HuffmanTreeNode parent = children[0].merge(children[1]);
                queue2.offer(parent);
            }
        }
        
        return root;
    }

}
