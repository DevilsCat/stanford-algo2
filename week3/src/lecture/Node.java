package lecture;

public class Node {
    private int value;
    private double frequency;
    private Node left;
    private Node right;
    
    public Node(int value, double frequency, Node left, Node right) {
        super();
        this.value = value;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
    
    public Node(int value, double frequency) {
        this(value, frequency, null, null);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node [value=" + value + ", frequency=" + frequency + ", left=" + left + ", right=" + right + "]";
    }
    
}
