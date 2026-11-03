package com.ankur.Lab_4;

import java.util.*;

public class Ans {
    private record Edge(char dest, int value) {
    }

    static class Graph {
        private final Set<Character> nodes;
        private final Map<Character, List<Edge>> graph;

        public Graph(Set<Character> nodes) {
            this.nodes = nodes;
            this.graph = new HashMap<>();

            for (char src : nodes) {
                graph.put(src, new ArrayList<>());
            }
        }

        public void addNode(char src, char dest, int value) {
            List<Edge> ret = graph.get(src);
            ret.add(new Edge(dest, value));
        }

        public int computeScore(char src) {
            return 0;
        }

        public int computeScore(char dest, char src) {
            Map<Character, Integer> distance = new HashMap<>();

            for (char node : nodes) {
                distance.put(node, Integer.MAX_VALUE);
            }

            distance.put(src, 0);

            PriorityQueue<Character> pq =
                    new PriorityQueue<>(Comparator.comparingInt(distance::get));

            pq.add(src);

            while (!pq.isEmpty()) {
                char current = pq.poll();

                // We reached the destination
                if (current == dest) {
                    return distance.get(current);
                }

                for (Edge edge : graph.getOrDefault(current, List.of())) {
                    char next = edge.dest();
                    int newDistance = distance.get(current) + edge.value();

                    if (newDistance < distance.get(next)) {
                        distance.put(next, newDistance);
                        pq.add(next);
                    }
                }
            }

            // No path exists
            return -1;
        }
    }

    static Graph initGraph() {
        Set<Character> nodes = new HashSet<>(7);
        nodes.add('H');
        nodes.add('D');
        nodes.add('C');
        nodes.add('T');
        nodes.add('F');
        nodes.add('E');
        nodes.add('W');

        Graph graph = new Graph(nodes);
        graph.addNode('H', 'D', 1);
        graph.addNode('D', 'C', -1);
        graph.addNode('C', 'T', 1);
        graph.addNode('D', 'W', 1);
        graph.addNode('H', 'E', 1);
        graph.addNode('E', 'W', 1);
        graph.addNode('F', 'E', -1);
        return graph;
    }

    static void main() {
        Graph graph = initGraph();

        System.out.printf("a) For P(t=1 | h=1) and P(t=1): %s is greater\n", (graph.computeScore('T', 'H') > graph.computeScore('T')) ? "P(t=1 | h=1)" : "P(t=1)");

        char[][] states = {
                {'W', 'H', 'W', 'E'},
                {'C', 'E', 'C', 'F'},
                {'C', 'E', 'C', 'F'},
                {'W', 'E', 'W', 'D'}
        };
        int[][] stateValues = {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 0},
                {1, 1, 1, 1}
        };

        char c = 'b';
        for (char[] state : states) {
            final String state1 = String.format("P(%s=1 | %s=1)", state[0], state[1]);
            final String state2 = String.format("P(%s=1 | %s=1)", state[2], state[3]);
            String winner = (graph.computeScore(state[0], state[1])> graph.computeScore(state[2], state[3])) ? state1 : state2;

            System.out.printf("%s) %s and %s: %s is greater\n", c, state1, state2, winner);
            c++;
        }
    }
}
