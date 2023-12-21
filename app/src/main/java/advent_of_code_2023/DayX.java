package advent_of_code_2023;

import java.util.*;

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

    static Map<Map.Entry<Integer, Integer>, Integer> buildLengths(int x, int y, int xMax, int yMax,
                                                                  Set<Map.Entry<Integer, Integer>> rocks) {
        var visited = new HashMap<Map.Entry<Integer, Integer>, Integer>();
        var q = new PriorityQueue<State>();
        visited.put(Map.entry(x, y), 0);
        q.add(new State(x, y, List.of(Map.entry(x, y))));

        while (!q.isEmpty()) {
            var s = q.poll();

            for (int xn = -1; xn <= 1; xn++) {
                for (int yn = -1; yn <= 1; yn++) {
                    if (xn == 0 || yn == 0) {
                        var xd = s.x + xn;
                        var yd = s.y + yn;

                        var coordinate = Map.entry(xd, yd);
                        if (xd >= 0 && yd >= 0 && xd < xMax && yd < yMax && !rocks.contains(coordinate)) {
                            if (!visited.containsKey(coordinate)) {
                                visited.put(coordinate, s.history.size());

                                var hd = new ArrayList<>(s.history);
                                hd.add(coordinate);
                                q.add(new State(xd, yd, hd));

                            }
                        }
                    }
                }
            }
        }

        return visited;
    }

    static class Magic implements Comparable<Magic> {

        int x;
        int y;
        int steps;
        int xO;
        int yO;

        @Override
        public int compareTo(final Magic o) {
            return Integer.compare(steps, o.steps);
        }
    }

    static long part2(String[] input, int steps) {
        result.clear();

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

        var visited = buildLengths(sx, sy, xMax, yMax, rocks);
        Map<Map.Entry<Integer, Integer>, Map<Map.Entry<Integer, Integer>, Integer>> edges = new HashMap<>();

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                if (x == 0 || y == 0 || x == xMax - 1 || y == yMax - 1) {
                    edges.put(Map.entry(x, y), buildLengths(x, y, xMax, yMax, rocks));
                }
            }
        }

        //        edges.keySet().stream().map(e -> "%s,%s".formatted(e.getKey(), e.getValue())).sorted().forEach(System.out::println);

        //        System.out.println(coords.get(Map.entry(0,0)));

        //        var q = new PriorityQueue<State>();
        //        visited.put(Map.entry(sx, sy), 1);
        //        q.add(new State(sx, sy, List.of(Map.entry(sx, sy))));
        //
        //        while (!q.isEmpty()) {
        //            var s = q.poll();
        //
        //            for (int xn = -1; xn <= 1; xn++) {
        //                for (int yn = -1; yn <= 1; yn++) {
        //                    if (xn == 0 || yn == 0) {
        //                        var xd = s.x + xn;
        //                        var yd = s.y + yn;
        //
        //                        var coordinate = Map.entry(xd, yd);
        //                        if (xd >= 0 && yd >= 0 && xd < xMax && yd < yMax && !rocks.contains(coordinate)) {
        //                            if (!visited.containsKey(coordinate)) {
        //                                var hd = new ArrayList<>(s.history);
        //                                hd.add(coordinate);
        //                                q.add(new State(xd, yd, hd));
        //
        //                                visited.put(coordinate, hd.size());
        //                            }
        //                        }
        //                    }
        //                }
        //            }
        //        }

        //        for (var rem = steps; rem > 0; rem--) {
        //
        //        }

        //        var result = 0;
        //        Map<Map.Entry<Integer, Integer>, Integer> positions = new HashMap<>();

        var q = new PriorityQueue<Magic>();

        for (var e : visited.entrySet()) {
            var length = e.getValue();
            var key = e.getKey();
            var x = key.getKey();
            var y = key.getValue();

            if (length <= steps) {
                result.put("%s,%s,%s,%s".formatted(x, y, 0, 0), length);
            }

            if (x == 0) {
                var m = new Magic();
                m.x = xMax - 1;
                m.y = y;
                m.xO = -1;
                m.yO = 0;
                m.steps = length + 1;
                q.add(m);
                //                walk(edges, xMax - 1, y, -1, 0, xMax, yMax, steps, length + 1);
            } else if (x == xMax - 1) {
                var m = new Magic();
                m.x = 0;
                m.y = y;
                m.xO = 1;
                m.yO = 0;
                m.steps = length + 1;
                q.add(m);
                //                walk(edges, 0, y, 1, 0, xMax, yMax, steps, length + 1);
            }

            if (y == 0) {
                var m = new Magic();
                m.x = x;
                m.y = yMax - 1;
                m.xO = 0;
                m.yO = -1;
                m.steps = length + 1;
                q.add(m);
                //                walk(edges, x, yMax - 1, 0, -1, xMax, yMax, steps, length + 1);
            } else if (y == yMax - 1) {
                var m = new Magic();
                m.x = x;
                m.y = 0;
                m.xO = 0;
                m.yO = 1;
                m.steps = length + 1;
                q.add(m);
                //                walk(edges, x, 0, 0, 1, xMax, yMax, steps, length + 1);
            }

            //                for (int xn = -1; xn <= 1; xn++) {
            //                    for (int yn = -1; yn <= 1; yn++) {
            //                        if ((xn == 0 || yn == 0) && (xn != yn)) {
            //                            int xd = x + xn;
            //                            int yd = y + yn;
            //
            //                            if (xd < 0) {
            //                                xd += xMax;
            //                            } else if (xd >= xMax) {
            //                                xd -= xMax;
            //                            } else if (yd < 0) {
            //                                yd += yMax;
            //                            } else if (yd >= yMax) {
            //                                yd -= yMax;
            //                            } else {
            //                                throw new IllegalStateException();
            //                            }
            //
            //                            if (xd == 0 || yd == 0 || xd == xMax - 1 || yd == yMax - 1) {
            //                                positions.put(Map.entry(xd, yd), length + 1);
            //                            }
            //                        }
            //                    }
            //                }
            //            }
        }

        var cache = new HashMap<String, Integer>();

        while (!q.isEmpty()) {
            var next = q.poll();

            final var cachedValue = cache.get("%s,%s,%s,%s".formatted(next.x, next.y, next.xO, next.yO));
            cache.put("%s,%s,%s,%s".formatted(next.x, next.y, next.xO, next.yO), cachedValue == null || cachedValue > next.steps ? next.steps : cachedValue);
            if (next.steps > steps || (cachedValue != null && cachedValue <= next.steps)) {

            } else {
                var position = edges.get(Map.entry(next.x, next.y));

                for (var e : position.entrySet()) {
                    final var xx = e.getKey().getKey();
                    final var yy = e.getKey().getValue();

                    var length = next.steps + e.getValue();

                    if (length <= steps) {
                        String key = "%s,%s,%s,%s".formatted(xx, yy, next.xO, next.yO);
                        var old = result.get(key);
                        result.put(key, old != null && old < length ? old : length);
                    }

                    if (xx == 0) {
                        var m = new Magic();
                        m.x = xMax - 1;
                        m.y = yy;
                        m.xO = next.xO - 1;
                        m.yO = next.yO;
                        m.steps = length + 1;
                        q.add(m);
                    } else if (xx == xMax - 1) {
                        var m = new Magic();
                        m.x = 0;
                        m.y = yy;
                        m.xO = next.xO + 1;
                        m.yO = next.yO;
                        m.steps = length + 1;
                        q.add(m);
                    }

                    if (yy == 0) {
                        var m = new Magic();
                        m.x = xx;
                        m.y = yMax - 1;
                        m.xO = next.xO;
                        m.yO = next.yO - 1;
                        m.steps = length + 1;
                        q.add(m);
                    } else if (yy == yMax - 1) {
                        var m = new Magic();
                        m.x = xx;
                        m.y = 0;
                        m.xO = next.xO;
                        m.yO = next.yO + 1;
                        m.steps = length + 1;
                        q.add(m);
                    }
                }
            }
        }

        //        visited.forEach((k,v ) -> System.out.println("%s,%s -> %s".formatted(k.getKey(), k.getValue(), v)));

        //        System.out.println("xMax: %s, yMax: %s".formatted(xMax, yMax));
        //
        //        positions.forEach((k, v) -> System.out.println("%s,%s -> %s".formatted(k.getKey(), k.getValue(), v)));

        //        nope.add(Map.entry(0,0));

        //        for (var p : positions.entrySet()) {
        //            walk(edges, p.getKey().getKey(), p.getKey().getValue(), 100, 100, steps, p.getValue());
        //        }

        //        for (var e : visited2.entrySet()) {
        //            var l = e.getValue();
        //            if (l != null && l <= (steps + 1) && l % 2 != steps % 2) {
        //                c++;
        //            }
        //        }

        //        for (var e : visited3.entrySet()) {
        //            var l = e.getValue() - visited2.get(e.getKey());
        //            System.out.println("%s,%s: %s (%s - %s)".formatted(e.getKey().getKey(), e.getKey().getValue(), l, e.getValue(), visited2.get(e.getKey())));
        //        }

        //        for (var i = 0; i < steps; i++) {
        //            visitedAgain.values().stream().filter(ii -> ii < i).findAny();
        //        }

        //        visited2.values().forEach(System.out::println);
        //        visited3.values().forEach(System.out::println);

        //        System.out.println(visited.size());
        //        System.out.println(visited2.size());
        //        System.out.println(visited3.size());

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

