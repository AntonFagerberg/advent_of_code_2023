package advent_of_code_2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DayX {

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
                    //                    System.out.print('O');
                } else {
                    //                    System.out.print(input[y].charAt(x));
                }
            }
            //            System.out.println();
        }

        return unique.size();
    }

    static int part2(String[] input, int steps) {
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
        var visited2 = new HashMap<Map.Entry<Integer, Integer>, Integer>();
        var visited3 = new HashMap<Map.Entry<Integer, Integer>, Integer>();
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

                        var xr = xd;
                        var yr = yd;

                        while (xr < 0) {
                            xr += xMax;
                        }

                        if (yr < 0) {
                            yr += yMax;
                        }

                        if (xr >= xMax) {
                            xr -= xMax;
                        }

                        if (yr >= yMax) {
                            yr -= yMax;
                        }

                        var coordinateNormalized = Map.entry(xr, yr);
                        var coordinate = Map.entry(xd, yd);
                        if (!rocks.contains(Map.entry(xr, yr))) {
                            if (xd == xr && yd == yr && !visited.containsKey(coordinateNormalized)) {
                                if (s.history.size() <= steps) {
                                    var hd = new ArrayList<>(s.history);
                                    hd.add(coordinate);
                                    var sd = new State(xd, yd, hd);

                                    visited.put(coordinateNormalized, hd.size());
                                    q.add(sd);
                                }
                            } else if ((xd != xr || yd != yr)) {
                                if (!visited3.containsKey(coordinateNormalized)) {
                                    if (s.history.size() <= steps) {
                                        var hd = new ArrayList<>(s.history);
                                        hd.add(coordinate);
                                        var sd = new State(xd, yd, hd);

                                        if (!visited2.containsKey(coordinateNormalized)) {
                                            visited2.put(coordinateNormalized, hd.size());
                                        } else if (!visited3.containsKey(coordinateNormalized) && s.history.contains(coordinateNormalized) && !s.history.contains(coordinate)) {
                                            visited3.put(coordinateNormalized, hd.size());
                                        }

                                        q.add(sd);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        var c = 0;

        for (var e : visited.entrySet()) {
            var l = e.getValue();
            if (l != null && l <= (steps + 1) && l % 2 != steps % 2) {
                c++;
            }
        }

        for (var e : visited2.entrySet()) {
            var l = e.getValue();
            if (l != null && l <= (steps + 1) && l % 2 != steps % 2) {
                c++;
            }
        }

//        for (var e : visited3.entrySet()) {
//            var l = e.getValue() - visited2.get(e.getKey());
//            System.out.println("%s,%s: %s (%s - %s)".formatted(e.getKey().getKey(), e.getKey().getValue(), l, e.getValue(), visited2.get(e.getKey())));
//        }

        //        for (var i = 0; i < steps; i++) {
        //            visitedAgain.values().stream().filter(ii -> ii < i).findAny();
        //        }

//        visited2.values().forEach(System.out::println);
//        visited3.values().forEach(System.out::println);

        System.out.println(visited.size());
        System.out.println(visited2.size());
        System.out.println(visited3.size());

        //        for (int y = 0; y < input.length; y++) {
        //            for (int x = 0; x < input[y].length(); x++) {
        //                var e = Map.entry(x, y);
        //                var l = visited.get(e);
        //                if (l != null && l <= (steps + 1) && l % 2 != 0) {
        //                    unique.add(e);
        //                    //                    System.out.print('O');
        //                } else {
        //                    //                    System.out.print(input[y].charAt(x));
        //                }
        //            }
        //            //            System.out.println();
        //        }

        System.out.println("--- " + c + " ---");
        return c;
    }

}
