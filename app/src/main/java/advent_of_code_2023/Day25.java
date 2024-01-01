package advent_of_code_2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Day25 {

    static class Edge {

        String s;
        String t;
        int w;

        public Edge(String s, String t, int w) {
            this.s = s;
            this.t = t;
            this.w = w;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            final Edge that = (Edge) o;
            return w == that.w && Objects.equals(s, that.s) && Objects.equals(t, that.t);
        }

        @Override
        public int hashCode() {
            return Objects.hash(s, t, w);
        }

    }

    static Edge nextEdge(Set<String> nodes, Map<String, Map<String, Integer>> edges) {
        var weights = new ArrayList<Integer>(nodes.size());
        var remaining = new HashSet<>(nodes);
        var randomPick = nodes.iterator().next();
        var last = randomPick;
        var nextLast = last;

        remaining.remove(randomPick);

        var maxWeight = Integer.MIN_VALUE;
        while (!remaining.isEmpty()) {
            maxWeight = Integer.MIN_VALUE;
            nextLast = last;

            for (var edge : remaining) {
                int weightSum = 0;

                for (var e : edges.get(edge).entrySet()) {
                    if (!remaining.contains(e.getKey())) {
                        weightSum += e.getValue();
                    }
                }

                if (weightSum > maxWeight) {
                    last = edge;
                    maxWeight = weightSum;
                }
            }

            remaining.remove(last);
            weights.add(maxWeight);
        }

        return new Edge(
                nextLast,
                last,
                weights.getLast());
    }

    static int solve(String[] input) {
        var nodes = new HashSet<String>();
        var edges = new HashMap<String, Map<String, Integer>>();

        for (var line : input) {
            var part = line.split("([ :])");
            for (var i = 2; i < part.length; i++) {
                nodes.add(part[0]);
                nodes.add(part[i]);

                var s1 = edges.getOrDefault(part[0], new HashMap<>());
                s1.put(part[i], 1);
                edges.putIfAbsent(part[0], s1);

                var s2 = edges.getOrDefault(part[i], new HashMap<>());
                s2.put(part[0], 1);
                edges.putIfAbsent(part[i], s2);
            }
        }

        var size = nodes.size();

        Edge bestEdge = null;
        while (nodes.size() > 1) {
            var edge = nextEdge(nodes, edges);

            if (bestEdge == null || edge.w < bestEdge.w) {
                bestEdge = edge;
            }

            var se = edges.remove(edge.s);
            var te = edges.remove(edge.t);
            nodes.remove(edge.s);
            nodes.remove(edge.t);
            var n = edge.s + "|" + edge.t;
            nodes.add(n);

            edges.values().forEach(set -> {
                final var remove = set.remove(edge.s);
                final var contains = set.remove(edge.t);
                if (remove != null || contains != null) {
                    var a = remove == null ? 0 : remove;
                    var b = contains == null ? 0 : contains;
                    set.put(n, a + b);
                }
            });

            var ns = new HashMap<String, Integer>();

            se.forEach((k, v) -> {
                if (!k.equals(edge.s) && !k.equals(edge.t)) {
                    ns.put(k, v + ns.getOrDefault(k, 0));
                }
            });

            te.forEach((k, v) -> {
                if (!k.equals(edge.s) && !k.equals(edge.t)) {
                    ns.put(k, v + ns.getOrDefault(k, 0));
                }
            });

            edges.put(n, ns);
        }

        var partitionSize = bestEdge.t.split("\\|").length;

        return partitionSize * (size - partitionSize);
    }

}
