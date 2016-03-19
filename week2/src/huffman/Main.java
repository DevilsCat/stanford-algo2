package huffman;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import huffman.algorithms.DoubleQueueHuffmanTreeAlgorithm;
import huffman.algorithms.HuffmanEncoder;
import huffman.datastructures.HuffmanTreeNode;

public class Main {

    public static void main(String[] args) {
        List<HuffmanTreeNode> nodes = new ArrayList<>();
        
        nodes.add(new HuffmanTreeNode('a', 5));
        nodes.add(new HuffmanTreeNode('b', 9));
        nodes.add(new HuffmanTreeNode('c', 12));
        nodes.add(new HuffmanTreeNode('d', 13));
        nodes.add(new HuffmanTreeNode('e', 16));
        nodes.add(new HuffmanTreeNode('f', 45));
        
        HuffmanEncoder encoder = new HuffmanEncoder(new DoubleQueueHuffmanTreeAlgorithm());
        
        Map<Character, String> codeMap = encoder.encode(nodes);
        
        for (Character key : codeMap.keySet()) {
            System.out.println("char: " + key + ", code: " + codeMap.get(key));
        }
        
    }
}
