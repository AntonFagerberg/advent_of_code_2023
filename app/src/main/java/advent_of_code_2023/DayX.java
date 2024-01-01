package advent_of_code_2023;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DayX {

    static int part1(String[] input) {
        return -1;
    }

    static Map<Map.Entry<Integer, Integer>, Set<Map.Entry<Integer, Integer>>> graph = new HashMap<>();
    static Map<Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>>, Integer> length = new HashMap<>();

    //    static void buildGraph(int x, int y, String[] input) {
    //        var opts = options(x, y, input);
    //        opts.remove(Map.entry(x, y));
    //        graph.put(Map.entry(x, y), new HashSet<>(opts));
    //        for (var e : opts) {
    //            if (!graph.containsKey(e)) {
    //                buildGraph(e.getKey(), e.getValue(), input);
    //            }
    //        }
    //    }

    static Set<Map.Entry<Integer, Integer>> options(int x, int y, String[] input) {
        var result = new HashSet<Map.Entry<Integer, Integer>>();

        for (int xx = -1; xx <= 1; xx++) {
            for (int yy = -1; yy <= 1; yy++) {
                if ((xx == 0 || yy == 0)) {
                    var key = Map.entry(x + xx, y + yy);
                    try {
                        var c = input[y + yy].charAt(x + xx);
                        if (Set.of('.', '>', '<', 'v').contains(c)) {
                            result.add(key);
                        }
                    } catch (IndexOutOfBoundsException e) {

                    }
                }
            }
        }

        return result;
    }

    static long result = 0;
    static long versions = 0;

    static void dfs(Map.Entry<Integer, Integer> pos, int maxY, int acc, Set<Map.Entry<Integer, Integer>> history) {
        if (history.contains(pos)) {
            return;
        }

        if (pos.getValue() == maxY) {
            result = Math.max(acc, result);
            versions++;
            System.out.println("%s: %s (%s)".formatted(versions, acc, result));
            return;
        }

        var h = new HashSet<>(history);
        h.add(pos);

        for (var e : graph.get(pos)) {
            dfs(e, maxY, acc + 1, h);
        }
    }

    static class State implements Comparable<State> {

        Map.Entry<Integer, Integer> pos;
        Set<Map.Entry<Integer, Integer>> history = new HashSet<>();

        int length = 0;

        public State(Map.Entry<Integer, Integer> pos) {
            this.pos = pos;
        }

        @Override
        public int compareTo(final State o) {
            return -Integer.compare(history.size(), o.history.size());
        }
    }

    static long part2(String[] input) {
        final var entry = Map.entry(1, 0);
        var poz = new LinkedList<Map.Entry<Integer, Integer>>();
        poz.addFirst(entry);
        while (!poz.isEmpty()) {
            var p = poz.removeFirst();
            final var options = options(p.getKey(), p.getValue(), input);
            options.remove(p);
            graph.put(p, options);
            for (var e : options) {
                if (!graph.containsKey(e)) {
                    poz.addFirst(e);
                }
            }
        }

        System.out.println("Before: %s".formatted(graph.size()));

        var minimized = false;
        while (!minimized) {
            minimized = true;
            var opt  = graph.entrySet().stream().filter(e -> e.getValue().size() == 2).findFirst();
            if (opt.isPresent()) {
                minimized = false;
                final var c = opt.get().getKey();
                graph.remove(c);
                var i = opt.get().getValue().iterator();
                var a = i.next();
                var b = i.next();
                graph.get(a).remove(c);
                graph.get(a).add(b);
                graph.get(b).remove(c);
                graph.get(b).add(a);
                var lll = length.getOrDefault(Map.entry(a, c), 1) + length.getOrDefault(Map.entry(c, b), 1);
                length.put(Map.entry(a, b), lll);
                length.put(Map.entry(b, a), lll);
            }
        }

        System.out.println("After: %s".formatted(graph.size()));
        graph.entrySet().forEach(System.out::println);

        var s = new State(entry);

        var l = new PriorityQueue<State>();
        l.add(s);

        while (!l.isEmpty()) {
            s = l.poll();

            if (s.pos.getValue() == input.length - 1) {
                if (versions % 10_000 == 0) {
                    System.out.printf("%s: %s (%s)%n", versions, s.history.size(), result);
                }
                    result = Math.max(s.length, result);
                    versions++;
            }

            var h = new HashSet<>(s.history);
            h.add(s.pos);

            for (var e : graph.get(s.pos)) {
                if (!s.history.contains(e)) {
                    var ss = new State(e);
                    ss.history = h;
                    ss.length = s.length + length.getOrDefault(Map.entry(s.pos, ss.pos), 1);
                    l.add(ss);
                }
            }
        }
        //        h.add(entry);
        //        dfs(entry, input.length - 1, 0, new HashSet<>());
        return result;
    }

}
