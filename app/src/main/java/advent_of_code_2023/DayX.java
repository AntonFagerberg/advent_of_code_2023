package advent_of_code_2023;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DayX {

    static int part1(String[] input) {
        boolean isWorkflow = true;

        List<Map<String, Integer>> parts = new ArrayList<>();
        Map<String, List<Function<Map<String, Integer>, String>>> workflows = new HashMap<>();

        for (var line : input) {
            if (line.isBlank()) {
                isWorkflow = false;
            } else if (isWorkflow) {
                var pieces = line.split("[{},]");

                var name = pieces[0];
                List<Function<Map<String, Integer>, String>> functions = new ArrayList<>();

                for (int i = 1; i < pieces.length - 1; i++) {
                    var ubp = pieces[i].split("[<>:]");

                    final BiFunction<Integer, Integer, Boolean> comparator;
                    if (pieces[i].contains(">")) {
                        comparator = (a, b) -> a > b;
                    } else if (pieces[i].contains("<")) {
                        comparator = (a, b) -> a < b;
                    } else {
                        throw new IllegalStateException();
                    }

                    functions.add(
                            map -> {
                                if (comparator.apply(map.get(ubp[0]), Integer.parseInt(ubp[1]))) {
                                    return ubp[2];
                                }

                                return null;
                            }
                    );
                }

                functions.add(m -> pieces[pieces.length - 1]);

                workflows.put(name, functions);
            } else {
                var pieces = line.split("[{},=]");
                var m = new HashMap<String, Integer>();
                for (var i = 2; i < pieces.length; i += 2) {
                    m.put(pieces[i - 1], Integer.parseInt(pieces[i]));
                }
                parts.add(m);
            }
        }

        var accepted = new ArrayList<Map<String, Integer>>();

        for (var part : parts) {
            var w = workflows.get("in");
            while (w != null) {
                for (var f : w) {
                    var r = f.apply(part);
                    if (r != null) {
                        if (r.equals("A")) {
                            accepted.add(part);
                            w = null;
                            break;
                        } else if (r.equals("R")) {
                            w = null;
                            break;
                        } else {
                            w = workflows.get(r);
                            break;
                        }
                    }
                }
            }
        }

        return accepted.stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .reduce(0, Integer::sum);
    }

    static long part2(String[] input) {
        boolean isWorkflow = true;

        Map<String, List<Map.Entry<String, String>>> workflows = new HashMap<>();

        for (var line : input) {
            if (line.isBlank()) {
                isWorkflow = false;
            } else if (isWorkflow) {
                var pieces = line.split("[{},]");

                var name = pieces[0];
                var list = workflows.getOrDefault(name, new ArrayList<>());
                workflows.putIfAbsent(name, list);

                for (int i = 1; i < pieces.length - 1; i++) {
                    var p = pieces[i].split(":");
                    list.add(Map.entry(p[0], p[1]));
                }

                list.add(Map.entry("", pieces[pieces.length - 1]));

            }
        }

        //        System.out.println(workflows);
        var conds = new ArrayList<Condition>();
        final var in = dag(workflows, "in", new ArrayList<>());
//                in.forEach(System.out::println);
        for (var d : in) {
            var c = new Condition();
            for (var i : d) {
                c.update(i);
            }

            conds.add(c);
            //
            //            long x = c.limits.get("xmax") - c.limits.get("xmin");
            //            long m = c.limits.get("mmax") - c.limits.get("mmin");
            //            long a = c.limits.get("amax") - c.limits.get("amin");
            //            long s = c.limits.get("smax") - c.limits.get("smin");
            //
            //            //            System.out.println(x);
            //            //            System.out.println(m);
            //            //            System.out.println(a);
            //            //            System.out.println(s);
            //            //            System.out.println("---");
            //
            //            if (x > 0 && m > 0 && a > 0 && s > 0) {
            //                //                System.out.println(" OK:\n" + c);
            //                conds.add(c);
            //            } else {
            //                                System.out.println("NOK:\n" + c);
            //            }
        }

//        System.out.println(in.size());
//        System.out.println(conds.size());

//        conds.forEach(System.out::println);

        long result = 0;

        for (int i = 0; i < conds.size(); i++) {
            var c = conds.get(i);
            final var xmax = c.limits.get("xmax");
            final var xmin = c.limits.get("xmin");
            final var mmax = c.limits.get("mmax");
            final var mmin = c.limits.get("mmin");
            final var amax = c.limits.get("amax");
            final var amin = c.limits.get("amin");
            final var smax = c.limits.get("smax");
            final var smin = c.limits.get("smin");

            long x = xmax - xmin - 1;
            long m = mmax - mmin - 1;
            long a = amax - amin - 1;
            long s = smax - smin - 1;

            final var prod = x * m * a * s;
            if (prod <= 0) {
                System.out.println(c);
                System.out.println(x);
                System.out.println(m);
                System.out.println(a);
                System.out.println(s);
                System.out.println(prod);
                throw new IllegalStateException();
            }
            result += prod;

            // This was completely unnecessary :(
            for (var ii = i + 1; ii < conds.size(); ii++) {
                var cc = conds.get(ii);
                final var cxmax = cc.limits.get("xmax");
                final var cxmin = cc.limits.get("xmin");
                final var cmmax = cc.limits.get("mmax");
                final var cmmin = cc.limits.get("mmin");
                final var camax = cc.limits.get("amax");
                final var camin = cc.limits.get("amin");
                final var csmax = cc.limits.get("smax");
                final var csmin = cc.limits.get("smin");

                //                long xIntersect = 0L;
                //
                //                if (cxmax >= xmin && cxmax <= xmax) {
                //                    xIntersect = xmax - cxmax;
                //                } else if (cxmin >= xmin && cxmin <= xmax) {
                //                    xIntersect = xmax - cxmin;
                //                }
                //
                //                long mIntersect = 0L;
                //
                //                if (cmmax >= mmin && cmmax <= mmax) {
                //                    mIntersect = mmax - cmmax;
                //                } else if (cmmin >= mmin && cmmin <= mmax) {
                //                    mIntersect = mmax - cmmin;
                //                }
                //
                //                long aIntersect = 0L;
                //
                //                if (camax >= amin && camax <= amax) {
                //                    aIntersect = amax - camax;
                //                } else if (camin >= amin && camin <= amax) {
                //                    aIntersect = amax - camin;
                //                }
                //
                //                long sIntersect = 0L;
                //
                //                if (csmax >= smin && csmax <= smax) {
                //                    sIntersect = smax - csmax;
                //                } else if (csmin >= smin && csmin <= smax) {
                //                    sIntersect = smax - csmin;
                //                }

                long xIntersect = Math.max(intersect(xmin, xmax, cxmin, cxmax) - 1, 0);
                long mIntersect = Math.max(intersect(mmin, mmax, cmmin, cmmax) - 1, 0);
                long aIntersect = Math.max(intersect(amin, amax, camin, camax) - 1, 0);
                long sIntersect = Math.max(intersect(smin, smax, csmin, csmax) - 1, 0);

                final var overlap = xIntersect * mIntersect * aIntersect * sIntersect;


                if (overlap > 0) {
                    System.out.println(c);
                    System.out.println(cc);
                    System.out.println(" => " + xIntersect);
                    System.out.println(" => " + mIntersect);
                    System.out.println(" => " + aIntersect);
                    System.out.println(" => " + sIntersect);

                    System.out.println(" ?> " + overlap);
                    System.out.println("---");

                    result -= overlap;
                }

                //                result -= overlap;

                //                                System.out.println("---");
            }

            if (result < 0) {
                throw new IllegalStateException();
            }

        }

        System.out.println("Result: " + result);
        System.out.println("Diff: " + (result - 167409079868000L));

        //        for (var cc : conds) {
        //            if (cc.limits.get("xmax") <= cc.limits.get("xmin")) {
        //                System.out.println(cc);
        //            }
        //            if (cc.limits.get("mmax") <= cc.limits.get("mmin")) {
        //                System.out.println(cc);
        //            }
        //            if (cc.limits.get("amax") <= cc.limits.get("amin")) {
        //                System.out.println(cc);
        //            }
        //            if (cc.limits.get("smax") <= cc.limits.get("smin")) {
        //                System.out.println(cc);
        //            }
        //        }

        //        conds.forEach(System.out::println);

        return result;

        //        Map<String, Integer> limits = new HashMap<>(
        //                Map.of(
        //                        "xmax", 0,
        //                        "xmin", 4000,
        //                        "mmax", 0,
        //                        "mmin", 4000,
        //                        "amax", 0,
        //                        "amin", 4000,
        //                        "smax", 0,
        //                        "smin", 400
        //                ));
        //
        //        for (var c : conds) {
        //            for (var e : c.limits.entrySet()) {
        //                if (e.getKey().contains("max")) {
        //                    limits.put(e.getKey(), Math.max(limits.get(e.getKey()), e.getValue()));
        //                } else {
        //                    limits.put(e.getKey(), Math.min(limits.get(e.getKey()), e.getValue()));
        //                }
        //            }
        //        }
        //
        //        limits.entrySet().forEach(System.out::println);
        //
        //        long x = limits.get("xmax") - limits.get("xmin");
        //        long m = limits.get("mmax") - limits.get("mmin");
        //        long a = limits.get("amax") - limits.get("amin");
        //        long s = limits.get("smax") - limits.get("smin");
        //
        //        return x*m*a*s;
    }

    static int intersect(int minA, int maxA, int minB, int maxB) {
        var r = Math.min(maxA, maxB) - Math.max(minA, minB);
        return Math.max(r, 0);

        //        if (minA <= maxB && minA >= minB) {
        //            return Math.min(maxB, maxA) - minA;
        //        }
        //
        //        if (maxA <= maxB && maxA >= minB) {
        //            return maxA - Math.max(minA, minB);
        //        }
        //
        //        return 0;

        //        if (cxmax >= xmin && cxmax <= xmax) {
        //
        //            xIntersect = xmax - cxmax;
        //        } else if (cxmin >= xmin && cxmin <= xmax) {
        //            xIntersect = xmax - cxmin;
        //        }
    }

    static class Condition {

        Map<String, Integer> limits = new HashMap<>(
                Map.of(
                        "xmax", 4001,
                        "xmin", 0,
                        "mmax", 4001,
                        "mmin", 0,
                        "amax", 4001,
                        "amin", 0,
                        "smax", 4001,
                        "smin", 0
                ));

        void update(String s) {
            var p = s.split("[<>]");
            final var maxKey = p[0] + "max";
            final var minKey = p[0] + "min";
            final int v = Integer.parseInt(p[1]);

            if (s.contains("<")) {
                limits.put(maxKey, Math.min(limits.get(maxKey), v));
            } else if (s.contains(">")) {
                limits.put(minKey, Math.max(limits.get(minKey), v));
            } else {
                throw new IllegalStateException();
            }
        }

        @Override
        public String toString() {
            return """
                    %s > x > %s
                    %s > m > %s
                    %s > a > %s
                    %s > s > %s
                    """.formatted(limits.get("xmax"), limits.get("xmin"),
                    limits.get("mmax"), limits.get("mmin"),
                    limits.get("amax"), limits.get("amin"),
                    limits.get("smax"), limits.get("smin"));
        }
    }

    static List<List<String>> dag(Map<String, List<Map.Entry<String, String>>> workflows, String current, List<String> acc) {
        if (current.equals("A")) {
            return List.of(acc);
        } else if (current.equals("R")) {
            return List.of();
        }

        var actions = workflows.get(current);
        var result = new ArrayList<List<String>>();

        var flipAcc = new ArrayList<>(acc);
        for (var action : actions) {
            if (action.getKey().isEmpty()) {
                result.addAll(dag(workflows, action.getValue(), flipAcc));
            } else {
                var a = new ArrayList<>(flipAcc);
                a.add(action.getKey());
                result.addAll(dag(workflows, action.getValue(), a));

                var flip = action.getKey();
                if (flip.contains(">")) {
                    var f = flip.split(">");
                    // s > 10 -> s < 11
                    flip = f[0] + "<" + (Integer.parseInt(f[1]) + 1);
                } else {
                    // s < 10 -> s > 9
                    var f = flip.split("<");
                    flip = f[0] + ">" + (Integer.parseInt(f[1]) - 1);
                }

                flipAcc.add(flip);
            }

            //            var ap = new ArrayList<>(acc);
            //            if (!action.getKey().isEmpty()) {
            //                ap.add(action.getKey());
            //            }
            //            result.addAll(dag(workflows, action.getValue(), ap));
        }

        return result;
    }

}
