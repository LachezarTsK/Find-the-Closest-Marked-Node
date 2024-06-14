
#include <span>
#include <queue>
#include <limits>
#include <vector>
#include <unordered_map>
using namespace std;

class Solution {

struct Point {
        int ID{};
        int distanceFromStart{};
        Point() = default;
        Point(int ID, int distanceFromStart) : ID{ ID }, distanceFromStart{ distanceFromStart } {}
    };

    struct ComparatorPoint {
        auto operator()(const Point& first, const Point& second) const {
            return first.distanceFromStart > second.distanceFromStart;
        }
    };

    static constexpr int INFINITY_POINT = numeric_limits<int>::max();
    static constexpr int NO_PATH_FOUND = -1;

    int numberOfNodes{};
    vector<bool> markedAsBoolean;
    vector<unordered_map<int, int>> directedGraph;

public:
    int minimumDistance(int numberOfNodes, const vector<vector<int>>& edges, int startID, const vector<int>& markedID) {
        this->numberOfNodes = numberOfNodes;
        directedGraph = createDirectedGraph(edges);
        markedAsBoolean = createArrayMarkedAsBoolean(markedID);

        return findShortestPathWithDijkstraSearch(startID);
    }

private:
    int findShortestPathWithDijkstraSearch(int startID) {
        priority_queue<Point, vector<Point>, ComparatorPoint> minHeap;
        minHeap.emplace(startID, 0);

        vector<int> minDistance(numberOfNodes, INFINITY_POINT);
        minDistance[startID] = 0;

        while (!minHeap.empty()) {
            Point current = minHeap.top();
            minHeap.pop();
            if (markedAsBoolean[current.ID]) {
                return current.distanceFromStart;
            }

            for (const auto& [nextID, distance] : directedGraph[current.ID]) {
                if (minDistance[nextID] >
                    current.distanceFromStart + distance) {
                    minDistance[nextID] = current.distanceFromStart + distance;
                    minHeap.emplace(nextID, minDistance[nextID]);
                }
            }
        }

        return NO_PATH_FOUND;
    }

    vector<unordered_map<int, int>> createDirectedGraph(span<const vector<int>> edges) const {
        vector<unordered_map<int, int>> graph(numberOfNodes);

        for (const auto& edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int distance = edge[2];

            if (graph[from].contains(to) && graph[from][to] < distance) {
                distance = graph[from][to];
            }
            graph[from][to] = distance;
        }

        return graph;
    }

    vector<bool> createArrayMarkedAsBoolean(span<const int> markedID) const {
        vector<bool> marked(numberOfNodes);
        for (const auto& node : markedID) {
            marked[node] = true;
        }
        return marked;
    }
};
