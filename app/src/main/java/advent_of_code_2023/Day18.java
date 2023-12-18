package advent_of_code_2023;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.checkerframework.common.value.qual.ArrayLen;

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

    static long part3(String[] input) {
        int x = 0;
        int y = 0;
        var l = new ArrayList<List<Integer>>();
        var ld = new ArrayList<List<Integer>>();

        l.add(List.of(0, 0));
        ld.add(List.of(0, 0));

        var len = 0;
//        int xd = 0;
//        int yd = 0;
        for (var line : input) {
            var parts = line.split(" ");
            var dir = parts[0];
            var nr = Integer.parseInt(parts[1]);

//            nr += 1;
//            nr *= 2;
            //            for (int i = 0; i < nr; i++) {
            if (dir.equals("R")) {
                x += nr;
//                xd++;
            } else if (dir.equals("L")) {
                x -= nr;
//                xd--;
            } else if (dir.equals("D")) {
                y += nr;
//                yd++;
            } else if (dir.equals("U")) {
                y -= nr;
//                yd--;
            } else {
                throw new IllegalStateException("");
            }

            len += nr;

            l.add(List.of(x,y));
//            ld.add(List.of(xd, yd));
            //            }
        }

        var lKewl = new ArrayList<List<Integer>>();
        lKewl.add(l.getFirst());

        for (int i = 2; i < l.size(); i++) {
            var x1 = l.get(i - 2).getFirst();
            var y1 = l.get(i - 2).getLast();

            var x2 = l.get(i).getFirst();
            var y2 = l.get(i).getLast();

            var dx = x2 - x1;
            var dy = y2 - y1;

            var x3 = l.get(i-1).getFirst();
            var y3 = l.get(i-1).getLast();

            if (dx > 0 && dy > 0) {
                lKewl.add(List.of(x3 + 1, y3));
            } else if (dx > 0 && dy < 0) {
                lKewl.add(List.of(x3, y3));
            } else if (dx < 0 && dy > 0) {
                lKewl.add(List.of(x3 + 1, y3 + 1));
            } else if (dx < 0 && dy < 0) {
                lKewl.add(List.of(x3, y3 + 1));
            } else {
                throw new IllegalStateException();
            }
        }

        lKewl.add(l.getLast());

//        l.remove(l.size() - 1);
//        l.add(List.of(-1, 0));
//
//        for (int i = 0; i < l.size(); i++) {
//            var a = l.get(i);
//            var b = ld.get(i);
//            System.out.println(" => %s,%s".formatted(a.getFirst()+b.getFirst(), a.getLast() + b.getLast()));
//        }

//        var ll = new ArrayList<List<Integer>>(l.size());
//        ll.add(l.get(0));
//        for (int i = 1; i < l.size(); i++) {
//            var dx = l.get(i - 1).getFirst() - l.get(i).getFirst();
//            var dy = l.get(i - 1).getLast() - l.get(i).getLast();
//
//            if (dx > 0) {
//                ll.add(List.of(l.get(i).getFirst() + 1, l.get(i-1).getLast());
//            } else if (dx < 0) {
//                ll.add(List.of(l.get(i).getFirst() - 1, l.get(i-1).getLast());
//            }
//        }

        //        l.forEach(System.out::println);

        var xMax = 0;
        var xMin = 0;
        var yMax = 0;
        var yMin = 0;
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
        //
        //        System.out.println(xMax);
        //        System.out.println(yMax);
        //
        //        for (int i = 0; i < l.size(); i++) {
        //            try {
        //                System.out.println("%s,%s -> %s".formatted(p.xpoints[i], p.ypoints[i], l.get(i)));
        //            } catch (IndexOutOfBoundsException e) {
        //
        //            }
        //        }

        l = lKewl;

        //        int result = 0;
        for (int yy = yMin; yy <= yMax; yy++) {
            for (int xx = xMin; xx <= xMax; xx++) {
                //                if (p.contains(new Point(xx, yy))) {
                //                    result++;
                ////                    System.out.print("#");
                //                } else {
                if (l.contains(List.of(xx,yy))) {
//                    System.out.println("-> %s,%s".formatted(xx, yy));
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
                //                    System.out.print(".");
                //                }
            }
            System.out.println();
        }

        long sum = 0;
        //       long sum = 0;

        l.forEach(System.out::println);

//        System.out.println(calcAreaSize(new Area(p)));
//        System.out.println(len);
        final var npoints = l.size();
        for (int i = 0; i < npoints - 1; i++) {
            final var sum1 =
                    sum + ((long) l.get(i).getFirst()) * l.get((i + 1)).getLast() - ((long) l.get(i).getLast()) * l.get((i + 1))
                            .getFirst();

//            System.out.println(" ~> " + sum1);
            sum = sum1;
        }

        System.out.println(calcAreaSize(new Area(p)));
        System.out.println("sum: %s, sum / 2: %s, len %s".formatted(sum, sum / 2, len));

        return sum / 2;
    }

    static long part2(String[] input) {
        int x = 0;
        int y = 0;
        List<List<Integer>> l = new ArrayList<>();
//        Set<Integer> lengthsY = new HashSet<>();
//        Set<Integer> lengthsX = new HashSet<>();
        l.add(List.of(0, 0));
        for (var line : input) {
            var parts = line.split(" ");
            var nr = (int) Long.parseLong(parts[2].substring(2, 2 + 5), 16);
            if (nr < 0) {
                throw new IllegalStateException();
            }
            var di = Integer.parseInt(parts[2].substring(2 + 5, 2 + 5 + 1));

//            var xOld = x;
//            var yOld = y;

            //            for (long i = 0; i < nr; i++) {
            if (di == 0) {
                x += nr;
//                lengthsX.add(Math.abs(Math.abs(xOld) - Math.abs(x)));
            } else if (di == 2) {
                x -= nr;
//                lengthsX.add(Math.abs(Math.abs(xOld) - Math.abs(x)));
            } else if (di == 1) {
                y += nr;
//                lengthsY.add(Math.abs(Math.abs(yOld) - Math.abs(y)));
            } else if (di == 3) {
                y -= nr;
//                lengthsY.add(Math.abs(Math.abs(yOld) - Math.abs(y)));
            } else {
                throw new IllegalStateException("");
            }

            l.add(List.of(x, y));
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

        //        System.out.println(xMin);
        //        System.out.println(xMax);
        //        System.out.println(yMin);
        //        System.out.println(yMax);
        //        System.out.println("---");
        //        System.out.println(gcd(lengthsX));
        //        System.out.println(gcd(lengthsY));

        //        new Area(p).

//        long sum = 0;
//        final var npoints = l.size();
//        for (int i = 0; i < npoints - 1; i++) {
//            sum = sum + ((long) l.get(i).getFirst()) * l.get((i + 1) % npoints).getLast() - ((long) l.get(i).getLast()) * l.get(
//                    (i + 1) % npoints).getFirst();
//        }
//         (sum / 2) is your area.
//        System.out.println("The area is : " + (sum / 2));

        //        var d = Areas.approxArea(p.getPathIterator(new AffineTransform()));
//        var d = calcAreaSize(new Area(p));
//        System.out.println(d);
//        return Double.valueOf(d).longValue();

                long result = 0;
                for (long yy = yMin; yy <= yMax; yy++) {
//                    System.out.println(yy / (double) yMax);
                    if (yy % 1_000 == 0) {
                        System.out.println(100*(yy / (float) yMax));
                    }
                    for (long xx = xMin; xx <= xMax; xx++) {
                        if (p.contains(xx, yy)) {
                            result++;
                        }
        ////                    System.out.print("#");
        //                } else {
        ////                    System.out.print(".");
        //                }
                    }
        //            System.out.println();
                }

                return result;
    }

    static int gcd(Collection<Integer> l) {
        var i = l.iterator();
        var a = i.next();
        System.out.println(" => " + a);
        while (i.hasNext()) {
            a = gcd(a, i.next());
            System.out.println(" => " + a);
        }

        return a;
    }

    static int gcd(int a, int b) {
        System.out.println("--> " + a);
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    static long calcAreaSize(Area area) {
        long sum = 0;
        float xBegin = 0, yBegin = 0, xPrev = 0, yPrev = 0, coords[] = new float[6];
        for (PathIterator iterator1 = area.getPathIterator(null, 1.0); !iterator1.isDone(); iterator1.next()) {
            switch (iterator1.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO:
                    xBegin = coords[0];
                    yBegin = coords[1];
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
            xPrev = coords[0];
            yPrev = coords[1];
        }
        return sum;
    }

}
