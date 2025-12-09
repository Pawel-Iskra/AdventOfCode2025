package day08;

import utils.MyUtils;

import java.util.*;

public class Day08 {


    private static final double EPSILON = 1e-9;
    private static final int CONNECTIONS = 10;

    private static List<Coordinates> LIST_OF_BOXES_COORDINATES;
    private static int NUMBER_OF_BOXES;
    private static Graph BOXES_AS_GRAPH;

    record Coordinates(int x, int y, int z) {}


    static class Graph {
        private List<Integer> nodeList;
        private int vertices;
        private List<Integer>[] adjacencyList;
        private List<Edge>[] edgeListFromNode;
        private List<Edge> edgeList;
        private boolean[] visited;
        private int counter = 0;

        public Graph(int vertices) {
            this.visited = new boolean[vertices];
            this.vertices = vertices;
            this.nodeList = new ArrayList<>();
            this.edgeList = new ArrayList<>();
            this.edgeListFromNode = new List[vertices];
            this.adjacencyList = (List<Integer>[]) new List[vertices];
            for (int i = 0; i < vertices; i++) {
                adjacencyList[i] = new ArrayList<Integer>();
            }
            for (int i = 0; i < vertices; i++) {
                edgeListFromNode[i] = new ArrayList<>();
            }
        }

        List<Edge> getEdgeList() {
            return edgeList;
        }

        public List<Integer> getAdjacencyList(int vertex) {
            return adjacencyList[vertex];
        }

        public List<Edge> getEdgesFromNode(int vertex) {
            return edgeListFromNode[vertex];
        }

        public void addEdge(int v1, int v2, double dist) {
            Edge edge1 = new Edge(v1, v2, dist);
            Edge edge2 = new Edge(v2, v1, dist);
            edgeList.add(edge1);
            edgeList.add(edge2);
            edgeListFromNode[v1].add(edge1);
            edgeListFromNode[v2].add(edge2);
            adjacencyList[v1].add(v2);
            adjacencyList[v2].add(v1);
        }

        public List<Edge> getEdgesByDistance(double distance) {
            return edgeList.stream()
                    .filter(edge -> Math.abs(edge.getDistance() - distance) < EPSILON)
                    .toList();
        }

        public int getNumberOfNodesPossibleFromNode(int start) {
            counter = 0;
            this.visited = new boolean[vertices];
            depthFirstSearch(start);
            return counter;
        }

        public List<Integer> getNodeListPossibleFromNode(int start) {
            this.nodeList = new ArrayList<>();
            this.visited = new boolean[vertices];
            depthFirstSearch(start);
            return nodeList;
        }

        public void depthFirstSearch(int vertex) {
            visited[vertex] = true;
            counter++;
            nodeList.add(vertex);
            for (int currentVertexFromAdjacency : getAdjacencyList(vertex)) {
                if (!visited[currentVertexFromAdjacency]) {
                    depthFirstSearch(currentVertexFromAdjacency);
                }
            }
        }

        @Override
        public String toString() {
            return "Graph{" +
                    "vertices=" + vertices +
                    ", adjacencyList=" + Arrays.toString(adjacencyList) +
                    ", edgeListFromNode=" + Arrays.toString(edgeListFromNode) +
                    '}';
        }
    }


    static class Edge implements Comparable<Edge> {
        private int from;
        private int to;
        private double distance;

        public Edge(int from, int to, double distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public int compareTo(final Edge other) {
            return Double.compare(this.distance, other.distance);
        }

        @Override
        public String toString() {
            return "\nEdge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", distance=" + distance +
                    '}';
        }
    }

