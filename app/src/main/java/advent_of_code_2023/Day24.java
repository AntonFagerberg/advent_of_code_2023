package advent_of_code_2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day24 {

    static int part1(String[] input, long min, long max) {
        var equations = new ArrayList<Map.Entry<Double, Double>>();
        var start = new ArrayList<Map.Entry<Long, Long>>();
        var delta = new ArrayList<Map.Entry<Long, Long>>();

        for (var l : input) {
            var parts = l.split("[, @]+");
            var x = Long.parseLong(parts[0]);
            var y = Long.parseLong(parts[1]);
            var vx = Long.parseLong(parts[3]);
            var vy = Long.parseLong(parts[4]);

            long x2 = x + vx;
            long y2 = y + vy;

            var m = (y2 - y) / (double) (x2 - x);
            var bb = y - m * x;

            equations.add(Map.entry(m, bb));
            start.add(Map.entry(x, y));
            delta.add(Map.entry(x2 - x, y2 - y));

        }

        int result = 0;

        for (var i = 0; i < equations.size() - 1; i++) {
            for (var j = i + 1; j < equations.size(); j++) {
                var eq1 = equations.get(i);
                var eq2 = equations.get(j);
                var m1 = eq1.getKey();
                var b1 = eq1.getValue();
                var m2 = eq2.getKey();
                var b2 = eq2.getValue();

                double x = (b2 - b1) / (m1 - m2);
                double y = m1 * x + b1;

                if (x >= min && y >= min && x <= max && y <= max) {
                    var ok = true;
                    for (var ij : List.of(i, j)) {
                        var dx = delta.get(ij).getKey();
                        var dy = delta.get(ij).getValue();
                        var sx = start.get(ij).getKey();
                        var sy = start.get(ij).getValue();

                        if (dx >= 0) {
                            ok = ok && x >= sx;
                        } else {
                            ok = ok && x <= sx;
                        }

                        if (dy >= 0) {
                            ok = ok && y >= sy;
                        } else {
                            ok = ok && y <= sy;
                        }
                    }
                    if (ok) {
                        result++;
                    }
                }
            }
        }

        return result;
    }

}
