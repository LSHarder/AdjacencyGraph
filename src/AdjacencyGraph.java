import java.util.ArrayList;

public class AdjacencyGraph {
    ArrayList<Vertex> vertices;

    public AdjacencyGraph() {
        vertices = new ArrayList<Vertex>();
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    public void addEdge(Vertex f, Vertex t, Integer w) {
        if (!(vertices.contains(f) && vertices.contains(t))) {
            System.out.println(" Vertex not in graph");
            return;
        }
        Edge e = new Edge(f, t, w);
    }

    public void PrintGraph() {
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println(" From Vertex: " + vertices.get(i).name);
            Vertex currentfrom = vertices.get(i);
            for (int j = 0; j < currentfrom.OutEdges.size(); j++) {
                Edge currentEdge = currentfrom.OutEdges.get(j);
                System.out.println(" To: " + currentEdge.to.name + " weight: " + currentEdge.weight);
            }
            System.out.println(" ");
        }
    }

    public void MSTPrims() {
        //ny algoritme til at udregne minimum spanning tree med prims
        MinHeap<Vertex> Q = new MinHeap<>();
        ArrayList<Vertex> inMST = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            Q.Insert(vertices.get(i));
        }
        if (vertices.size() > 0) {
            vertices.get(0).dist = 0;
            int pos = Q.getPosition(vertices.get(0));
            Q.decreasekey(pos);
        }

        while (!Q.isEmpty()) {
            Vertex u = Q.extractMin();
            inMST.add(u);
            for (int i = 0; i < u.OutEdges.size(); i++) {
                Edge e = u.OutEdges.get(i);
                if (e.weight < e.to.dist && !inMST.contains(e.to)) {
                    e.to.dist = e.weight;
                    e.to.prev = Integer.parseInt(u.name);
                    int pos2 = Q.getPosition(e.to);
                    Q.decreasekey(pos2);

                }
            }
        }
        int MST = 0;
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println("vekter " + i + " har vægt " + vertices.get(i).dist + " og har parent " + vertices.get(i).prev);
            MST = MST + vertices.get(i).dist;
        }
        System.out.println("Minimum spanning tree Disitance: " + MST + " Km");
        int Price = MST * 100000;
        System.out.println("Price of the cable is: " + Price + " Kr");
    }
}

class Vertex implements Comparable<Vertex>{
    String name;
    ArrayList<Edge> OutEdges;
    Integer dist= Integer.MAX_VALUE;
    Integer prev = null;
    public Vertex(String id){
        name=id;
        OutEdges=new ArrayList<Edge>();
    }
    public void addOutEdge(Edge e) {
        OutEdges.add(e);
    }

    @Override
    public int compareTo(Vertex o) {
        if (this.dist<o.dist)
            return -1;
        if (this.dist>o.dist)
            return 1;
        return 0;
    }
}

class Edge{
    Integer weight;
    Vertex from;
    Vertex to;
    public Edge(Vertex from, Vertex to, Integer cost){
        this.from=from;
        this.to=to;
        this.weight=cost;
        this.from.addOutEdge(this);
    }
}