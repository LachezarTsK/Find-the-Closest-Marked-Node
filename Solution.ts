
function minimumDistance(numberOfNodes: number, edges: number[][], startID: number, markedID: number[]): number {
    this.INFINITY_POINT = Number.POSITIVE_INFINITY;
    this.NO_PATH_FOUND = -1;

    this.numberOfNodes = numberOfNodes;
    this.directedGraph = createDirectedGraph(edges);
    this.markedAsBoolean = createArrayMarkedAsBoolean(markedID);

    return findShortestPathWithDijkstraSearch(startID);
};

function Point(ID: number, distanceFromStart: number) {
    this.ID = ID;
    this.distanceFromStart = distanceFromStart;
}

function findShortestPathWithDijkstraSearch(startID) {

    const { MinPriorityQueue } = require('@datastructures-js/priority-queue');
    const minHeap = new MinPriorityQueue({ compare: (first, second) => first.distanceFromStart - second.distanceFromStart });
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

function createDirectedGraph(edges: number[][]): Map<number, number>[] {
    const graph: Map<number, number>[] = new Array(this.numberOfNodes);
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

function createArrayMarkedAsBoolean(markedID: number[]): boolean[] {
    const marked: boolean[] = new Array(this.numberOfNodes).fill(false);
    for (let node of markedID) {
        marked[node] = true;
    }
    return marked;
}
