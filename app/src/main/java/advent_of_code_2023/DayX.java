package advent_of_code_2023;

import java.util.*;
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
            if (!history.add(Map.entry(x,y))) {
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
