
using System;
using System.Collections.Generic;

public class Solution
{
    private sealed record Point(int ID, int distanceFromStart) {}

    private static readonly int INFINITY_POINT = int.MaxValue;
    private static readonly int NO_PATH_FOUND = -1;

    private int numberOfNodes;
    private bool[]? markedAsBoolean;
    private Dictionary<int, int>[]? directedGraph;

    public int MinimumDistance(int numberOfNodes, IList<IList<int>> edges, int startID, int[] markedID)
    {
        this.numberOfNodes = numberOfNodes;
        this.directedGraph = CreateDirectedGraph(edges);
        this.markedAsBoolean = CreateArrayMarkedAsBoolean(markedID);

        return FindShortestPathWithDijkstraSearch(startID);
    }

    private int FindShortestPathWithDijkstraSearch(int startID)
    {
        Comparer<int> comparator = Comparer<int>.Create((first, second) => first.CompareTo(second));
        PriorityQueue<Point, int> minHeap = new PriorityQueue<Point, int>(comparator);
        minHeap.Enqueue(new Point(startID, 0), 0);

        int[] minDistance = new int[numberOfNodes];
        Array.Fill(minDistance, INFINITY_POINT);
        minDistance[startID] = 0;

        while (minHeap.Count > 0)
        {
            Point current = minHeap.Dequeue();
            if (markedAsBoolean![current.ID])
            {
                return current.distanceFromStart;
            }

            foreach (int next in directedGraph![current.ID].Keys)
            {
                if (minDistance[next] > current.distanceFromStart + directedGraph![current.ID][next])
                {
                    minDistance[next] = current.distanceFromStart + directedGraph![current.ID][next];
                    minHeap.Enqueue(new Point(next, minDistance[next]), minDistance[next]);
                }
            }
        }

        return NO_PATH_FOUND;
    }

    private Dictionary<int, int>[] CreateDirectedGraph(IList<IList<int>> edges)
    {
        var graph = new Dictionary<int, int>[numberOfNodes];
        for (int nodeID = 0; nodeID < numberOfNodes; ++nodeID)
        {
            graph[nodeID] = new Dictionary<int, int>();
        }

        foreach (IList<int> edge in edges)
        {
            int from = edge[0];
            int to = edge[1];
            int distance = edge[2];

            if (graph[from].ContainsKey(to) && graph[from][to] < distance)
            {
                distance = graph[from][to];
            }
            graph[from][to] = distance;
        }
        return graph;
    }

    private bool[] CreateArrayMarkedAsBoolean(int[] markedID)
    {
        bool[] marked = new bool[numberOfNodes];
        foreach (int node in markedID)
        {
            marked[node] = true;
        }
        return marked;
    }
}
