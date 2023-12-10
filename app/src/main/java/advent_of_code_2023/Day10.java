package advent_of_code_2023;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Day10 {

    static int part1(String[] input) {
        int x = -1;
        int y = -1;
        for (int yy = 0; yy < input.length; yy++) {
            for (int xx = 0; xx < input[yy].length(); xx++) {
                if (input[yy].charAt(xx) == 'S') {
                    x = xx;
                    y = yy;
                }
            }

        }

        var visited = new ArrayList<String>();
        while (true) {
            var current = input[y].charAt(x);

            String key = "%s,%s".formatted(x, y);

            if (current == '|') {
                for (var yy : List.of(y - 1, y + 1)) {
                    var newKey = "%s,%s".formatted(x, yy);
                    if (!visited.getLast().equals(newKey)) {
                        y = yy;
                    }
                }
            } else if (current == '-') {
                for (var xx : List.of(x - 1, x + 1)) {
                    var newKey = "%s,%s".formatted(xx, y);
                    if (!visited.getLast().equals(newKey)) {
                        x = xx;
                    }
                }
            } else if (current == 'F') {
                for (var xy : List.of(List.of(x, y + 1), List.of(x + 1, y))) {
                    var xx = xy.getFirst();
                    var yy = xy.getLast();
                    var newKey = "%s,%s".formatted(xx, yy);
                    if (!visited.getLast().equals(newKey)) {
                        x = xx;
                        y = yy;
                    }
                }
            } else if (current == 'J') {
                for (var xy : List.of(List.of(x, y - 1), List.of(x - 1, y))) {
                    var xx = xy.getFirst();
                    var yy = xy.getLast();

                    var newKey = "%s,%s".formatted(xx, yy);
                    if (!visited.getLast().equals(newKey)) {
                        x = xx;
                        y = yy;
                    }
                }
            } else if (current == 'L') {
                for (var xy : List.of(List.of(x, y - 1), List.of(x + 1, y))) {
                    var xx = xy.getFirst();
                    var yy = xy.getLast();
                    var newKey = "%s,%s".formatted(xx, yy);
                    if (!visited.getLast().equals(newKey)) {
                        x = xx;
                        y = yy;
                    }
                }
            } else if (current == '7') {
                for (var xy : List.of(List.of(x, y + 1), List.of(x - 1, y))) {
                    var xx = xy.getFirst();
                    var yy = xy.getLast();
                    var newKey = "%s,%s".formatted(xx, yy);
                    if (!visited.getLast().equals(newKey)) {
                        x = xx;
                        y = yy;
                    }
                }
            } else if (current == 'S') {
                if (visited.isEmpty()) {
                    if (Set.of('|', 'L', 'J').contains(input[y + 1].charAt(x))) {
                        y++;
                    } else if (Set.of('|', '7', 'F').contains(input[y - 1].charAt(x))) {
                        y--;
                    } else if (Set.of('-', '7', 'J').contains(input[y].charAt(x + 1))) {
                        x++;
                    } else if (Set.of('-', 'L', 'F').contains(input[y - 1].charAt(x - 1))) {
                        x--;
                    }
                } else {
                    break;
                }
            } else {
                throw new IllegalStateException(":(");
            }

            visited.add(key);
        }

        return visited.size() / 2;
    }

    static int part2(String[] input) {
        int x = -1;
        int y = -1;
        for (int yy = 0; yy < input.length; yy++) {
            for (int xx = 0; xx < input[yy].length(); xx++) {
                if (input[yy].charAt(xx) == 'S') {
                    x = xx;
                    y = yy;
                }
            }
        }

        var visited = new ArrayList<String>();
        while (true) {
            var current = input[y].charAt(x);
            String key = "%s,%s".formatted(x, y);

            if (current == '|') {
                for (var yy : List.of(y - 1, y + 1)) {
                    var newKey = "%s,%s".formatted(x, yy);
                    if (!visited.getLast().equals(newKey)) {
                        y = yy;
                    }
                }
            } else if (current == '-') {
                for (var xx : List.of(x - 1, x + 1)) {
                    var newKey = "%s,%s".formatted(xx, y);
                    if (!visited.getLast().equals(newKey)) {
                        x = xx;
                    }
                }
            } else if (current == 'F') {
                for (var xy : List.of(List.of(x, y + 1), List.of(x + 1, y))) {
                    var xx = xy.getFirst();
                    var yy = xy.getLast();
                    var newKey = "%s,%s".formatted(xx, yy);
                    if (!visited.getLast().equals(newKey)) {
                        x = xx;
                        y = yy;
                    }
                }
            } else if (current == 'J') {
                for (var xy : List.of(List.of(x, y - 1), List.of(x - 1, y))) {
                    var xx = xy.getFirst();
                    var yy = xy.getLast();

                    var newKey = "%s,%s".formatted(xx, yy);
                    if (!visited.getLast().equals(newKey)) {
                        x = xx;
                        y = yy;
                    }
                }
            } else if (current == 'L') {
                for (var xy : List.of(List.of(x, y - 1), List.of(x + 1, y))) {
                    var xx = xy.getFirst();
                    var yy = xy.getLast();
                    var newKey = "%s,%s".formatted(xx, yy);
                    if (!visited.getLast().equals(newKey)) {
                        x = xx;
                        y = yy;
                    }
                }
            } else if (current == '7') {
                for (var xy : List.of(List.of(x, y + 1), List.of(x - 1, y))) {
                    var xx = xy.getFirst();
                    var yy = xy.getLast();
                    var newKey = "%s,%s".formatted(xx, yy);
                    if (!visited.getLast().equals(newKey)) {
                        x = xx;
                        y = yy;
                    }
                }
            } else if (current == 'S') {
                if (visited.isEmpty()) {
                    if (Set.of('|', 'L', 'J').contains(input[y + 1].charAt(x))) {
                        y++;
                    } else if (Set.of('|', '7', 'F').contains(input[y - 1].charAt(x))) {
                        y--;
                    } else if (Set.of('-', '7', 'J').contains(input[y].charAt(x + 1))) {
                        x++;
                    } else if (Set.of('-', 'L', 'F').contains(input[y - 1].charAt(x - 1))) {
                        x--;
                    }
                } else {
                    break;
                }
            } else {
                throw new IllegalStateException(":(");
            }

            visited.add(key);
        }

        var xs = new int[visited.size()];
        var ys = new int[visited.size()];
        int k = 0;
        for (var v : visited) {
            var parts = v.split(",");
            xs[k] = Integer.parseInt(parts[0]);
            ys[k] = Integer.parseInt(parts[1]);
            k++;
        }


        var poly = new Polygon(xs, ys, xs.length);
        int result = 0;
        for (int yy = 0; yy < input.length; yy++) {
            for (int xx = 0; xx < input[yy].length(); xx++) {
                var key = "%s,%s".formatted(xx, yy);
                if (!visited.contains(key) && poly.contains(xx, yy)) {
                    result++;
                }
            }

        }

        return result;
    }

}
