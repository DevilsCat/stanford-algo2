package graph;

public class Edge<W> {
    public Vertex<?> from;
    public Vertex<?> to;
    public W weight;

    public Edge(Vertex<?> from, Vertex<?> to, W weight) {
        super();
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Vertex<?> getFrom() {
        return from;
    }

    public void setFrom(Vertex<?> from) {
        this.from = from;
    }

    public Vertex<?> getTo() {
        return to;
    }

    public void setTo(Vertex<?> to) {
        this.to = to;
    }

    public W getWeight() {
        return weight;
    }

    public void setWeight(W weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
    }
    
}