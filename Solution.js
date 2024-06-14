
/**
 * @param {number} numberOfNodes
 * @param {number[][]} edges
 * @param {number} startID
 * @param {number[]} markedID
 * @return {number}
 */
var minimumDistance = function (numberOfNodes, edges, startID, markedID) {
    this.INFINITY_POINT = Number.POSITIVE_INFINITY;
    this.NO_PATH_FOUND = -1;

    this.numberOfNodes = numberOfNodes;
    this.directedGraph = createDirectedGraph(edges);
    this.markedAsBoolean = createArrayMarkedAsBoolean(markedID);

    return findShortestPathWithDijkstraSearch(startID);
};

/**
 * @param {number} ID
 * @param {number} distanceFromStart 
 */
function Point(ID, distanceFromStart) {
    this.ID = ID;
    this.distanceFromStart = distanceFromStart;
}

/**
 * @param {number} startID
 * @return {number}  
 */
function findShortestPathWithDijkstraSearch(startID) {

    // const {MinPriorityQueue} = require('@datastructures-js/priority-queue');
    const minHeap = new MinPriorityQueue({compare: (first, second) => first.distanceFromStart - second.distanceFromStart});
    minHeap.enqueue(new Point(startID, 0));

    const minDistance = new Array(this.numberOfNodes).fill(this.INFINITY_POINT);
    minDistance[startID] = 0;

    while (!minHeap.isEmpty()) {
        const current = minHeap.dequeue();
        if (this.markedAsBoolean[current.ID]) {
            return current.distanceFromStart;
        }

        for (let next of this.directedGraph[current.ID].keys()) {
            if (minDistance[next] > current.distanceFromStart + this.directedGraph[current.ID].get(next)) {
                minDistance[next] = current.distanceFromStart + this.directedGraph[current.ID].get(next);
                minHeap.enqueue(new Point(next, minDistance[next]));
            }
        }
    }

    return this.NO_PATH_FOUND;
}

/**
 * @param {number[][]} edges
 * @return {Map<number,number>[]}
 */
function createDirectedGraph(edges) {
    const graph = new Array(this.numberOfNodes);
    for (let nodeID = 0; nodeID < this.numberOfNodes; ++nodeID) {
        graph[nodeID] = new Map();
    }

    for (let edge of edges) {
        let from = edge[0];
        let to = edge[1];
        let distance = edge[2];

        if (graph[from].has(to) && graph[from].get(to) < distance) {
            distance = graph[from].get(to);
        }
        graph[from].set(to, distance);
    }
    return graph;
}

/**
 * @param {number[]} markedID
 * @return {boolean[]}
 */
function createArrayMarkedAsBoolean(markedID) {
    const marked = new Array(this.numberOfNodes).fill(false);
    for (let node of markedID) {
        marked[node] = true;
    }
    return marked;
}