//        if (System.getenv().get("DEBUG") != null) {
            for (int yO = -10; yO <= 10; yO++) {
                for (int y = 0; y < input.length; y++) {
                    for (int xO = -10; xO <= 10; xO++) {
                        System.out.print(" ");
                        for (int x = 0; x < input[y].length(); x++) {
                            if (result.containsKey("%s,%s,%s,%s".formatted(x, y, xO, yO))) {
//                                System.out.print(" O");
                                String s = "   " + result.get("%s,%s,%s,%s".formatted(x, y, xO, yO));
                                System.out.print(s.substring(s.length() - 3));
                            } else {
                                final var c = input[y].charAt(x);
                                System.out.print("  " + (c == 'S' ? '.' : c));

                            }
                        }
                    }
                    System.out.println();
                }
                System.out.println();
            }
//        }

        //        System.out.println("--- " + wasHere.size() + " ---");
        //        wasHere.stream().filter(s -> !s.endsWith(",0,0")).forEach(System.out::println);

        //        wasHere.entrySet().stream().filter(e -> e.getValue()).filter(e -> !e.getKey().endsWith(",0,0")).forEach(System.out::println);

        //        wasHere.keySet().stream().sorted().forEach(System.out::println);

//        result.stream().sorted().forEach(System.out::println);

        return result.values().stream().filter(i -> i % 2 == 0).count();

