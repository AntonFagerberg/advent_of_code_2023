package advent_of_code_2023;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

public class Day18 {

    static int part1(String[] input) {
        int x = 0;
        int y = 0;
        var l = new ArrayList<String>();
        l.add("0,0");
        for (var line : input) {
            var parts = line.split(" ");
            var dir = parts[0];
            var nr = Integer.parseInt(parts[1]);

            for (int i = 0; i < nr; i++) {
                if (dir.equals("R")) {
                    x++;
                } else if (dir.equals("L")) {
                    x--;
                } else if (dir.equals("D")) {
                    y++;
                } else if (dir.equals("U")) {
                    y--;
                } else {
                    throw new IllegalStateException("");
                }

                l.add("%s,%s".formatted(x, y));
            }
        }

        l.forEach(System.out::println);

        var xMax = 0;
        var xMin = 0;
        var yMax = 0;
        var yMin = 0;
        var p = new Polygon();
        for (var xy : l) {
            var z = xy.split(",");
            int x1 = Integer.parseInt(z[0]);
            int y1 = Integer.parseInt(z[1]);
            xMax = Math.max(xMax, x1);
            xMin = Math.min(xMin, x1);
            yMax = Math.max(yMax, y1);
            yMin = Math.min(yMin, y1);
            p.addPoint(x1, y1);
        }

        System.out.println(xMax);
        System.out.println(yMax);

        int result = 0;
        for (int yy = yMin; yy <= yMax; yy++) {
            for (int xx = xMin; xx <= xMax; xx++) {
                if (l.contains("%s,%s".formatted(xx, yy)) || p.contains(xx, yy)) {
                    result++;
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }

        return result;
    }

    static int part3(String[] input) {
        int x = 0;
        int y = 0;
        var l = new ArrayList<String>();
        l.add("0,0");
        for (var line : input) {
            var parts = line.split(" ");
            var dir = parts[0];
            var nr = Integer.parseInt(parts[1]);

            for (int i = 0; i < nr; i++) {
                if (dir.equals("R")) {
                    x++;
                } else if (dir.equals("L")) {
                    x--;
                } else if (dir.equals("D")) {
                    y++;
                } else if (dir.equals("U")) {
                    y--;
                } else {
                    throw new IllegalStateException("");
                }

                l.add("%s,%s".formatted(x, y));
            }
        }

//        l.forEach(System.out::println);

        var xMax = 0;
        var xMin = 0;
        var yMax = 0;
        var yMin = 0;
        var p = new Polygon();
        for (var xy : l) {
            var z = xy.split(",");
            int x1 = Integer.parseInt(z[0]);
            int y1 = Integer.parseInt(z[1]);
            xMax = Math.max(xMax, x1);
            xMin = Math.min(xMin, x1);
            yMax = Math.max(yMax, y1);
            yMin = Math.min(yMin, y1);
            p.addPoint(x1, y1);
        }

        System.out.println(xMax);
        System.out.println(yMax);

        for (int i = 0; i < p.xpoints.length; i++) {
            try {
                System.out.println("%s,%s -> %s".formatted(p.xpoints[i], p.ypoints[i], l.get(i)));
            } catch (IndexOutOfBoundsException e) {

            }
        }

        int result = 0;
        for (int yy = yMin; yy <= yMax; yy++) {
            for (int xx = xMin; xx <= xMax; xx++) {
                if (p.contains(new Point(xx, yy))) {
                    result++;
                    System.out.print("#");
                } else {
                    if (l.contains("%s,%s".formatted(xx,yy))) {
                        System.out.println("-> %s,%s".formatted(xx,yy));
                    }
                    System.out.print(".");
                }
            }
            System.out.println();
        }

        return result;
    }

    static long part2(String[] input) {
        int x = 0;
        int y = 0;
        List<List<Integer>> l = new ArrayList<>();
        l.add(List.of(0,0));
        for (var line : input) {
            var parts = line.split(" ");
            var nr = Long.parseLong(parts[2].substring(2, 2 + 5), 16);
            var di = Integer.parseInt(parts[2].substring(2 + 5, 2 + 5 + 1));

//            for (long i = 0; i < nr; i++) {
                if (di == 0) {
                    x += nr - 1;
                } else if (di == 2) {
                    x -= nr - 1;
                } else if (di == 1) {
                    y += nr -1;
                } else if (di == 3) {
                    y -= nr -1;
                } else {
                    throw new IllegalStateException("");
                }

                l.add(List.of(x,y));
//            }
        }

//        l.forEach(System.out::println);

        var xMax = 0L;
        var xMin = 0L;
        var yMax = 0L;
        var yMin = 0L;
        var p = new Polygon();
        for (var xy : l) {
            int x1 = xy.getFirst();
            int y1 = xy.getLast();
            xMax = Math.max(xMax, x1);
            xMin = Math.min(xMin, x1);
            yMax = Math.max(yMax, y1);
            yMin = Math.min(yMin, y1);
            p.addPoint(x1, y1);
        }

        System.out.println(xMin);
        System.out.println(xMax);
        System.out.println(yMin);
        System.out.println(yMax);


//        var d = Areas.approxArea(p.getPathIterator(new AffineTransform()));
        var d = calcAreaSize(new Area(p));
        System.out.println(d);
        return Double.valueOf(d).longValue();


//        long result = 0;
//        for (long yy = yMin; yy <= yMax; yy++) {
////            System.out.println(yy / (double) yMax);
//            for (long xx = xMin; xx <= xMax; xx++) {
//                if (p.contains(xx, yy)) {
//                    result++;
////                    System.out.print("#");
//                } else {
////                    System.out.print(".");
//                }
//            }
//            System.out.println();
//        }

//        return result;
    }

    static long calcAreaSize(Area area){
        long sum = 0;
        float xBegin=0, yBegin=0, xPrev=0, yPrev=0, coords[] = new float[6];
        for (PathIterator iterator1 = area.getPathIterator(null, 0.1); !iterator1.isDone(); iterator1.next()){
            switch (iterator1.currentSegment(coords))
            {
                case PathIterator.SEG_MOVETO:
                    xBegin = coords[0]; yBegin = coords[1];
                    break;
                case PathIterator.SEG_LINETO:
                    // the well-known trapez-formula
                    sum += (coords[0] - xPrev) * (coords[1] + yPrev) / 2.0;
                    break;
                case PathIterator.SEG_CLOSE:
                    sum += (xBegin - xPrev) * (yBegin + yPrev) / 2.0;
                    break;
                default:
                    // curved segments cannot occur, because we have a flattened ath
                    throw new InternalError();
            }
            xPrev = coords[0]; yPrev = coords[1];
        }
        return sum;
    }

}
