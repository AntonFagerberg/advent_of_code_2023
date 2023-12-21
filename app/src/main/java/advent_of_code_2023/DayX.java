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

    static class Grid {

        Map.Entry<Integer, Integer> minUp;
        Map.Entry<Integer, Integer> minDown;
        Map.Entry<Integer, Integer> minLeft;
        Map.Entry<Integer, Integer> minRight;
        Map<Map.Entry<Integer, Integer>, Integer> lengths;
        int stepsTaken = 0;

        List<Map.Entry<Integer, Integer>> mins;

        Grid copy() {
            var g = new Grid();
            g.minUp = minUp;
            g.minDown = minDown;
            g.minLeft = minLeft;
            g.minRight = minRight;
            g.lengths = lengths;
            g.mins = mins;
            return g;
        }

        static Grid of(int x, int y) {
            var g = new Grid();
            g.lengths = buildLengths(x, y);

            int minXY0 = Integer.MAX_VALUE;
            int minXYMax = Integer.MAX_VALUE;
            for (int xx = 0; xx < xMax; xx++) {
                var minXY0Coord = Map.entry(xx, 0);
                var minXY0p = Math.min(minXY0, g.lengths.get(minXY0Coord));
                if (minXY0p < minXY0) {
                    minXY0 = minXY0p;
                    g.minUp = minXY0Coord;
                }

                var minXYMaxCoord = Map.entry(xx, yMax - 1);
                var minXYMaxp = Math.min(minXYMax, g.lengths.get(minXYMaxCoord));
                if (minXYMaxp < minXYMax) {
                    minXYMax = minXYMaxp;
                    g.minDown = minXYMaxCoord;
                }
            }

            int minYX0 = Integer.MAX_VALUE;
            int minYXMax = Integer.MAX_VALUE;
            for (int yy = 0; yy < yMax; yy++) {
                var minYX0Coord = Map.entry(0, yy);
                var minYX0p = Math.min(minYX0, g.lengths.get(minYX0Coord));
                if (minYX0p < minYX0) {
                    minYX0 = minYX0p;
                    g.minLeft = minYX0Coord;
                }

                var minYXMaxCoord = Map.entry(xMax - 1, yy);
                var minYXMaxp = Math.min(minYXMax, g.lengths.get(minYXMaxCoord));
                if (minYXMaxp < minYXMax) {
                    minYXMax = minYXMaxp;
                    g.minRight = minYXMaxCoord;
                }
            }

//            g.maxLength = g.lengths.values().stream().max(Integer::compareTo).get();
            g.mins = List.of(g.minDown, g.minUp, g.minLeft, g.minRight);

            return g;
        }
    }

    static HashMap<Map.Entry<Integer, Integer>, Grid> gridCache = new HashMap<>();

    static Grid getGrid(int x, int y) {
        var key = Map.entry(x, y);
        if (!gridCache.containsKey(key)) {
//            System.out.println("miss! %s, %s".formatted(x, y));
            var v = Grid.of(x, y);
            gridCache.put(key, v);
            return v;
        }

//        System.out.println("hit! %s, %s".formatted(x, y));
        return gridCache.get(key);
    }

    static int xMax;
    static int yMax;

    static Map<Map.Entry<Integer, Integer>, Integer> buildLengths(int x, int y) {
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

    static Set<Map.Entry<Integer, Integer>> rocks = new HashSet<>();
    static Set<Map.Entry<Integer, Integer>> bigCache = new HashSet<>();

//    static List<Grid> traverse(List<String> positions, int stepsLeft) {
//
//    }

    static long part2(String[] input, int steps) {
        result.clear();
        rocks.clear();

        var sx = -1;
        var sy = -1;

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

        yMax = input.length;
        xMax = input[0].length();

        var q = new LinkedList<String>();
        q.addFirst("%s,%s,%s,%s,%s".formatted(sx, sy, 0, 0, 0));

        var result = new HashSet<Grid>();

        while (!q.isEmpty()) {
            var parts = q.removeFirst().split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int xO = Integer.parseInt(parts[2]);
            int yO = Integer.parseInt(parts[3]);
            int stepsTaken = Integer.parseInt(parts[4]);

            System.out.println("%s,%s = %s".formatted(xO, yO, q.size()));

            var bigKey = Map.entry(xO, yO);
            // TODO > eller >=
            if (!bigCache.contains(bigKey) && steps - stepsTaken >= 0) {
                bigCache.add(bigKey);

                var grid = getGrid(x, y);

//                if (stepsLeft >= grid.maxLength) {
//                    result.put(grid, 1 + result.getOrDefault(grid, 0));
//                } else {
//                    var g = grid.copy();
//                    g.stepsTaken = stepsLeft;
//                    result.put(g, 1 + result.getOrDefault(g, 0));
//                }

                var gc = grid.copy();
                gc.stepsTaken = stepsTaken;
                result.add(gc);

                for (var c : grid.mins) {
                    var cx = c.getKey();
                    var cy = c.getValue();

                    if (c.equals(grid.minLeft)) {
                        if (!bigCache.contains(Map.entry(xO - 1, yO))) {
                            q.addLast("%s,%s,%s,%s,%s".formatted(xMax - 1, cy, xO - 1, yO, stepsTaken + 1 + grid.lengths.get(c)));
                        }
                    } else if (c.equals(grid.minRight)) {
                        if (!bigCache.contains(Map.entry(xO + 1, yO))) {
                            q.addLast("%s,%s,%s,%s,%s".formatted(0, cy, xO + 1, yO, stepsTaken + 1 + grid.lengths.get(c)));
                        }
                    } else if (c.equals(grid.minUp)) {
                        if (!bigCache.contains(Map.entry(xO, yO - 1))) {
                            q.addLast("%s,%s,%s,%s,%s".formatted(cx, yMax - 1, xO, yO - 1, stepsTaken + 1 + grid.lengths.get(c)));
                        }
                    } else if (c.equals(grid.minDown)) {
                        if (!bigCache.contains(Map.entry(xO, yO + 1))) {
                            q.addLast("%s,%s,%s,%s,%s".formatted(cx, 0, xO, yO + 1, stepsTaken + 1 + grid.lengths.get(c)));
                        }
                    }
                }


//                q.addLast("%s,%s,%s,%s,%s".formatted(grid.minLeft.getKey(), grid.minLeft.getKey().byteValue(), xO - 1, yO, stepsLeft - grid.lengths.get(grid.minLeft) - 1));
//                q.addLast("%s,%s,%s,%s,%s".formatted(grid.minRight.getKey(), grid.minRight.getKey().byteValue(), xO + 1, yO, stepsLeft - grid.lengths.get(grid.minRight) - 1));
//                q.addLast("%s,%s,%s,%s,%s".formatted(grid.minUp.getKey(), grid.minUp.getKey().byteValue(), xO, yO - 1, stepsLeft - grid.lengths.get(grid.minUp) - 1));
//                q.addLast("%s,%s,%s,%s,%s".formatted(grid.minDown.getKey(), grid.minDown.getKey().byteValue(), xO, yO + 1, stepsLeft - grid.lengths.get(grid.minDown) - 1));
            }
        }

        var r = 0L;
        var iii = result.size();
        var ii = 0;
        for (var g : result) {
            ii++;
            System.out.println("%s of %s".formatted(ii, iii));

//            System.out.println("%s -> %s".formatted(g, mul));
//            System.out.println(g.lengths);
//            System.out.println(g.stepsTaken);

            r += g.lengths.values().stream()
                    .map(i -> i + g.stepsTaken)
                    .filter(i -> i <= steps && i % 2 == steps % 2)
                    .count();

//            r += mul * g.lengths.values().stream().filter(i -> i <= g.stepsTaken && i % 2 == steps % 2).count();
        }

//        System.out.println(result);
        return r;


//        var nextPositions = new ArrayList<String>();
//        var result = new ArrayList<Grid>();
//
//        for (var position: positions) {
//            var parts = position.spl
//        }

//        var visited = buildLengths(sx, sy);
////        Map<Map.Entry<Integer, Integer>, Map<Map.Entry<Integer, Integer>, Integer>> edges = new HashMap<>();
////
////        for (int y = 0; y < input.length; y++) {
////            for (int x = 0; x < input[y].length(); x++) {
////                if (x == 0 || y == 0 || x == xMax - 1 || y == yMax - 1) {
////                    edges.put(Map.entry(x, y), buildLengths(x, y));
////                }
////            }
////        }
//
//        //        var q = new PriorityQueue<State>();
//        //        visited.put(Map.entry(sx, sy), 1);
//        //        q.add(new State(sx, sy, List.of(Map.entry(sx, sy))));
//        //
//        //        while (!q.isEmpty()) {
//        //            var s = q.poll();
//        //
//        //            for (int xn = -1; xn <= 1; xn++) {
//        //                for (int yn = -1; yn <= 1; yn++) {
//        //                    if (xn == 0 || yn == 0) {
//        //                        var xd = s.x + xn;
//        //                        var yd = s.y + yn;
//        //
//        //                        var coordinate = Map.entry(xd, yd);
//        //                        if (xd >= 0 && yd >= 0 && xd < xMax && yd < yMax && !rocks.contains(coordinate)) {
//        //                            if (!visited.containsKey(coordinate)) {
//        //                                var hd = new ArrayList<>(s.history);
//        //                                hd.add(coordinate);
//        //                                q.add(new State(xd, yd, hd));
//        //
//        //                                visited.put(coordinate, hd.size());
//        //                            }
//        //                        }
//        //                    }
//        //                }
//        //            }
//        //        }
//
//        //        for (var rem = steps; rem > 0; rem--) {
//        //
//        //        }
//
//        //        var result = 0;
//        //        Map<Map.Entry<Integer, Integer>, Integer> positions = new HashMap<>();
//
//        var q = new PriorityQueue<Magic>();
//
//        for (var e : visited.entrySet()) {
//            var length = e.getValue();
//            var key = e.getKey();
//            var x = key.getKey();
//            var y = key.getValue();
//
//            if (length <= steps) {
//                result.put("%s,%s,%s,%s".formatted(x, y, 0, 0), length);
//            }
//
//            if (x == 0) {
//                var m = new Magic();
//                m.x = xMax - 1;
//                m.y = y;
//                m.xO = -1;
//                m.yO = 0;
//                m.steps = length + 1;
//                q.add(m);
//                //                walk(edges, xMax - 1, y, -1, 0, xMax, yMax, steps, length + 1);
//            } else if (x == xMax - 1) {
//                var m = new Magic();
//                m.x = 0;
//                m.y = y;
//                m.xO = 1;
//                m.yO = 0;
//                m.steps = length + 1;
//                q.add(m);
//                //                walk(edges, 0, y, 1, 0, xMax, yMax, steps, length + 1);
//            }
//
//            if (y == 0) {
//                var m = new Magic();
//                m.x = x;
//                m.y = yMax - 1;
//                m.xO = 0;
//                m.yO = -1;
//                m.steps = length + 1;
//                q.add(m);
//                //                walk(edges, x, yMax - 1, 0, -1, xMax, yMax, steps, length + 1);
//            } else if (y == yMax - 1) {
//                var m = new Magic();
//                m.x = x;
//                m.y = 0;
//                m.xO = 0;
//                m.yO = 1;
//                m.steps = length + 1;
//                q.add(m);
//                //                walk(edges, x, 0, 0, 1, xMax, yMax, steps, length + 1);
//            }
//
//            //                for (int xn = -1; xn <= 1; xn++) {
//            //                    for (int yn = -1; yn <= 1; yn++) {
//            //                        if ((xn == 0 || yn == 0) && (xn != yn)) {
//            //                            int xd = x + xn;
//            //                            int yd = y + yn;
//            //
//            //                            if (xd < 0) {
//            //                                xd += xMax;
//            //                            } else if (xd >= xMax) {
//            //                                xd -= xMax;
//            //                            } else if (yd < 0) {
//            //                                yd += yMax;
//            //                            } else if (yd >= yMax) {
//            //                                yd -= yMax;
//            //                            } else {
//            //                                throw new IllegalStateException();
//            //                            }
//            //
//            //                            if (xd == 0 || yd == 0 || xd == xMax - 1 || yd == yMax - 1) {
//            //                                positions.put(Map.entry(xd, yd), length + 1);
//            //                            }
//            //                        }
//            //                    }
//            //                }
//            //            }
//        }
//
//        var cache = new HashMap<String, Integer>();
//
////        while (!q.isEmpty()) {
////            var next = q.poll();
////
////            final var cachedValue = cache.get("%s,%s,%s,%s".formatted(next.x, next.y, next.xO, next.yO));
////            cache.put("%s,%s,%s,%s".formatted(next.x, next.y, next.xO, next.yO), cachedValue == null || cachedValue > next.steps ? next.steps : cachedValue);
////            if (next.steps > steps || (cachedValue != null && cachedValue <= next.steps)) {
////
////            } else {
////                var position = edges.get(Map.entry(next.x, next.y));
////
////                for (var e : position.entrySet()) {
////                    final var xx = e.getKey().getKey();
////                    final var yy = e.getKey().getValue();
////
////                    var length = next.steps + e.getValue();
////
////                    if (length <= steps) {
////                        String key = "%s,%s,%s,%s".formatted(xx, yy, next.xO, next.yO);
////                        var old = result.get(key);
////                        result.put(key, old != null && old < length ? old : length);
////                    }
////
////                    if (xx == 0) {
////                        var m = new Magic();
////                        m.x = xMax - 1;
////                        m.y = yy;
////                        m.xO = next.xO - 1;
////                        m.yO = next.yO;
////                        m.steps = length + 1;
////                        q.add(m);
////                    } else if (xx == xMax - 1) {
////                        var m = new Magic();
////                        m.x = 0;
////                        m.y = yy;
////                        m.xO = next.xO + 1;
////                        m.yO = next.yO;
////                        m.steps = length + 1;
////                        q.add(m);
////                    }
////
////                    if (yy == 0) {
////                        var m = new Magic();
////                        m.x = xx;
////                        m.y = yMax - 1;
////                        m.xO = next.xO;
////                        m.yO = next.yO - 1;
////                        m.steps = length + 1;
////                        q.add(m);
////                    } else if (yy == yMax - 1) {
////                        var m = new Magic();
////                        m.x = xx;
////                        m.y = 0;
////                        m.xO = next.xO;
////                        m.yO = next.yO + 1;
////                        m.steps = length + 1;
////                        q.add(m);
////                    }
////                }
////            }
////        }
//
//        // max = 130
//        // min = 65
//
//            for (int yO = -10; yO <= 10; yO++) {
//                for (int y = 0; y < input.length; y++) {
//                    for (int xO = -10; xO <= 10; xO++) {
//                        System.out.print(" ");
//                        for (int x = 0; x < input[y].length(); x++) {
//                            if (result.containsKey("%s,%s,%s,%s".formatted(x, y, xO, yO))) {
////                                System.out.print(" O");
//                                String s = "    " + result.get("%s,%s,%s,%s".formatted(x, y, xO, yO));
//                                System.out.print(s.substring(s.length() - 4));
//                            } else {
//                                final var c = input[y].charAt(x);
//                                System.out.print("   " + (c == 'S' ? '.' : c));
//
//                            }
//                        }
//                    }
//                    System.out.println();
//                }
//                System.out.println();
//            }
//
//        return result.values().stream().filter(i -> i % 2 == 0).count();
    }

    static Map<String, Integer> result = new HashMap<>();

}
