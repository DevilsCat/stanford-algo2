package huffman.algorithms;

import java.util.HashMap;
import java.util.List;

import huffman.datastructures.HuffmanTreeNode;

public class HuffmanEncoder {
    
    private HuffmanTreeAlgorithm algo;
    
    public HuffmanEncoder(HuffmanTreeAlgorithm algo) {
        this.algo = algo;
    }
    
    public HashMap<Character, String> encode(List<HuffmanTreeNode> nodes) {
        HuffmanTreeNode root = algo.makeTree(nodes);
                
        HashMap<Character, String> hashMap = new HashMap<>();
        visit(root, hashMap, "");
        return hashMap;
    }
    
    private void visit(HuffmanTreeNode node, HashMap<Character, String> hashMap,  String code) {
        if (node == null) { return; }
        
        if (node.getLeft() == null && node.getRight() == null) {  // hit a leaf.
            hashMap.put(node.getChars().charAt(0), code);
            return;
        }
        
        visit(node.getLeft(), hashMap, code + '0');
        visit(node.getRight(), hashMap, code + '1');
    }
}