    private static void prepareData(List<String> input) {
        LIST_OF_BOXES_COORDINATES = new ArrayList<>();
        for (String currentLine : input) {
            String[] coordinates = currentLine.split(",");
            LIST_OF_BOXES_COORDINATES.add(new Coordinates(
                    Integer.parseInt(coordinates[0]),
                    Integer.parseInt(coordinates[1]),
                    Integer.parseInt(coordinates[2])));
        }
        NUMBER_OF_BOXES = LIST_OF_BOXES_COORDINATES.size();
        BOXES_AS_GRAPH = new Graph(NUMBER_OF_BOXES);
        for (int i = 0; i < NUMBER_OF_BOXES; i++) {
            Coordinates startCoords = LIST_OF_BOXES_COORDINATES.get(i);
            for (int j = i + 1; j < NUMBER_OF_BOXES; j++) {
                Coordinates endCoords = LIST_OF_BOXES_COORDINATES.get(j);
                double distance = Math.sqrt(Math.pow(startCoords.x - endCoords.x, 2) +
                        Math.pow(startCoords.y - endCoords.y, 2) +
                        Math.pow(startCoords.z - endCoords.z, 2)
                );
                BOXES_AS_GRAPH.addEdge(i, j, distance);
            }
        }

    }

    public static void partTwo() {
        System.out.println("\nPART II:");
        Graph boxesCircuitsGraph = new Graph(NUMBER_OF_BOXES);
        List<Edge> edgeList = BOXES_AS_GRAPH.getEdgeList();
        Collections.sort(edgeList);
        int index = 0;
        while (true) { // for 1000 nodes it takes about 2min to solve
            List<Edge> edgeWithSmallestDist = BOXES_AS_GRAPH.getEdgesByDistance(edgeList.get(index).getDistance());
            index += 2;
            boxesCircuitsGraph.addEdge(edgeWithSmallestDist.get(0).getTo(), edgeWithSmallestDist.get(0).getFrom(), 1);
            if (index < NUMBER_OF_BOXES / 2) continue;

            int nodes = boxesCircuitsGraph.getNumberOfNodesPossibleFromNode(edgeWithSmallestDist.get(0).getTo());
            if (nodes == NUMBER_OF_BOXES) {
                long result = (long) LIST_OF_BOXES_COORDINATES.get((edgeWithSmallestDist.get(0).getTo())).x *
                        LIST_OF_BOXES_COORDINATES.get((edgeWithSmallestDist.get(0).getFrom())).x;
                System.out.println("result = " + result);
                break;
            }
        }
    }

    public static void partOne() {
        System.out.println("PART I:");
        Graph boxesCircuitsGraph = new Graph(NUMBER_OF_BOXES);
        List<Edge> edgeList = BOXES_AS_GRAPH.getEdgeList();
        Collections.sort(edgeList);
        for (int i = 0; i < CONNECTIONS; i++) {
            List<Edge> edgeWithSmallestDist = BOXES_AS_GRAPH.getEdgesByDistance(edgeList.get(2 * i).getDistance());
            boxesCircuitsGraph.addEdge(edgeWithSmallestDist.get(0).getTo(), edgeWithSmallestDist.get(0).getFrom(), 1);
        }
        List<Integer> amountOfBoxesInCircuits = new ArrayList<>();
        Set<Set<Integer>> paths = new HashSet<>();
        for (int i = 0; i < NUMBER_OF_BOXES; i++) {
            List<Integer> path = (boxesCircuitsGraph.getNodeListPossibleFromNode(i));
            Collections.sort(path);
            paths.add(new HashSet<>(path));
        }
        for (Set<Integer> currentPath : paths) {
            amountOfBoxesInCircuits.add(currentPath.size());
        }
        Collections.sort(amountOfBoxesInCircuits);
        long result = amountOfBoxesInCircuits.stream()
                .skip(amountOfBoxesInCircuits.size() - 3)
                .reduce(1, (a, b) -> a * b);
        System.out.println("result = " + result);
    }


    public static void main(String[] args) {
        String pathToInputFile = "src/main/resources/day08.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        prepareData(inputLines);
        partOne();
        partTwo();
    }
}
