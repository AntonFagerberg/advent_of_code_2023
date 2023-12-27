package advent_of_code_2023;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.SimpleGraph;

public class DayX {

    static Set<Integer> checkGroups(Set<Map.Entry<String, String>> wires) {
        if (wires.isEmpty()) {
            return new HashSet<>();
        }

        var wire = wires.iterator().next();
        var acc = new HashSet<String>();
        acc.add(wire.getKey());
        acc.add(wire.getValue());
        var i = 0;

        while (acc.size() != i) {
            i = acc.size();
            var wp = wires.stream()
                    .filter(e -> acc.contains(e.getKey()) || acc.contains(e.getValue()))
                    .map(e -> {
                        acc.add(e.getValue());
                        acc.add(e.getKey());
                        return e;
                    }).collect(Collectors.toSet());

            wires.removeAll(wp);
        }

        var r = checkGroups(wires);
        r.add(i);
        return r;
    }

    static class Node {

        Set<String> name;
        Set<Node> edge = new HashSet<>();

        public Node(Set<String> name) {
            this.name = name;
        }

        Node merge(Node other) {
            var newName = new HashSet<String>();
            newName.addAll(this.name);
            newName.addAll(other.name);
            var newNode = new Node(newName);

            edge.forEach(n -> {
                if (!n.equals(this) && !n.equals(other)) {
                    n.edge.add(newNode);
                }
                n.edge.remove(this);
                n.edge.remove(other);
            });

            other.edge.forEach(n -> {
                if (!n.equals(this) && !n.equals(other)) {
                    n.edge.add(newNode);
                }
                n.edge.remove(this);
                n.edge.remove(other);
            });

            newNode.edge.addAll(edge);
            newNode.edge.addAll(other.edge);
            newNode.edge.remove(this);
            newNode.edge.remove(other);

            return newNode;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            final Node node = (Node) o;
            return Objects.equals(name, node.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    static int part1(String[] input) throws InterruptedException {
        var r = new Random();

        var nodes = new HashSet<String>();
        var edges = new HashMap<String, Set<String>>();

        for (var line : input) {
            var part = line.split("( |\\:)");
            for (var i = 2; i < part.length; i++) {
                nodes.add(part[0]);
                nodes.add(part[i]);

                var s1 = edges.getOrDefault(part[0], new HashSet<>());
                s1.add(part[i]);
                edges.putIfAbsent(part[0], s1);

                var s2 = edges.getOrDefault(part[i], new HashSet<>());
                s2.add(part[0]);
                edges.putIfAbsent(part[i], s2);

            }
        }

        final var nThreads = Runtime.getRuntime().availableProcessors();
        var e = Executors.newFixedThreadPool(nThreads);

        for (var np = 0; np < nThreads; np++) {
            e.execute(() -> {
                while (true) {
                    var a = new HashSet<String>();
                    int b = 0;

                    for (String n : nodes) {
                        if (r.nextBoolean()) {
                            a.add(n);
                        } else {
                            b++;
                        }
                    }

                    long i = 0L;

                    for (var name : a) {
                        i += edges.get(name).stream().filter(Predicate.not(a::contains)).count();
                        if (i > 3) {
                            break;
                        }
                    }

                    if (i == 3) {
                        System.out.println(" => " + a.size() * b);
                        break;
                    }
                }
            });

        }
        e.awaitTermination(10, TimeUnit.DAYS);
        return -1;
    }

    static int part1_3(String[] input) throws InterruptedException {
        var r = new Random();

        var nodes = new HashSet<String>();
        var edges = new HashMap<String, Set<String>>();

        for (var line : input) {
            var part = line.split("( |\\:)");
            for (var i = 2; i < part.length; i++) {
                nodes.add(part[0]);
                nodes.add(part[i]);

                var s1 = edges.getOrDefault(part[0], new HashSet<>());
                s1.add(part[i]);
                edges.putIfAbsent(part[0], s1);

                var s2 = edges.getOrDefault(part[i], new HashSet<>());
                s2.add(part[0]);
                edges.putIfAbsent(part[i], s2);

            }
        }

        var g = new DefaultUndirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        nodes.forEach(g::addVertex);
        edges.entrySet().forEach(e -> {
            e.getValue().forEach(k -> {
//                System.out.println(e.getKey() + " = " + k);
                g.addEdge(e.getKey(), k);
            });
        });




        System.out.println(" v => " + g.vertexSet());
        System.out.println(" e => " + g.edgeSet());

        var s = new StoerWagnerMinimumCut(g);

        System.out.println(s.minCut());

        return s.minCut().size() * (nodes.size() - s.minCut().size());

//        while (true) {
//            var a = new HashSet<String>();
//            int b = 0;
//
//            for (String n : nodes) {
//                if (r.nextBoolean()) {
//                    a.add(n);
//                } else {
//                    b++;
//                }
//            }
//
//            long i = 0L;
//
//            for (var name : a) {
//                i += edges.get(name).stream().filter(Predicate.not(a::contains)).count();
//                if (i > 3) {
//                    break;
//                }
//            }
//
//            if (i == 3) {
//                System.out.println(" => " + a.size() * b);
//                break;
//            }
//        }
    }

    static Stream<Set<String>> buildSets(LinkedList<String> input) {
        if (input.isEmpty()) {
            return Stream.of(new HashSet<>());
        }

        var f = input.removeFirst();

        return buildSets(input)
                .flatMap(s -> {
                    var ss = new HashSet<>(s);
                    ss.add(f);
                    return Stream.of(s, ss);
                });
    }

    static int part1_2(String[] input) {
        var r = new Random();

        var nodes = new HashSet<String>();
        var edges = new HashMap<String, Set<String>>();

        for (var line : input) {
            var part = line.split("( |\\:)");
            for (var i = 2; i < part.length; i++) {
                nodes.add(part[0]);
                nodes.add(part[i]);

                var s1 = edges.getOrDefault(part[0], new HashSet<>());
                s1.add(part[i]);
                edges.putIfAbsent(part[0], s1);

                var s2 = edges.getOrDefault(part[i], new HashSet<>());
                s2.add(part[0]);
                edges.putIfAbsent(part[i], s2);

            }
        }

        //        System.out.println(nodes.size());
        //        System.out.println(Math.pow(2, nodes.size()));
        //        System.out.println(buildSets(new LinkedList<>(nodes)).toList().size());

        final var initialValue = Double.valueOf(Math.pow(2, nodes.size())).longValue();
        var max = new AtomicLong(0);

        return buildSets(new LinkedList<>(nodes))
                .filter(a -> {
                    var c = max.incrementAndGet();

                    if (c % 1_000_000 == 0) {
                        System.out.println("%s / %s (%s)".formatted(c, initialValue, 100 * (c / ((double) initialValue))));
                    }
                    int i = 0;
                    for (var name : a) {
                        i += edges.get(name).stream().filter(Predicate.not(a::contains)).count();
                        if (i > 3) {
                            break;
                        }
                    }

                    return i == 3;
                })
                .findFirst()
                .map(a -> a.size() * (nodes.size() - a.size()))
                .get();
    }

    //        while (true) {
    //            Map<Set<String>, Node> graph = new HashMap<>();
    //
    //            for (var line : input) {
    //                var part = line.split("( |\\:)");
    //                for (var i = 2; i < part.length; i++) {
    //                    var e1 = graph.get(Set.of(part[0]));
    //                    if (e1 == null) {
    //                        e1 = new Node(Set.of(part[0]));
    //                        graph.put(e1.name, e1);
    //                    }
    //
    //                    var e2 = graph.get(Set.of(part[i]));
    //                    if (e2 == null) {
    //                        e2 = new Node(Set.of(part[i]));
    //                        graph.put(e2.name, e2);
    //                    }
    //
    //                    e1.edge.add(e2);
    //                    e2.edge.add(e1);
    //                }
    //            }
    //
    //            var wg = new ArrayList<>(graph.values());
    //
    //            while (wg.size() > 2) {
    //                var e1 = wg.remove(r.nextInt(wg.size()));
    //                var e2 = wg.remove(r.nextInt(wg.size()));
    //                wg.add(e1.merge(e2));
    //            }
    //
    //            var iterator = wg.iterator();
    //            var first = iterator.next();
    //            var second = iterator.next();
    //
    //            if (first.edge.size() == 3) {
    //                return first.name.size() * second.name.size();
    //            }
    //        }
    //
    //    }

    //        int[][] arr = null;
    //        Map<Integer, Integer> statistics = new LinkedHashMap<Integer, Integer>();
    //        int min = arr.length;
    //        int iter = arr.length * arr.length;
    //        GlobalMinCut.Graph g = createGraph(arr);

    //        GlobalMinCut.Graph gr = new GlobalMinCut.Graph();
    //
    //        var w = wires.stream().toList();
    //        var n = nodes.stream().toList();
    //
    //        for (var ww : w) {
    //            var v = gr.getVertex(n.indexOf(ww.getKey()));
    //            var v2 = gr.getVertex(n.indexOf(ww.getValue()));
    //
    //            GlobalMinCut.Edge e;
    //            if ((e = v2.getEdgeTo(v)) == null)
    //            {
    //                e = new GlobalMinCut.Edge(v, v2);
    //                gr.edges.add(e);
    //                v.addEdge(e);
    //                v2.addEdge(e);
    //            }
    //        }

    //        for (int i = 0; i < array.length; i++)
    //        {
    //            GlobalMinCut.Vertex v = gr.getVertex(i);
    //            for (int edgeTo : array[i])
    //            {
    //                GlobalMinCut.Vertex v2 = gr.getVertex(edgeTo);
    //                GlobalMinCut.Edge e;
    //                if ((e = v2.getEdgeTo(v)) == null)
    //                {
    //                    e = new GlobalMinCut.Edge(v, v2);
    //                    gr.edges.add(e);
    //                    v.addEdge(e);
    //                    v2.addEdge(e);
    //                }
    //            }
    //        }

    //        printGraph(gr);
    //        int min = nodes.size();
    //        int iter = nodes.size() * nodes.size();
    //        for (int i = 0; i < iter; i++)
    //        {
    //            int currMin = minCut(gr);
    //            min = Math.min(min, currMin);
    //            Integer counter;
    //            if ((counter = statistics.get(currMin)) == null)
    //            {
    //                counter = 0;
    //            }
    //            statistics.put(currMin, counter + 1);
    //}
    //        System.out.println("Min: " + min + " stat: "
    //                + (statistics.get(min) * 100 / iter) + "%");

    //        var g = new GFG.Graph(nodes.size(), wires.size()*2);
    //        var w = wires.stream().toList();
    //
    //        for (int i = 0; i < wires.size(); i++) {
    //            final var i1 = nodes.indexOf(w.get(i).getValue());
    //            final var i2 = nodes.indexOf(w.get(i).getKey());
    //            System.out.println("%s -> %s (%s)".formatted(i1, i2, i));
    //            g.edge[i] = new GFG.Edge(i1, i2);
    //            g.edge[i + wires.size()] = new GFG.Edge(i2, i1);
    //        }
    //
    //        GFG.kargerMinCut(g);

    //        var m = new HashMap<String, List<String>>();
    //
    //        for (var e : wires) {
    //            var l = m.getOrDefault(e.getKey(), new ArrayList<>());
    //            l.add(e.getValue());
    //            m.put(e.getKey(), l);
    //
    //            l = m.getOrDefault(e.getValue(), new ArrayList<>());
    //            l.add(e.getKey());
    //            m.put(e.getValue(), l);
    //        }
    //
    //        m.entrySet().stream()
    //                .sorted(Comparator.comparingInt(e -> e.getValue().size()))
    //                .forEach(System.out::println);
    //
    //        int size = wires.size();
    //        var wl = wires.stream().collect(Collectors.toList());

    //        for (var i = 0; i < size - 2; i++) {
    //            System.out.println("%s / %s".formatted(i, size - 3));
    //            for (var j = i; j < size - 1; j++) {
    //                for (var k = j; k < size; k++) {
    //                    var w = new HashSet<>(wires);
    //                    w.remove(wl.get(i));
    //                    w.remove(wl.get(j));
    //                    w.remove(wl.get(k));
    //                    var groups = checkGroups(w);
    //                    if (groups.size() == 2) {
    //                        return groups.stream().reduce(1, (a,b) -> a*b);
    //                    }
    //                }
    //            }
    //        }

    //        return -1;
    //    }

    static int part2(String[] input) {
        return -1;
    }

}
