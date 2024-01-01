package advent_of_code_2023;


import java.util.*;

public class Day23 {

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

        var result = 0;

        var xys = List.of(Map.entry(0, 1), Map.entry(0, -1), Map.entry(1, 0), Map.entry(-1, 0));
        var history = new HashMap<Map.Entry<Integer, Integer>, Integer>();

        while (!q.isEmpty() || result == 0) {
            var s = q.poll();

            if (s.y == input.length - 1) {
                result = s.length;
            } else {
                var entry = Map.entry(s.x, s.y);
                var eHist = history.get(entry);

                if (eHist == null || eHist <= s.length) {
                    history.put(entry, s.length);

                    for (var xy : xys) {
                        var xx = s.x + xy.getKey();
                        var yy = s.y + xy.getValue();

                        try {
                            var c = input[yy].charAt(xx);
                            var mutate = s.mutate(xx, yy);

                            if (c != '#') {
                                if (c == '.') {
                                    q.add(mutate);
                                } else if (c == '>' && xy.getKey() == 1 && xy.getValue() == 0) {
                                    q.add(mutate);
                                } else if (c == 'v' && xy.getKey() == 0 && xy.getValue() == 1) {
                                    q.add(mutate);
                                }
                            }
                        } catch (IndexOutOfBoundsException e) {

                        }
                    }
                }
            }
        }

        return result - 1;
    }

    static Map<Map.Entry<Integer, Integer>, Set<Map.Entry<Integer, Integer>>> graph = new HashMap<>();
    static Map<Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>>, Integer> length = new HashMap<>();

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

    static class State2 implements Comparable<State2> {

        Map.Entry<Integer, Integer> pos;
        Set<Map.Entry<Integer, Integer>> history = new HashSet<>();

        int length = 0;

        public State2(Map.Entry<Integer, Integer> pos) {
            this.pos = pos;
        }

        @Override
        public int compareTo(final State2 o) {
            return -Integer.compare(history.size(), o.history.size());
        }
    }

    static int part2(String[] input) {
        final var entry = Map.entry(1, 0);
        var position = new LinkedList<Map.Entry<Integer, Integer>>();
        position.addFirst(entry);
        while (!position.isEmpty()) {
            var currentPosition = position.removeFirst();
            final var options = options(currentPosition.getKey(), currentPosition.getValue(), input);
            options.remove(currentPosition);
            graph.put(currentPosition, options);
            for (var e : options) {
                if (!graph.containsKey(e)) {
                    position.addFirst(e);
                }
            }
        }


        var minimized = false;
        while (!minimized) {
            minimized = true;
            var opt = graph.entrySet().stream().filter(e -> e.getValue().size() == 2).findFirst();
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

        var state = new State2(entry);

        var q = new PriorityQueue<State2>();
        q.add(state);

        int result = 0;

        while (!q.isEmpty()) {
            state = q.poll();

            if (state.pos.getValue() == input.length - 1) {
                result = Math.max(state.length, result);
            }

            var h = new HashSet<>(state.history);
            h.add(state.pos);

            for (var e : graph.get(state.pos)) {
                if (!state.history.contains(e)) {
                    var newState = new State2(e);
                    newState.history = h;
                    newState.length = state.length + length.getOrDefault(Map.entry(state.pos, newState.pos), 1);
                    q.add(newState);
                }
            }
        }

        return result;
    }

}