//        return result.size() / 2;

    }

    static Map<String, Integer> result = new HashMap<>();
//
    //    static void walk(Map<Map.Entry<Integer, Integer>, Map<Map.Entry<Integer, Integer>, Integer>> edges,
    //            int x, int y,
    //            int xOff, int yOff,
    //            int xMax, int yMax,
    //            int steps, int lengthAcc) {
    //
    //        if (xOff == 0 && yOff == 0) {
    //            return;
    //        }
    //        //        final var mapKey = "%s,%s,%s,%s".formatted(x, y, xOff, yOff);
    //        //
    //        //        final var min = record.get(mapKey);
    //        //
    //        //        if (min != null && lengthAcc > min) {
    //        //            return;
    //        //        }
    //        //
    //        //        record.put(mapKey, lengthAcc);
    //
    //        //        if (result.contains(mapKey)) {
    //        //            return;
    //        //        }
    //
    //        if (lengthAcc > steps) {
    //            return;
    //        }
    //
    //        var position = edges.get(Map.entry(x, y));
    //
    //        for (var e : position.entrySet()) {
    //            final var xx = e.getKey().getKey();
    //            final var yy = e.getKey().getValue();
    //
    //            var length = lengthAcc + e.getValue();
    //            if (length <= steps && length % 2 == steps % 2) {
    //                result.add("%s,%s,%s,%s".formatted(xx, yy, xOff, yOff));
    //            }
    //
    //            if (xx == 0) {
    //                walk(edges, xMax - 1, yy, xOff - 1, yOff, xMax, yMax, steps, length + 1);
    //            } else if (xx == xMax - 1) {
    //                walk(edges, 0, yy, xOff + 1, yOff, xMax, yMax, steps, length + 1);
    //            } else if (yy == 0) {
    //                walk(edges, xx, yMax - 1, xOff, yOff - 1, xMax, yMax, steps, length + 1);
    //            } else if (yy == yMax - 1) {
    //                walk(edges, xx, 0, xOff, yOff + 1, xMax, yMax, steps, length + 1);
    //            }
    //        }
    //    }

}
