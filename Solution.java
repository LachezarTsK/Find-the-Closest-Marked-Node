
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Solution {

    private record Point(int ID, int distanceFromStart) {}
    
    private static final int INFINITY_POINT = Integer.MAX_VALUE;
    private static final int NO_PATH_FOUND = -1;

    private int numberOfNodes;
    private boolean[] markedAsBoolean;
    private Map<Integer, Integer>[] directedGraph;

    public int minimumDistance(int numberOfNodes, List<List<Integer>> edges, int startID, int[] markedID) {
        this.numberOfNodes = numberOfNodes;
        this.directedGraph = createDirectedGraph(edges);
        this.markedAsBoolean = createArrayMarkedAsBoolean(markedID);
        return findShortestPathWithDijkstraSearch(startID);
    }

    private int findShortestPathWithDijkstraSearch(int startID) {
        PriorityQueue<Point> minHeap = new PriorityQueue<>((first, second) -> first.distanceFromStart - second.distanceFromStart);
        minHeap.add(new Point(startID, 0));

        int[] minDistance = new int[numberOfNodes];
        Arrays.fill(minDistance, INFINITY_POINT);
        minDistance[startID] = 0;

        while (!minHeap.isEmpty()) {
            Point current = minHeap.poll();
            if (markedAsBoolean[current.ID]) {
                return current.distanceFromStart;
            }

            for (int next : directedGraph[current.ID].keySet()) {
                if (minDistance[next] > current.distanceFromStart + directedGraph[current.ID].get(next)) {
                    minDistance[next] = current.distanceFromStart + directedGraph[current.ID].get(next);
                    minHeap.add(new Point(next, minDistance[next]));
                }
            }
        }

        return NO_PATH_FOUND;
    }

    private Map<Integer, Integer>[] createDirectedGraph(List<List<Integer>> edges) {
        Map<Integer, Integer>[] graph = new HashMap[numberOfNodes];
        for (int nodeID = 0; nodeID < numberOfNodes; ++nodeID) {
            graph[nodeID] = new HashMap<>();
        }

        for (List<Integer> edge : edges) {
            int from = edge.get(0);
            int to = edge.get(1);
            int distance = edge.get(2);

            if (graph[from].containsKey(to) && graph[from].get(to) < distance) {
                distance = graph[from].get(to);
            }
            graph[from].put(to, distance);
        }
        return graph;
    }

    private boolean[] createArrayMarkedAsBoolean(int[] markedID) {
        boolean[] marked = new boolean[numberOfNodes];
        for (int node : markedID) {
            marked[node] = true;
        }
        return marked;
    }
}
