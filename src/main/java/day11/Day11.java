package day11;

import utils.MyUtils;

import java.util.*;

public class Day11 {

    private static final String YOU = "you";
    private static final String OUT = "out";
    private static final String SVR = "svr";
    private static final String DAC = "dac";
    private static final String FFT = "fft";


    private static Graph GRAPH_NETWORK;
    private static List<String> NODE_LIST;


    record Edge(int from, int to) {
    }

    static class Graph {
        private final int vertices;
        private final List<Integer>[] adjacencyList;
        private final List<Edge>[] edgeListFromNode;
        private final boolean[] visited;

        public Graph(int vertices) {
            this.visited = new boolean[vertices];
            this.vertices = vertices;
            this.edgeListFromNode = new List[vertices];
            this.adjacencyList = (List<Integer>[]) new List[vertices];
            for (int i = 0; i < vertices; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            for (int i = 0; i < vertices; i++) {
                edgeListFromNode[i] = new ArrayList<>();
            }
        }

        public List<Integer> getAdjacencyList(int vertex) {
            return adjacencyList[vertex];
        }

        public void addEdge(int from, int to) {
            Edge edge = new Edge(from, to);
            edgeListFromNode[from].add(edge);
            adjacencyList[from].add(to);
        }

        public List<List<Integer>> getAllPossiblePathsFromTo(int from, int to) { // BFS
            List<List<Integer>> pathsFromTo = new ArrayList<>();

            Queue<List<Integer>> pathsQueue = new ArrayDeque<>();
            pathsQueue.add(List.of(from));
            while (!pathsQueue.isEmpty()) {
                List<Integer> currentPath = pathsQueue.poll();
                Integer currentNode = currentPath.getLast();
                if (currentNode == to) {
                    pathsFromTo.add(currentPath);
                    continue;
                }
                for (Integer nextNode : getAdjacencyList(currentNode)) {
                    if (!currentPath.contains(nextNode)) {
                        List<Integer> newPath = new ArrayList<>(currentPath);
                        newPath.add(nextNode);
                        pathsQueue.add(newPath);
                    }
                }
            }
            return pathsFromTo;
        } // part 2-> OutOfMemoryError: Java heap space


        public int getCountAllPossiblePathsFromToContaining(int from, int to, List<Integer> nodesRequired) {
            return dfsFromToContaining(from, to, nodesRequired, new HashSet<>());
        } // too slow

        private int dfsFromToContaining(int current, int to, List<Integer> nodesRequired, Set<Integer> visited) {
            visited.add(current);
            if (current == to) {
                boolean containsAll = visited.containsAll(nodesRequired);
                visited.remove(current);
                return containsAll ? 1 : 0;
            }
            int counter = 0;
            for (int next : getAdjacencyList(current)) {
                if (!visited.contains(next)) {
                    counter += dfsFromToContaining(next, to, nodesRequired, visited);
                }
            }
            visited.remove(current);
            return counter;
        } // too slow

        public void depthFirstSearch(int vertex) {
            visited[vertex] = true;
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
                    '}';
        }
    }


    private static void prepareData(List<String> input) {
        Set<String> nodesSet = new HashSet<>();
        for (String line : input) {
            String[] lineParts = line.replace(":", "").split(" ");
            for (String current : lineParts) {
                nodesSet.add(current.strip());
            }
        }
        NODE_LIST = new ArrayList<>(nodesSet);
//        for (int i = 0; i < NODE_LIST.size(); i++) {
//            System.out.println(NODE_LIST.get(i) + " -> " + i);
//        }
        GRAPH_NETWORK = new Graph(NODE_LIST.size());
        for (String line : input) {
            String[] lineParts = line.replace(":", "").split(" ");
            for (int i = 1; i < lineParts.length; i++) {
                GRAPH_NETWORK.addEdge(
                        NODE_LIST.indexOf(lineParts[0].strip()), NODE_LIST.indexOf(lineParts[i].strip()));
            }
        }
//        System.out.println("graph = " + GRAPH_NETWORK);
    }


    static void partOne() {
        System.out.println("PART I:");
        int youNodeIndex = NODE_LIST.indexOf(YOU);
        int outNodeIndex = NODE_LIST.indexOf(OUT);
        List<List<Integer>> resultPaths = GRAPH_NETWORK.getAllPossiblePathsFromTo(youNodeIndex, outNodeIndex);

//        System.out.println("resultPaths = ");
//        for (List<Integer> path : resultPaths) {
//            for (int node : path) {
//                System.out.print(NODE_LIST.get(node));
//                if (!OUT.equals(NODE_LIST.get(node))) {
//                    System.out.print(" -> ");
//                }
//            }
//            System.out.println();
//        }

        System.out.println("resultPaths.size() = " + resultPaths.size());
    }


    static void partTwo() {
        System.out.println("\nPART II:");
        int svrNodeIndex = NODE_LIST.indexOf(SVR);
        int outNodeIndex = NODE_LIST.indexOf(OUT);
        int dacNodeIndex = NODE_LIST.indexOf(DAC);
        int fftNodeIndex = NODE_LIST.indexOf(FFT);

        int result = GRAPH_NETWORK.getCountAllPossiblePathsFromToContaining(
                svrNodeIndex, outNodeIndex, List.of(dacNodeIndex, fftNodeIndex));
        System.out.println("result = " + result);
    }


    static void main() {
        String pathToInputFile = "src/main/resources/day11.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        prepareData(inputLines);
//        partOne();
        partTwo();
    }
}
