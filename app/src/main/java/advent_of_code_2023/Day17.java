package advent_of_code_2023;

import java.util.*;

public class Day17 {

    static String key(int x, int y) {
        return "%s,%s".formatted(x, y);
    }

    record State1(int x, int y, int dx, int dy, int len) implements Comparable<State1> {

        @Override
        public int compareTo(State1 o) {
            return Integer.compare(len, o.len);
        }

        String seen() {
            return "%s,%s,%s,%s".formatted(x, y, dx, dy);
        }

        State1 get(int nx, int ny, Map<String, Integer> m) {
            var vx = m.get(key(x + nx, y));
            if (nx == 1) {
                if (dx < 0 || dx == 3 || vx == null) {
                    return null;
                }

                return new State1(x + nx, y, dx + nx, 0, len + vx);
            }

            if (nx == -1) {
                if (dx > 0 || dx == -3 || vx == null) {
                    return null;
                }

                return new State1(x + nx, y, dx + nx, 0, len + vx);
            }

            var vy = m.get(key(x, y + ny));

            if (ny == 1) {
                if (dy < 0 || dy == 3 || vy == null) {
                    return null;
                }

                return new State1(x, y + ny, 0, dy + ny, len + vy);
            }

            if (ny == -1) {
                if (dy > 0 || dy == -3 || vy == null) {
                    return null;
                }

                return new State1(x, y + ny, 0, dy + ny, len + vy);
            }

            throw new IllegalStateException(":(");
        }
    }

    static int part1(String[] input) {
        var values = new HashMap<String, Integer>();
        var seen = new HashSet<String>();

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                var node = "%s,%s".formatted(x, y);
                var v = Integer.parseInt(String.valueOf(input[y].charAt(x)));
                values.put(node, v);
            }
        }

        var q = new PriorityQueue<State1>();
        values.put("0,0", 0);
        q.add(new State1(0, 0, 0, 0, 0));

        var tx = input[0].length() - 1;
        var ty = input.length - 1;

        while (!q.isEmpty()) {
            var n = q.poll();
            var v = values.get(key(n.x, n.y));

            if (n.x == tx && n.y == ty) {
                return n.len;
            }

            if (!seen.contains(n.seen()) && v != null) {
                seen.add(n.seen());

                var possibilities = List.of(List.of(-1, 0), List.of(1, 0), List.of(0, -1), List.of(0, 1));
                for (var xy : possibilities) {
                    var newState = n.get(xy.getFirst(), xy.getLast(), values);
                    if (newState != null) {
                        q.add(newState);
                    }
                }
            }

        }

        return -1;
    }

    record State2(int x, int y, int dx, int dy, int len) implements Comparable<State2> {

        @Override
        public int compareTo(State2 o) {
            return Integer.compare(len, o.len);
        }

        String seen() {
            return "%s,%s,%s,%s".formatted(x, y, dx, dy);
        }

        State2 get(int nx, int ny, Map<String, Integer> m) {
            var vx = m.get(key(x + nx, y));

            if (nx != 0 && ((dy > 0 && dy < 4) || (dy < 0 && dy > -4))) {
                return null;
            } else if (ny != 0 && ((dx > 0 && dx < 4) || (dx < 0 && dx > -4))) {
                return null;
            }

            if (nx == 1) {
                if (dx < 0 || dx == 10 || vx == null) {
                    return null;
                }

                return new State2(x + nx, y, dx + nx, 0, len + vx);
            }

            if (nx == -1) {
                if (dx > 0 || dx == -10 || vx == null) {
                    return null;
                }

                return new State2(x + nx, y, dx + nx, 0, len + vx);
            }

            var vy = m.get(key(x, y + ny));

            if (ny == 1) {
                if (dy < 0 || dy == 10 || vy == null) {
                    return null;
                }

                return new State2(x, y + ny, 0, dy + ny, len + vy);
            }

            if (ny == -1) {
                if (dy > 0 || dy == -10 || vy == null) {
                    return null;
                }

                return new State2(x, y + ny, 0, dy + ny, len + vy);
            }

            throw new IllegalStateException(":(");
        }
    }

    static int part2(String[] input) {
        var values = new HashMap<String, Integer>();
        var seen = new HashSet<String>();

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                var node = "%s,%s".formatted(x, y);
                var v = Integer.parseInt(String.valueOf(input[y].charAt(x)));
                values.put(node, v);
            }
        }

        var q = new PriorityQueue<State2>();
        values.put("0,0", 0);
        q.add(new State2(0, 0, 0, 0, 0));

        var tx = input[0].length() - 1;
        var ty = input.length - 1;

        while (!q.isEmpty()) {
            var n = q.poll();
            var v = values.get(key(n.x, n.y));

            if (n.x == tx && n.y == ty) {
                if (Math.abs(n.dx) >= 4 || Math.abs(n.dy) >= 4) {
                    return n.len;
                }
            } else {
                if (!seen.contains(n.seen()) && v != null) {
                    seen.add(n.seen());

                    var possibilities = List.of(List.of(-1, 0), List.of(1, 0), List.of(0, -1), List.of(0, 1));
                    for (var xy : possibilities) {
                        var newState = n.get(xy.getFirst(), xy.getLast(), values);
                        if (newState != null) {
                            q.add(newState);
                        }
                    }
                }
            }
        }

        return -1;
    }

}
