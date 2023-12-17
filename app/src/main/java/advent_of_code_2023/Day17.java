package advent_of_code_2023;

import java.util.*;

public class Day17 {

    static int getX(String s) {
        return Integer.parseInt(s.split(",")[0]);
    }

    static int getY(String s) {
        return Integer.parseInt(s.split(",")[1]);
    }

    static String key(int x, int y) {
        return "%s,%s".formatted(x, y);
    }

    record State(int x, int y, int dx, int dy, int len, Map<String, String> history) implements Comparable<State> {

        @Override
        public int compareTo(State o) {
            return Integer.compare(len, o.len);
        }

        String seen() {
            return "%s,%s,%s,%s".formatted(x, y, dx, dy);
        }

        State get(int nx, int ny, Map<String, Integer> m) {
            var nh = new HashMap<>(history);
            nh.put(otherKey(x, y), "%s,%s".formatted(dx, dy));

            var vx = m.get(otherKey(x + nx, y));
            if (nx == 1) {
                if (dx < 0) {
                    return null;
                }
                if (dx == 3) {
                    return null;
                }

                if (vx == null) {
                    return null;
                }


                return new State(x + nx, y, dx + nx, 0, len + vx, nh);
            }

            if (nx == -1) {
                if (dx > 0) {
                    return null;
                }
                if (dx == -3) {
                    return null;
                }
                if (vx == null) {
                    return null;
                }

                return new State(x + nx, y, dx + nx, 0, len + vx, nh);
            }

            var vy = m.get(otherKey(x, y + ny));
            if (ny == 1) {
                if (dy < 0) {
                    return null;
                }
                if (dy == 3) {
                    return null;
                }
                if (vy == null) {
                    return null;
                }

                return new State(x, y + ny, 0, dy + ny, len + vy, nh);
            }

            if (ny == -1) {
                if (dy > 0) {
                    return null;
                }
                if (dy == -3) {
                    return null;
                }
                if (vy == null) {
                    return null;
                }

                return new State(x, y + ny, 0, dy + ny, len + vy, nh);
            }

            throw new IllegalStateException(":(");
        }

        int dyy() {
            if (dy > 0) {
                return 1;
            } else if (dy < 0) {
                return -1;
            }

            return 0;
        }

        int dxx() {
            if (dx > 0) {
                return 1;
            } else if (dx < 0) {
                return -1;
            }

            return 0;
        }

        String key() {
            return otherKey(x, y);
        }

        String otherKey(int x, int y) {
            return "%s,%s".formatted(x, y);
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

        var q = new PriorityQueue<State>();
        values.put("0,0", 0);
        q.add(new State(0, 0, 0, 0, 0, Map.of("0,0", "0,0")));

        var tx = input[0].length() - 1;
        var ty = input.length - 1;

        while (!q.isEmpty()) {

            var n = q.poll();
            var v = values.get(key(n.x, n.y));

            if (n.x == tx && n.y == ty) {
                for (int y = 0; y < input.length; y++) {
                    for (int x = 0; x < input[y].length(); x++) {
                        var node = "%s,%s".formatted(x, y);
                        String h = n.history.get(node);
                        if (h != null) {
                            System.out.print("#");
                        } else {
                            System.out.print(input[y].charAt(x));
                        }
                    }
                    System.out.println();
                }

                n.history.values().forEach(System.out::println);

                return n.len;
            }

            if (!seen.contains(n.seen()) && v != null) {
                seen.add(n.seen());

//                if (n.x > tx || n.y > ty) {
//                    break;
//                }


                var possibilities = List.of(List.of(-1, 0), List.of(1, 0), List.of(0, -1), List.of(0, 1));
                for (var p : possibilities) {
                    var px = p.getFirst();
                    var py = p.getLast();

                    var ns = n.get(px, py, values);
                    if (ns != null) {
                        q.add(ns);
                    }
                }
            }

        }

        return -1;
    }

    static int part2(String[] input) {
        return -1;
    }

}
