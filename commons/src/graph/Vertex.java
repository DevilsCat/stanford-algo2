package graph;

public class Vertex<V> {
    private int id;
    private V value;

    public Vertex(int id, V value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Vertex [id=" + id + ", value=" + value + "]";
    }
    
}