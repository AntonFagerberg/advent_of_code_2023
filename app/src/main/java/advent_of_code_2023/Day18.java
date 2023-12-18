package advent_of_code_2023;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Day18 {

    static int part1(String[] input) {
        int x = 0;
        int y = 0;
        var coordinates = new ArrayList<String>();
        coordinates.add("0,0");
        for (var line : input) {
            var parts = line.split(" ");
            var dir = parts[0];
            var nr = Integer.parseInt(parts[1]);

            for (int i = 0; i < nr; i++) {
                switch (dir) {
                    case "R" -> x++;
                    case "L" -> x--;
                    case "D" -> y++;
                    case "U" -> y--;
                    default -> throw new IllegalStateException("");
                }

                coordinates.add("%s,%s".formatted(x, y));
            }
        }

        var xMax = 0;
        var xMin = 0;
        var yMax = 0;
        var yMin = 0;
        var polygon = new Polygon();
        for (var xy : coordinates) {
            var z = xy.split(",");
            int x1 = Integer.parseInt(z[0]);
            int y1 = Integer.parseInt(z[1]);
            xMax = Math.max(xMax, x1);
            xMin = Math.min(xMin, x1);
            yMax = Math.max(yMax, y1);
            yMin = Math.min(yMin, y1);
            polygon.addPoint(x1, y1);
        }

        int result = 0;
        for (int yy = yMin; yy <= yMax; yy++) {
            for (int xx = xMin; xx <= xMax; xx++) {
                if (coordinates.contains("%s,%s".formatted(xx, yy)) || polygon.contains(xx, yy)) {
                    result++;
                }
            }
        }

        return result;
    }

    static long part2(String[] input) {
        int x = 0;
        int y = 0;
        var coordinates = new ArrayList<List<Integer>>();

        coordinates.add(List.of(0, 0));
        for (var line : input) {
            var parts = line.split(" ");
            var nr = (int) Long.parseLong(parts[2].substring(2, 2 + 5), 16);
            var di = Integer.parseInt(parts[2].substring(2 + 5, 2 + 5 + 1));

            if (di == 0) {
                x += nr;
            } else if (di == 2) {
                x -= nr;
            } else if (di == 1) {
                y += nr;
            } else if (di == 3) {
                y -= nr;
            } else {
                throw new IllegalStateException("");
            }

            coordinates.add(List.of(x, y));
        }

        var enclosingPolygon = new ArrayList<List<Integer>>();
        enclosingPolygon.add(coordinates.getFirst());

        for (int i = 2; i < coordinates.size(); i++) {
            var x1 = coordinates.get(i - 2).getFirst();
            var y1 = coordinates.get(i - 2).getLast();

            var x2 = coordinates.get(i).getFirst();
            var y2 = coordinates.get(i).getLast();

            var dx = x2 - x1;
            var dy = y2 - y1;

            var x3 = coordinates.get(i - 1).getFirst();
            var y3 = coordinates.get(i - 1).getLast();

            // This depends on how the polygon is built - so it doesn't work on part 1
            // Too tired to fix.
            if (dx > 0 && dy > 0) {
                enclosingPolygon.add(List.of(x3 + 1, y3));
            } else if (dx > 0 && dy < 0) {
                enclosingPolygon.add(List.of(x3, y3));
            } else if (dx < 0 && dy > 0) {
                enclosingPolygon.add(List.of(x3 + 1, y3 + 1));
            } else if (dx < 0 && dy < 0) {
                enclosingPolygon.add(List.of(x3, y3 + 1));
            } else {
                throw new IllegalStateException();
            }
        }

        coordinates = enclosingPolygon;

        long result = 0;

        for (int i = 0; i < coordinates.size() - 1; i++) {
            result += ((long) coordinates.get(i).getFirst()) * coordinates.get((i + 1)).getLast() -
                    ((long) coordinates.get(i).getLast()) * coordinates.get((i + 1)).getFirst();
        }

        return result / 2;
    }

}
