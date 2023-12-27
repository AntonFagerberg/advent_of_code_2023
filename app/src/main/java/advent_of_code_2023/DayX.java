package advent_of_code_2023;

import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DayX {

    static public Optional<Map.Entry<Double, Double>> calculateIntersectionPoint(
            double m1,
            double b1,
            double m2,
            double b2) {

        if (m1 == m2) {
            return Optional.empty();
        }

        double x = (b2 - b1) / (m1 - m2);
        double y = m1 * x + b1;

        return Optional.of(Map.entry(x, y));
    }

    static class Pair {

        long first, second;

        public Pair(long first, long second) {
            this.first = first;
            this.second = second;
        }
    }

    // Function to find the line given two points
    static void lineFromPoints(Pair P, Pair Q) {
        long a = Q.second - P.second;
        double b = P.first - Q.first;
        double c = a * (P.first) + b * (P.second);

        if (b < 0) {
            //            System.out.println(
            //                    "The line passing through points P and Q is: "
            //                            + a + "x - " + b + "y = " + c);
            System.out.println("y = %sx + %s".formatted(a / b, c / b));
        } else {
            System.out.println("y = %sx + %s".formatted(a / b, c / b));
            //            System.out.println(
            //                    "The line passing through points P and Q is: "
            //                            + a + "x + " + b + "y = " + c);
        }
    }

    static int part1(String[] input) {

        var equations = new ArrayList<Map.Entry<Double, Double>>();
        var start = new ArrayList<Map.Entry<Long, Long>>();
        var delta = new ArrayList<Map.Entry<Long, Long>>();

        for (var l : input) {
            var parts = l.split("[, @]+");
            var px = Long.parseLong(parts[0]);
            var py = Long.parseLong(parts[1]);
            var pz = Long.parseLong(parts[2]);
            var vx = Long.parseLong(parts[3]);
            var vy = Long.parseLong(parts[4]);
            var vz = Long.parseLong(parts[5]);

            long x1 = px;
            long x2 = px + vx;

            long y1 = py;
            long y2 = py  + vy;

            long a = y2 - y1;
            long b = x1 - x2;
            long c = a * x1 + b * y1;

            System.out.println("%s, %s  -> %s, %s".formatted(x1,y1,x2,y2));

            var m = (y2-y1)/ (double) (x2-x1);
            var bb = y1 - m*x1;
            System.out.println("y = %sx + %s".formatted(m, bb));

            equations.add(Map.entry(m, bb));
            start.add(Map.entry(x1, y1));
            delta.add(Map.entry(x2-x1, y2-y1));


//            if (b < 0) {
//                var mx = a / (double) -b;
//                mx = -mx;
//                var bb = c / (double) -b;
//                list.add(Map.entry(mx, bb));
//
//                System.out.println(
//                        "> The line passing through points P and Q is: "
//                                + a + "x - " + b + "y = " + c);
//                System.out.println("y = %sx + %s".formatted(mx, bb));
//            } else {
//                var mx = a / (double) b;
//                mx = -mx;
//                var bb = c / (double) b;
//                list.add(Map.entry(mx, bb));
//
//                System.out.println("y = %sx + %s".formatted(mx, bb));
//                System.out.println(
//                        "The line passing through points P and Q is: "
//                                + a + "x + " + b + "y = " + c);
//            }

            System.out.println("---");

        }

        var min = 200000000000000L; // TODO
        var max = 400000000000000L;
        int c = 0;

        for (var i = 0; i < equations.size() - 1; i++) {
            for (var j = i + 1; j < equations.size(); j++) {
                var eqi = equations.get(i);
                var eqj = equations.get(j);
                var o = calculateIntersectionPoint(eqi.getKey(), eqi.getValue(), eqj.getKey(), eqj.getValue());
                if (o.isPresent()) {
                    var oo = o.get();
                    var cx = oo.getKey();
                    var cy = oo.getValue();

                    if (cx >= min && cy >= min && cx <= max && cy <= max) {
                        var ok = true;
                        for (var ij : List.of(i,j)) {
                            var dx = delta.get(ij).getKey();
                            var dy = delta.get(ij).getValue();
                            var sx = start.get(ij).getKey();
                            var sy = start.get(ij).getValue();

                            if (dx >= 0) {
                                ok = ok && cx >= sx;
                            } else {
                                ok = ok && cx <= sx;
                            }

                            if (dy >= 0) {
                                ok = ok && cy >= sy;
                            } else {
                                ok = ok && ok && cy <= sy;
                            }
                        }
                        if (ok) {
                            c++;
                            System.out.print(">   ");
                        }
                    }
                }
                System.out.println("%s -> %s | %s".formatted(o, eqi, eqj));
            }
        }

        return c;
    }

    static int part2(String[] input) {
        return -1;
    }

}
