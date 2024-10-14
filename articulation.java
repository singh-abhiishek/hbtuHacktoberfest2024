import java.util.ArrayList;
import java.util.List;

class Graph {
    private int vertices;
    private List<List<Integer>> adj;

    public Graph(int v) {
        vertices = v;
        adj = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u); // For undirected graph
    }

    public void findArticulationPoints() {
        boolean[] visited = new boolean[vertices];
        int[] disc = new int[vertices]; // Discovery times of visited vertices
        int[] low = new int[vertices];   // Lowest points
        boolean[] ap = new boolean[vertices]; // To store articulation points
        int parent[] = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            parent[i] = -1;
            if (!visited[i]) {
                dfs(i, visited, disc, low, ap, parent);
            }
        }

        System.out.println("Articulation points in the graph:");
        for (int i = 0; i < vertices; i++) {
            if (ap[i]) {
                System.out.print(i + " ");
            }
        }
    }

    private void dfs(int u, boolean[] visited, int[] disc, int[] low, boolean[] ap, int[] parent) {
        visited[u] = true;
        disc[u] = low[u] = ++time;

        int children = 0;

        for (int v : adj.get(u)) {
            if (!visited[v]) {
                children++;
                parent[v] = u;
                dfs(v, visited, disc, low, ap, parent);

                low[u] = Math.min(low[u], low[v]);

                // u is an articulation point in following cases:
                if (parent[u] == -1 && children > 1) {
                    ap[u] = true;
                }
                if (parent[u] != -1 && low[v] >= disc[u]) {
                    ap[u] = true;
                }
            } else if (v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }

    private int time = 0;

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(3, 4);

        g.findArticulationPoints();
    }
}
