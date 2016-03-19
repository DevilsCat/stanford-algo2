package huffman.datastructures;

public class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
    String chars = "";
    int frequency;
    HuffmanTreeNode left;
    HuffmanTreeNode right;
    
    public HuffmanTreeNode(char ch, int frequency) {
        this.chars += ch;
        this.frequency = frequency;
    }
    
    public HuffmanTreeNode(String chars, int frequency, HuffmanTreeNode left, HuffmanTreeNode right) {
        this.chars = chars;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public String getChars() {
        return chars;
    }
    
    public void setChars(String chars) {
        this.chars = chars;
    }
    
    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public HuffmanTreeNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanTreeNode left) {
        this.left = left;
    }

    public HuffmanTreeNode getRight() {
        return right;
    }

    public void setRight(HuffmanTreeNode right) {
        this.right = right;
    }
    
    public HuffmanTreeNode merge(HuffmanTreeNode that) {
        return new HuffmanTreeNode(
                this.getChars() + that.getChars(), 
                this.getFrequency() + that.getFrequency(), 
                this, that);
    }

    @Override
    public String toString() {
        return "HuffmanTreeNode [chars=" + chars + ", frequency=" + frequency + ", left=" + left + ", right=" + right
                + "]";
    }

    @Override
    public int compareTo(HuffmanTreeNode that) {
        return this.frequency - that.frequency;
    }
    
    
}
