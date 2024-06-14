
import java.util.PriorityQueue
import java.util.Arrays

class Solution {

    private data class Point(val ID: Int, val distanceFromStart: Int) {}

    companion object {
        const val INFINITY_POINT = Int.MAX_VALUE
        const val NO_PATH_FOUND = -1
    }

    private var numberOfNodes: Int = 0
    private lateinit var markedAsBoolean: BooleanArray
    private lateinit var directedGraph: List<HashMap<Int, Int>>

    fun minimumDistance(numberOfNodes: Int, edges: List<List<Int>>, startID: Int, markedID: IntArray): Int {
        this.numberOfNodes = numberOfNodes
        this.directedGraph = createDirectedGraph(edges)
        this.markedAsBoolean = createArrayMarkedAsBoolean(markedID)
        
        return findShortestPathWithDijkstraSearch(startID)
    }


    private fun findShortestPathWithDijkstraSearch(startID: Int): Int {
        val minHeap = PriorityQueue<Point> { first, second -> first.distanceFromStart - second.distanceFromStart }
        minHeap.add(Point(startID, 0))

        val minDistance = IntArray(numberOfNodes)
        Arrays.fill(minDistance, INFINITY_POINT)
        minDistance[startID] = 0

        while (!minHeap.isEmpty()) {
            val current = minHeap.poll()
            if (markedAsBoolean[current.ID]) {
                return current.distanceFromStart
            }

            for (next in directedGraph[current.ID].keys) {
                if (minDistance[next] > current.distanceFromStart + directedGraph[current.ID][next]!!) {
                    minDistance[next] = current.distanceFromStart + directedGraph[current.ID][next]!!
                    minHeap.add(Point(next, minDistance[next]))
                }
            }
        }

        return NO_PATH_FOUND
    }


    private fun createDirectedGraph(edges: List<List<Int>>): ArrayList<HashMap<Int, Int>> {
        val graph = ArrayList<HashMap<Int, Int>>(numberOfNodes)
        for (nodeID in 0..<numberOfNodes) {
            graph.add(HashMap<Int, Int>())
        }

        for (edge in edges) {
            val from = edge[0]
            val to = edge[1]
            var distance = edge[2]

            if (graph[from].containsKey(to) && graph[from][to]!! < distance) {
                distance = graph[from][to]!!
            }
            graph[from][to] = distance
        }
        return graph
    }

    private fun createArrayMarkedAsBoolean(markedID: IntArray): BooleanArray {
        val marked = BooleanArray(numberOfNodes)
        for (node in markedID) {
            marked[node] = true
        }
        return marked
    }
}
