package advent_of_code_2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Day21 {

    static class State implements Comparable<State> {

        int x;
        int y;
        List<Map.Entry<Integer, Integer>> history;

        State(int x, int y, List<Map.Entry<Integer, Integer>> history) {
            this.x = x;
            this.y = y;
            this.history = history;
        }

        @Override
        public int compareTo(State o) {
            return Integer.compare(history.size(), o.history.size());
        }
    }

    static int part1(String[] input, int steps) {
        var sx = -1;
        var sy = -1;

        Set<Map.Entry<Integer, Integer>> rocks = new HashSet<>();

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                var c = input[y].charAt(x);
                if (c == '#') {
                    rocks.add(Map.entry(x, y));
                } else if (c == 'S') {
                    sx = x;
                    sy = y;
                }
            }
        }

        var yMax = input.length;
        var xMax = input[0].length();

        var visited = new HashMap<Map.Entry<Integer, Integer>, Integer>();
        var q = new PriorityQueue<State>();
        visited.put(Map.entry(sx, sy), 1);
        q.add(new State(sx, sy, List.of(Map.entry(sx, sy))));

        while (!q.isEmpty()) {
            var s = q.poll();

            for (int xn = -1; xn <= 1; xn++) {
                for (int yn = -1; yn <= 1; yn++) {
                    if (xn == 0 || yn == 0) {
                        var xd = s.x + xn;
                        var yd = s.y + yn;

                        var coordinate = Map.entry(xd, yd);
                        if (xd >= 0 && yd >= 0 && xd < xMax && yd < yMax && !visited.containsKey(coordinate) && !rocks.contains(
                                coordinate)) {
                            var hd = new ArrayList<>(s.history);
                            hd.add(coordinate);
                            visited.put(coordinate, hd.size());

                            var sd = new State(xd, yd, hd);
                            q.add(sd);
                        }
                    }
                }
            }
        }

        var unique = new HashSet<Map.Entry<Integer, Integer>>();

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                var e = Map.entry(x, y);
                var l = visited.get(e);
                if (l != null && l <= (steps + 1) && l % 2 != 0) {
                    unique.add(e);
                }
            }
        }

        return unique.size();
    }

}
