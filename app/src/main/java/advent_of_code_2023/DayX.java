package advent_of_code_2023;

import advent_of_code_2023.dijk.Graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayX {

    static class State implements Comparable<State> {
        int x;
        int y;
        int px;
        int py;
        int length;

        Set<Map.Entry<Integer, Integer>> history = new HashSet<>();

        State(int x, int y, int length) {
            new State(x, y, length, Set.of(Map.entry(x, y)));
        }

        State(int x, int y, int length, Set<Map.Entry<Integer, Integer>> history) {
            this.x = x;
            this.y = y;
            this.history = history;
            this.length = length;
        }

        State mutate(int x, int y) {
            var history = HashSet.<Map.Entry<Integer, Integer>>newHashSet(this.history.size() + 1);
            history.addAll(this.history);
            if (!history.add(Map.entry(x, y))) {
                throw new IndexOutOfBoundsException();
            }
            var s = new State(x, y, length + 1, history);
            s.px = this.x;
            s.py = this.y;
            return s;
        }

        @Override
        public int compareTo(State o) {
            return Integer.compare(o.length, length);
        }
    }

    static int part1(String[] input) {
        var q = new PriorityQueue<State>();
        q.add(new State(1, 0, 0));

        var r = 0;

        var xys = List.of(Map.entry(0, 1), Map.entry(0, -1), Map.entry(1, 0), Map.entry(-1, 0));
        var history = new HashMap<Map.Entry<Integer, Integer>, Integer>();

        while (!q.isEmpty() || r == 0) {
            var s = q.poll();
//            System.out.println("%s,%s -> %s".formatted(s.x, s.y, q.size()));


            if (s.y == input.length - 1) {
                r = s.length;
            }

            Map.Entry<Integer, Integer> entry = Map.entry(s.x, s.y);
            var l = history.get(entry);
            if (l != null && l > s.length) {

            } else {
                history.put(entry, s.length);

                for (var xy : xys) {
                    var xx = s.x + xy.getKey();
                    var yy = s.y + xy.getValue();


                    try {
                        char c = input[yy].charAt(xx);
                        State mutate = s.mutate(xx, yy);
                        if (c == '#') {

                        } else if (c == '.') {
                            q.add(mutate);
                        } else if (c == '>' && xy.getKey() == 1 && xy.getValue() == 0) {
                            q.add(mutate);
                        } else if (c == 'v' && xy.getKey() == 0 && xy.getValue() == 1) {
                            q.add(mutate);
                        } else {
                            //throw new IllegalStateException();
                        }
                    } catch (IndexOutOfBoundsException e) {

                    }
                }
            }
        }

        return r - 1;
    }

    static Map<Map.Entry<Integer, Integer>, Node> nodeMap = new HashMap<>();

    static Set<Map.Entry<Integer, Integer>> visited = new HashSet<>();
    static Set<Map.Entry<Integer, Integer>> visitedTemp = new HashSet<>();

    static void buildMap(int x, int y, String[] input) {
        if (visited.contains(Map.entry(x, y))) {
            return;
        }

        var here = nodeMap.getOrDefault(Map.entry(x, y), new Node(x, y));
        nodeMap.putIfAbsent(Map.entry(x, y), here);

        var o = options(x, y, input);
        int i = 1;
        Map.Entry<Integer, Integer> f = null;
        while (o.size() == 1) {
            i++;
            f = o.iterator().next();
            o = options(f.getKey(), f.getValue(), input);
        }

        if (o.isEmpty()) {
            var other = nodeMap.getOrDefault(f, new Node(f.getKey(), f.getValue()));
            nodeMap.putIfAbsent(f, other);
            here.connect(other, i);
        } else {
            for (var ok : o) {
                var other = nodeMap.getOrDefault(ok, new Node(ok.getKey(), ok.getValue()));
                nodeMap.putIfAbsent(ok, other);
                here.connect(other, i);
            }

            for (var ok : o) {
                buildMap(ok.getKey(), ok.getValue(), input);
            }
        }
    }

    static Set<Map.Entry<Integer, Integer>> options(int x, int y, String[] input) {
        if (visited.contains(Map.entry(x, y))) {
            return Set.of();
        }

        visited.add(Map.entry(x, y));

        try {
            var c = input[y].charAt(x);
            if (c == '#') {
                return Set.of();
            }


            var result = HashSet.<Map.Entry<Integer, Integer>>newHashSet(4);

            var xys = List.of(Map.entry(0, 1), Map.entry(0, -1), Map.entry(1, 0), Map.entry(-1, 0));
            for (var xy : xys) {
                var xx = x + xy.getKey();
                var yy = y + xy.getValue();
                try {
                    var cc = input[yy].charAt(xx);
                    if (cc != '#' && !visitedTemp.contains(Map.entry(xx,yy))) {
                        visitedTemp.add(Map.entry(xx, yy));
                        result.add(Map.entry(xx, yy));
                    }
                } catch (IndexOutOfBoundsException ee) {

                }
            }

            return result;
        } catch (IndexOutOfBoundsException e) {
            return Set.of();
        }
    }

//    static Map.Entry<Integer, Integer> find(int x, int y, Set<Map.Entry<Integer, Integer>> history, String[] input) {
//        int nx = -1;
//        int ny = -1;
//
//        var xys = List.of(Map.entry(0, 1), Map.entry(0, -1), Map.entry(1, 0), Map.entry(-1, 0));
//        for (var xy : xys) {
//            if !(history.contains(xy)) {
//                var xx = x + xy.getKey();
//                var yy = y + xy.getValue();
//            }
//        }
//    }

    static class Position implements Comparable<Position> {
        Node node;
        Set<Node> history;
        int length;

        Position(Node node) {
            this.node = node;
            history = new HashSet<>();
            history.add(node);
        }

        Position next(Node node) {
            var p = new Position(node);
            p.history.addAll(history);
            Integer i = node.connections.get(this.node);
            if (i == null) {
                System.out.println("!!!");
            }
            p.length = length + i;
            return p;
        }

        @Override
        public int compareTo(Position o) {
            return Integer.compare(o.length, length);
        }
    }

    static ItemLimitedCache<Set<Node>, Boolean> hist = new ItemLimitedCache<>(1_000);

    static int part3(String[] input) {
//        buildMap(1, 0, input);

        var check = new LinkedList<Map.Entry<Integer, Integer>>();
        check.addFirst(Map.entry(1, 0));

        while (!check.isEmpty()) {
            var c = check.removeFirst();
            var x = c.getKey();
            var y = c.getValue();

            if (!visited.contains(Map.entry(x, y))) {
                var here = nodeMap.getOrDefault(Map.entry(x, y), new Node(x, y));
                nodeMap.putIfAbsent(Map.entry(x, y), here);

                var o = options(x, y, input);
                int i = 1;
                Map.Entry<Integer, Integer> f = null;
                while (o.size() == 1) {
                    i++;
                    f = o.iterator().next();
                    o = options(f.getKey(), f.getValue(), input);
                }

                if (o.isEmpty()) {
                    var other = nodeMap.getOrDefault(f, new Node(f.getKey(), f.getValue()));
                    nodeMap.putIfAbsent(f, other);
                    here.connect(other, i);
                } else {
                    for (var ok : o) {
                        var other = nodeMap.getOrDefault(ok, new Node(ok.getKey(), ok.getValue()));
                        nodeMap.putIfAbsent(ok, other);
                        here.connect(other, i);
                    }

                    for (var ok : o) {
                        check.addFirst(Map.entry(ok.getKey(), ok.getValue()));
//                        buildMap(ok.getKey(), ok.getValue(), input);
                    }
                }
            }
        }

        var q = new PriorityQueue<Position>();
        q.add(new Position(nodeMap.get(Map.entry(1, 0))));
        var r = 0;

        while (!q.isEmpty()) {
            var p = q.poll();

            if (hist.get(p.history) == null) {
                hist.put(p.history, true);

                if (p.node.y == input.length - 1) {
                    r = Math.max(r, p.length);
                    System.out.println(r);
                }

                for (var c : p.node.connections.entrySet()) {
                    if (!p.history.contains(c.getKey())) {
                        q.add(p.next(c.getKey()));
                    }
                }
            }
        }

        return r - 1;
    }
    static int part4(String[] input) {
        var check = new LinkedList<Map.Entry<Integer, Integer>>();
        check.addFirst(Map.entry(1, 0));

        while (!check.isEmpty()) {
            var c = check.removeFirst();
            var x = c.getKey();
            var y = c.getValue();

            if (!visited.contains(Map.entry(x, y))) {
                var here = nodeMap.getOrDefault(Map.entry(x, y), new Node(x, y));
                nodeMap.putIfAbsent(Map.entry(x, y), here);

                var o = options(x, y, input);
                int i = 1;
                Map.Entry<Integer, Integer> f = null;
                while (o.size() == 1) {
                    i++;
                    f = o.iterator().next();
                    o = options(f.getKey(), f.getValue(), input);
                }

                if (o.isEmpty()) {
                    var other = nodeMap.getOrDefault(f, new Node(f.getKey(), f.getValue()));
                    nodeMap.putIfAbsent(f, other);
                    here.connect(other, i);
                } else {
                    for (var ok : o) {
                        var other = nodeMap.getOrDefault(ok, new Node(ok.getKey(), ok.getValue()));
                        nodeMap.putIfAbsent(ok, other);
                        here.connect(other, i);
                    }

                    for (var ok : o) {
                        check.addFirst(Map.entry(ok.getKey(), ok.getValue()));
//                        buildMap(ok.getKey(), ok.getValue(), input);
                    }
                }
            }
        }

        int V = nodeMap.size(); // Number of vertices in graph
        int E = nodeMap.values().stream().map(n -> n.connections.size()).reduce(0, Integer::sum); // Number of edges in graph

        var indexedNodes = nodeMap.values().stream().toList();

        System.out.println("!!!");
        var indexedEdges = nodeMap.values().stream().flatMap(n -> {
            var l = new ArrayList<String>();
            var k1 = "%s,%s".formatted(n.x, n.y);
            for (var e : n.connections.entrySet()) {
            var k2 = "%s,%s".formatted(e.getKey().x, e.getKey().y);
                l.add("%s|%s|%s".formatted(k1, k2, e.getValue()));
            }
            return l.stream();
        }).toList();

        int zrc = -1;

        for (int ii = 0; ii < indexedNodes.size(); ii++) {
            var n = indexedNodes.get(ii);
            if (n.x == 139 && n.y == 140) {
                System.out.println("==> " + ii);
            }
            if (n.x == 1 && n.y == 0) {
                System.out.println("--> " + ii);
                zrc = ii;
            }
        }

        advent_of_code_2023.bf.Graph graph = new advent_of_code_2023.bf.Graph(V, E);

        System.out.println("!!?");
        for (var i = 0; i < E; i++) {
            var parts = indexedEdges.get(i).split("\\|");

            int src = -1;
            for (int ii = 0; ii < indexedNodes.size(); ii++) {
                var n = indexedNodes.get(ii);
                if (parts[0].equals("%s,%s".formatted(n.x, n.y))) {
                    src = ii;
                    break;
                }
            }
            int dest = -1;
            for (int ii = 0; ii < indexedNodes.size(); ii++) {
                var n = indexedNodes.get(ii);
                if (parts[1].equals("%s,%s".formatted(n.x, n.y))) {
                    dest = ii;
                    break;
                }
            }

            Integer i3 = Integer.valueOf(parts[2]);

            // 37516 - 35827 = 1689

//            System.out.println("%s | %s | %s".formatted(src, dest, i3));

            if (src == -1 || dest == -1 || i3 == -1) {
                throw new IllegalStateException();
            }

            graph.edge[i].src = src;
            graph.edge[i].dest = dest;
            graph.edge[i].weight = -i3;
        }

        System.out.println("!??");
        graph.BellmanFord(graph, zrc);
        System.out.println("???");



        return -1;

        // add edge 0-1 (or A-B in above figure)
//        graph.edge[0].src = 0;
//        graph.edge[0].dest = 1;
//        graph.edge[0].weight = -1;
//
//        // add edge 0-2 (or A-C in above figure)
//        graph.edge[1].src = 0;
//        graph.edge[1].dest = 2;
//        graph.edge[1].weight = 4;
//
//        // add edge 1-2 (or B-C in above figure)
//        graph.edge[2].src = 1;
//        graph.edge[2].dest = 2;
//        graph.edge[2].weight = 3;
//
//        // add edge 1-3 (or B-D in above figure)
//        graph.edge[3].src = 1;
//        graph.edge[3].dest = 3;
//        graph.edge[3].weight = 2;
//
//        // add edge 1-4 (or B-E in above figure)
//        graph.edge[4].src = 1;
//        graph.edge[4].dest = 4;
//        graph.edge[4].weight = 2;
//
//        // add edge 3-2 (or D-C in above figure)
//        graph.edge[5].src = 3;
//        graph.edge[5].dest = 2;
//        graph.edge[5].weight = 5;
//
//        // add edge 3-1 (or D-B in above figure)
//        graph.edge[6].src = 3;
//        graph.edge[6].dest = 1;
//        graph.edge[6].weight = 1;
//
//        // add edge 4-3 (or E-D in above figure)
//        graph.edge[7].src = 4;
//        graph.edge[7].dest = 3;
//        graph.edge[7].weight = -3;

        // Function call



//        var check = new LinkedList<Map.Entry<Integer, Integer>>();
//        check.addFirst(Map.entry(1, 0));
//
//        while (!check.isEmpty()) {
//            var c = check.removeFirst();
//            var x = c.getKey();
//            var y = c.getValue();
//
//            if (!visited.contains(Map.entry(x, y))) {
//                var here = nodeMap.getOrDefault(Map.entry(x, y), new Node(x, y));
//                nodeMap.putIfAbsent(Map.entry(x, y), here);
//
//                var o = options(x, y, input);
//                int i = 1;
//                Map.Entry<Integer, Integer> f = null;
//                while (o.size() == 1) {
//                    i++;
//                    f = o.iterator().next();
//                    o = options(f.getKey(), f.getValue(), input);
//                }
//
//                if (o.isEmpty()) {
//                    var other = nodeMap.getOrDefault(f, new Node(f.getKey(), f.getValue()));
//                    nodeMap.putIfAbsent(f, other);
//                    here.connect(other, i);
//                } else {
//                    for (var ok : o) {
//                        var other = nodeMap.getOrDefault(ok, new Node(ok.getKey(), ok.getValue()));
//                        nodeMap.putIfAbsent(ok, other);
//                        here.connect(other, i);
//                    }
//
//                    for (var ok : o) {
//                        check.addFirst(Map.entry(ok.getKey(), ok.getValue()));
////                        buildMap(ok.getKey(), ok.getValue(), input);
//                    }
//                }
//            }
//        }

//       var nodez = nodeMap.values().stream()
//               .map(n -> new advent_of_code_2023.dijk.Node("%s,%s".formatted(n.x, n.y)))
//               .collect(Collectors.toMap(n -> n.getName(), n -> n));
//
//        for (var n : nodeMap.values()) {
//            var name = "%s,%s".formatted(n.x, n.y);
//            for (var e : n.connections.entrySet()) {
//                nodez.get(name).addDestination(nodez.get("%s,%s".formatted(e.getKey().x, e.getKey().y)), -e.getValue());
//            }
//        }
//
//        var g = new Graph();
//        nodez.values().forEach(g::addNode);
//
//        System.out.println("!!!");
//
//        Graph.calculateShortestPathFromSource(g, nodez.get("1,0"));
//
//        nodez.values().forEach(n -> System.out.println(n.getDistance()));
//
//        System.out.println(" => " + nodez.get("139,131").getDistance());
//
//        return -1;
    }

    static int part2_old(String[] input) throws InterruptedException {
//        buildMap(1, 0, input);

        var check = new LinkedList<Map.Entry<Integer, Integer>>();
        check.addFirst(Map.entry(1, 0));

        while (!check.isEmpty()) {
            var c = check.removeFirst();
            var x = c.getKey();
            var y = c.getValue();

            if (!visited.contains(Map.entry(x, y))) {


                var here = nodeMap.getOrDefault(Map.entry(x, y), new Node(x, y));
                nodeMap.putIfAbsent(Map.entry(x, y), here);

                var o = options(x, y, input);
                int i = 1;
                Map.Entry<Integer, Integer> f = null;
                while (o.size() == 1) {
                    i++;
                    f = o.iterator().next();
                    o = options(f.getKey(), f.getValue(), input);
                }

                if (o.isEmpty()) {
                    var other = nodeMap.getOrDefault(f, new Node(f.getKey(), f.getValue()));
                    nodeMap.putIfAbsent(f, other);
                    here.connect(other, i);
                } else {
                    for (var ok : o) {
                        var other = nodeMap.getOrDefault(ok, new Node(ok.getKey(), ok.getValue()));
                        nodeMap.putIfAbsent(ok, other);
                        here.connect(other, i);
                    }

                    for (var ok : o) {
                        check.addFirst(Map.entry(ok.getKey(), ok.getValue()));
//                        buildMap(ok.getKey(), ok.getValue(), input);
                    }
                }
            }
        }

        var q = new PriorityQueue<Position>();
        q.add(new Position(nodeMap.get(Map.entry(1, 0))));
        var r = 0;

        while (!q.isEmpty()) {
            var p = q.poll();

            if (p.node.y == input.length - 1) {
                r = Math.max(r, p.length);
                System.out.println(r);
            }

            for (var c : p.node.connections.entrySet()) {
                if (!p.history.contains(c.getKey())) {
                    q.add(p.next(c.getKey()));
                }
            }
        }

        return r - 1;
//
////        var q = new Stack<Position>();
////
////        q.addFirst(new Position(nodeMap.get(Map.entry(1, 0))));
////
////        AtomicInteger r = new AtomicInteger(0);
////
////        var es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
////
////        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
////            es.execute(() -> {
////                while (!q.isEmpty()) {
////                    var p = q.removeFirst();
////
////                    if (p.node.y == input.length - 1) {
////                        var old = r.get();
////                        var rr = Math.max(old, p.length);
////
////                        while (!r.compareAndSet(old, rr)) {
////                            old = r.get();
////                            rr = Math.max(old, p.length);
////                        }
////
////                        System.out.println(rr);
////                    }
////
////                    for (var c : p.node.connections.entrySet()) {
////                        if (!p.history.contains(c.getKey())) {
////                            q.add(p.next(c.getKey()));
////                        }
////                    }
////                }
////            });
////        }
////        es.shutdown();
////        es.awaitTermination(10, TimeUnit.DAYS);
//
//
////        return r.get() - 1;
//
////        for (int y = 0; y < input.length; y++) {
////            for (int x = 0; x < input[y].length(); x++) {
////                var c = input[y].charAt(x);
////
////                if (c != '#') {
////
////                }
////            }
////        }
//
//
////        return -1;
////        var q = new PriorityQueue<State>();
////        q.add(new State(1, 0, 0));
////        Map<String, Integer> history2 = new HashMap<>();
////
////        var r = 0;
////
////        var xys = List.of(Map.entry(0, 1), Map.entry(0, -1), Map.entry(1, 0), Map.entry(-1, 0));
////
////        while (!q.isEmpty()) {
////            var s = q.poll();
//////            System.out.println("%s,%s -> %s".formatted(s.x, s.y, q.size()));
////
////
////            if (s.y == input.length - 1) {
////                r = Math.max(r, s.length);
////                System.out.println(r);
////            }
////
////            String entry = "%s,%s,%s,%s".formatted(s.x, s.y, s.px, s.py);
////            var l = history2.get(entry);
////            if (l != null && l > s.length) {
////
////            } else {
//////                history2.put(entry, s.length);
////
////                for (var xy : xys) {
////                    var xx = s.x + xy.getKey();
////                    var yy = s.y + xy.getValue();
////
////
////                    try {
////                        char c = input[yy].charAt(xx);
////                        State mutate = s.mutate(xx, yy);
////                        if (c != '#') {
////                            q.add(mutate);
////                        }
////                    } catch (IndexOutOfBoundsException e) {
////
////                    }
////                }
////            }
////        }
////
////        return r - 1;
    }static int part3_old(String[] input) throws InterruptedException {
//        buildMap(1, 0, input);

        var check = new LinkedList<Map.Entry<Integer, Integer>>();
        check.addFirst(Map.entry(1, 0));

        while (!check.isEmpty()) {
            var c = check.removeFirst();
            var x = c.getKey();
            var y = c.getValue();

            if (!visited.contains(Map.entry(x, y))) {

                visitedTemp.clear();
                var here = nodeMap.getOrDefault(Map.entry(x, y), new Node(x, y));
                nodeMap.putIfAbsent(Map.entry(x, y), here);

                var o = options(x, y, input);
                int i = 1;
                visitedTemp.add(Map.entry(x,y));
                Map.Entry<Integer, Integer> f = null;
                while (o.size() == 1) {
                    i++;
                    f = o.iterator().next();
                    o = options(f.getKey(), f.getValue(), input);
                }

                if (o.isEmpty()) {
                    var other = nodeMap.getOrDefault(f, new Node(f.getKey(), f.getValue()));
                    nodeMap.putIfAbsent(f, other);
                    here.connect(other, i);
                } else {
                    for (var ok : o) {
                        var other = nodeMap.getOrDefault(ok, new Node(ok.getKey(), ok.getValue()));
                        nodeMap.putIfAbsent(ok, other);
                        here.connect(other, i);
                    }

                    for (var ok : o) {
                        check.addFirst(Map.entry(ok.getKey(), ok.getValue()));
//                        buildMap(ok.getKey(), ok.getValue(), input);
                    }
                }
            }
        }

        var q = new PriorityQueue<Position>();
        q.add(new Position(nodeMap.get(Map.entry(1, 0))));
        var r = 0;

        while (!q.isEmpty()) {
            var p = q.poll();

            if (p.node.y == input.length - 1) {
                r = Math.max(r, p.length);
                System.out.println(r);
            }

            for (var c : p.node.connections.entrySet()) {
                if (!p.history.contains(c.getKey())) {
                    q.add(p.next(c.getKey()));
                }
            }
        }

        return r - 3; // suspekt
//
////        var q = new Stack<Position>();
////
////        q.addFirst(new Position(nodeMap.get(Map.entry(1, 0))));
////
////        AtomicInteger r = new AtomicInteger(0);
////
////        var es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
////
////        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
////            es.execute(() -> {
////                while (!q.isEmpty()) {
////                    var p = q.removeFirst();
////
////                    if (p.node.y == input.length - 1) {
////                        var old = r.get();
////                        var rr = Math.max(old, p.length);
////
////                        while (!r.compareAndSet(old, rr)) {
////                            old = r.get();
////                            rr = Math.max(old, p.length);
////                        }
////
////                        System.out.println(rr);
////                    }
////
////                    for (var c : p.node.connections.entrySet()) {
////                        if (!p.history.contains(c.getKey())) {
////                            q.add(p.next(c.getKey()));
////                        }
////                    }
////                }
////            });
////        }
////        es.shutdown();
////        es.awaitTermination(10, TimeUnit.DAYS);
//
//
////        return r.get() - 1;
//
////        for (int y = 0; y < input.length; y++) {
////            for (int x = 0; x < input[y].length(); x++) {
////                var c = input[y].charAt(x);
////
////                if (c != '#') {
////
////                }
////            }
////        }
//
//
////        return -1;
////        var q = new PriorityQueue<State>();
////        q.add(new State(1, 0, 0));
////        Map<String, Integer> history2 = new HashMap<>();
////
////        var r = 0;
////
////        var xys = List.of(Map.entry(0, 1), Map.entry(0, -1), Map.entry(1, 0), Map.entry(-1, 0));
////
////        while (!q.isEmpty()) {
////            var s = q.poll();
//////            System.out.println("%s,%s -> %s".formatted(s.x, s.y, q.size()));
////
////
////            if (s.y == input.length - 1) {
////                r = Math.max(r, s.length);
////                System.out.println(r);
////            }
////
////            String entry = "%s,%s,%s,%s".formatted(s.x, s.y, s.px, s.py);
////            var l = history2.get(entry);
////            if (l != null && l > s.length) {
////
////            } else {
//////                history2.put(entry, s.length);
////
////                for (var xy : xys) {
////                    var xx = s.x + xy.getKey();
////                    var yy = s.y + xy.getValue();
////
////
////                    try {
////                        char c = input[yy].charAt(xx);
////                        State mutate = s.mutate(xx, yy);
////                        if (c != '#') {
////                            q.add(mutate);
////                        }
////                    } catch (IndexOutOfBoundsException e) {
////
////                    }
////                }
////            }
////        }
////
////        return r - 1;
    }

    static class Node {
        int x;
        int y;
        Map<Node, Integer> connections;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
            connections = new HashMap<>();
        }

        void connect(Node other, int length) {
            if (!this.equals(other)) {
                connections.put(other, length);
                other.connections.put(this, length);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static int part2(String[] input) {
        var q = new PriorityQueue<State>();
        q.add(new State(1, 0, 0));
        Map<String, Integer> history2 = new HashMap<>();

        var r = 0;

        var xys = List.of(Map.entry(0, 1), Map.entry(0, -1), Map.entry(1, 0), Map.entry(-1, 0));

        while (!q.isEmpty()) {
            var s = q.poll();
//            System.out.println("%s,%s -> %s".formatted(s.x, s.y, q.size()));


            if (s.y == input.length - 1) {
                r = Math.max(r, s.length);
                System.out.println(r);
            }

            String entry = "%s,%s,%s,%s".formatted(s.x, s.y, s.px, s.py);
            var l = history2.get(entry);
            if (l != null && l > s.length) {

            } else {
//                history2.put(entry, s.length);

                for (var xy : xys) {
                    var xx = s.x + xy.getKey();
                    var yy = s.y + xy.getValue();


                    try {
                        char c = input[yy].charAt(xx);
                        State mutate = s.mutate(xx, yy);
                        if (c != '#') {
                            q.add(mutate);
                        }
                    } catch (IndexOutOfBoundsException e) {

                    }
                }
            }
        }

        return r - 1;
    }

}
