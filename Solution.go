
package main

import (
	"container/heap"
	"fmt"
	"math"
)

type Point struct {
	ID                int
	distanceFromStart int
}

var INFINITY_POINT = math.MaxInt
var NO_PATH_FOUND = -1

var numberOfNodes = 0
var markedAsBoolean []bool
var directedGraph []map[int]int

func minimumDistance(totalNodes int, edges [][]int, startID int, markedID []int) int {
	numberOfNodes = totalNodes
	directedGraph = createDirectedGraph(edges)
	markedAsBoolean = createArrayMarkedAsBoolean(markedID)

	return findShortestPathWithDijkstraSearch(startID)
}

func findShortestPathWithDijkstraSearch(startID int) int {
	minHeap := make(PriorityQueue, 0)
	item := &Item{
		value:    Point{startID, 0},
		priority: 0,
	}
	heap.Push(&minHeap, item)

	var minDistance = make([]int, numberOfNodes)
	for nodeId := 0; nodeId < numberOfNodes; nodeId++ {
		minDistance[nodeId] = INFINITY_POINT
	}
	minDistance[startID] = 0

	for minHeap.Len() > 0 {
		var current = heap.Pop(&minHeap).(*Item).value.(Point)
		if markedAsBoolean[current.ID] {
			return current.distanceFromStart
		}

		for next, edgeDistance := range directedGraph[current.ID] {
			if minDistance[next] > current.distanceFromStart+edgeDistance {
				minDistance[next] = current.distanceFromStart + edgeDistance
				item := &Item{
					value:    Point{next, minDistance[next]},
					priority: minDistance[next],
				}
				heap.Push(&minHeap, item)
			}
		}
	}

	return NO_PATH_FOUND
}

func createDirectedGraph(edges [][]int) []map[int]int {
	var graph = make([]map[int]int, numberOfNodes)
	for nodeID := 0; nodeID < numberOfNodes; nodeID++ {
		graph[nodeID] = map[int]int{}
	}

	for _, edge := range edges {
		from := edge[0]
		to := edge[1]
		distance := edge[2]

		if _, contains := graph[from][to]; contains && graph[from][to] < distance {
			distance = graph[from][to]
		}
		graph[from][to] = distance
	}
	return graph
}

func createArrayMarkedAsBoolean(markedID []int) []bool {
	marked := make([]bool, numberOfNodes)
	for _, node := range markedID {
		marked[node] = true
	}
	return marked
}

type Item struct {
	value    any
	priority int
	index    int
}

type PriorityQueue []*Item

func (pq PriorityQueue) Len() int {
	return len(pq)
}

func (pq PriorityQueue) Less(first int, second int) bool {
	return pq[first].priority < pq[second].priority
}

func (pq PriorityQueue) Swap(first int, second int) {
	pq[first], pq[second] = pq[second], pq[first]
	pq[first].index = first
	pq[second].index = second
}

func (pq *PriorityQueue) Push(object any) {
	length := len(*pq)
	item := object.(*Item)
	item.index = length
	*pq = append(*pq, item)
}

func (pq *PriorityQueue) Pop() any {
	pqBeforePop := *pq
	length := len(pqBeforePop)

	item := pqBeforePop[length-1]
	pqBeforePop[length-1] = nil

	item.index = -1
	*pq = pqBeforePop[0 : length-1]

	return item
}
