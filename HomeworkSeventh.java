import java.util.*;
import java.util.LinkedList;

public class HomeworkSeventh {
    public static void main(String[] args) {
        //Input
        int n = 10;

        //Output
        Graph graph = new Graph(n);
        graph.createEdges();
        graph.displayMatrix();
        System.out.println();
        graph.getRoutes(0, graph.getSize() - 1);
    }
}

class Vertex {
    private final char label;

    public Vertex(char label) {
        this.label = label;
    }

    public char getLabel() {
        return label;
    }
}

class Graph {
    private final ArrayList<Vertex> vertexList;
    private final int[][] adjMatrix;
    private final int size;

    public Graph(int vertexes) {
        this.vertexList = new ArrayList<>();
        int i = 0;
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            if (i < vertexes)
                vertexList.add(new Vertex(alphabet));
            i++;
        }
        size = vertexes;
        this.adjMatrix = new int[vertexes][vertexes];
        for (int[] row : adjMatrix) {
            for (int number: row ) {
                number = 0;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void addEdge(int first, int second){
        adjMatrix[first][second] = 1;
        adjMatrix[second][first] = 1;
    }

    public void displayVertex (int vertex) {
        System.out.print(vertexList.get(vertex).getLabel() + "\t");
    }

    public void createEdges() {
        for (int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if (i != j && new Random().nextInt(5) < 1)
                   addEdge(i, j);
            }
        }
        adjMatrix[0][size - 1] = 0;
        adjMatrix[size - 1][0] = 0;
    }

    public void displayMatrix() {
        System.out.print("\t");
        for(int i = 0; i < size; i++) {
            displayVertex(i);
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            displayVertex(i);
            for(int j = 0; j < size; j++) {
                String buffer = adjMatrix[i][j] + "\t";
                System.out.print(buffer);
                if (j == size - 1)
                    System.out.println();
            }
        }
    }

    public void getRoutes(int start, int end) {
        int[] prev = new int[size];
        int[] distance = new int[size];

        if (!getRoutes(start, end, prev, distance)) {
            System.out.println("No conceivable pass");
            return;
        }

        LinkedList<Integer> path = new LinkedList<>();
        int crawl = end;
        path.add(crawl);
        while (prev[crawl] != -1) {
            path.add(prev[crawl]);
            crawl = prev[crawl];
        }

        System.out.println("Shortest path length is: " + distance[end]);

        System.out.print("Path is : ");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(vertexList.get(path.get(i)).getLabel());
        }
        System.out.println();
    }

    private boolean getRoutes(int start, int end, int[] prev, int[] distance) {
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[size];
        for (int i = 0; i < size; i++) {
            visited[i] = false;
            distance[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }
        visited[start] = true;
        distance[start] = 0;
        queue.add(start);

        while (!queue.isEmpty()) {
            int current = queue.remove();
            ArrayList<Vertex> children = getChildren(vertexList.get(current));
            if (children!= null && !children.isEmpty()) {
                for (Vertex child: children) {
                    int index = vertexList.indexOf(child);
                    if (!visited[index]) {
                        visited[index] = true;
                        distance[index] = distance[current] + 1;
                        prev[index] = current;
                        queue.add(index);
                        if (index == end)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    private ArrayList<Vertex> getChildren(Vertex vertex) {
        if (this.size < 1)
            return null;
        ArrayList<Vertex> children = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Vertex current = vertexList.get(i);
            if (current.getLabel() == vertex.getLabel()) {
                for (int n = 0; n < size; n++) {
                    if (adjMatrix[i][n] == 1) {
                        children.add(vertexList.get(n));
                    }
                }
            }
        }
        return children;
    }
}
