package advent_of_code_2023;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Day19 {

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

                    functions.add(map -> comparator.apply(map.get(ubp[0]), Integer.parseInt(ubp[1])) ? ubp[2] : null);
                }

                functions.add(nope -> pieces[pieces.length - 1]);

                workflows.put(name, functions);
            } else {
                var pieces = line.split("[{},=]");
                var map = new HashMap<String, Integer>();
                for (var i = 2; i < pieces.length; i += 2) {
                    map.put(pieces[i - 1], Integer.parseInt(pieces[i]));
                }
                parts.add(map);
            }
        }

        var accepted = new ArrayList<Map<String, Integer>>();

        for (var part : parts) {
            var workflow = workflows.get("in");
            while (workflow != null) {
                for (var function : workflow) {
                    var target = function.apply(part);
                    if (target != null) {
                        if (target.equals("A")) {
                            accepted.add(part);
                            workflow = null;
                            break;
                        } else if (target.equals("R")) {
                            workflow = null;
                            break;
                        } else {
                            workflow = workflows.get(target);
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

        var conditions = new ArrayList<Condition>();
        for (var node : dag(workflows, "in", new ArrayList<>())) {
            var condition = new Condition();
            node.forEach(condition::update);
            conditions.add(condition);
        }

        long result = 0;

        for (int i = 0; i < conditions.size(); i++) {
            var condition = conditions.get(i);
            final var xmax = condition.limits.get("xmax");
            final var xmin = condition.limits.get("xmin");
            final var mmax = condition.limits.get("mmax");
            final var mmin = condition.limits.get("mmin");
            final var amax = condition.limits.get("amax");
            final var amin = condition.limits.get("amin");
            final var smax = condition.limits.get("smax");
            final var smin = condition.limits.get("smin");

            long x = xmax - xmin - 1;
            long m = mmax - mmin - 1;
            long a = amax - amin - 1;
            long s = smax - smin - 1;

            result += x * m * a * s;

            // This was completely unnecessary :(
            //            for (var ii = i + 1; ii < conditions.size(); ii++) {
            //                var otherCondition = conditions.get(ii);
            //                var cxmax = otherCondition.limits.get("xmax");
            //                var cxmin = otherCondition.limits.get("xmin");
            //                var cmmax = otherCondition.limits.get("mmax");
            //                var cmmin = otherCondition.limits.get("mmin");
            //                var camax = otherCondition.limits.get("amax");
            //                var camin = otherCondition.limits.get("amin");
            //                var csmax = otherCondition.limits.get("smax");
            //                var csmin = otherCondition.limits.get("smin");
            //
            //                long xIntersect = Math.max(intersect(xmin, xmax, cxmin, cxmax) - 1, 0);
            //                long mIntersect = Math.max(intersect(mmin, mmax, cmmin, cmmax) - 1, 0);
            //                long aIntersect = Math.max(intersect(amin, amax, camin, camax) - 1, 0);
            //                long sIntersect = Math.max(intersect(smin, smax, csmin, csmax) - 1, 0);
            //
            //                var overlap = xIntersect * mIntersect * aIntersect * sIntersect;
            //
            //                if (overlap > 0) {
            //                    result -= overlap;
            //                }
            //            }

        }

        return result;
    }

    static int intersect(int minA, int maxA, int minB, int maxB) {
        return Math.max(Math.min(maxA, maxB) - Math.max(minA, minB), 0);
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
                var newAcc = new ArrayList<>(flipAcc);
                newAcc.add(action.getKey());
                result.addAll(dag(workflows, action.getValue(), newAcc));

                var flip = action.getKey();
                if (flip.contains(">")) {
                    var f = flip.split(">");
                    // s > 10 -> s < 11
                    flip = f[0] + "<" + (Integer.parseInt(f[1]) + 1);
                } else {
                    var f = flip.split("<");
                    // s < 10 -> s > 9
                    flip = f[0] + ">" + (Integer.parseInt(f[1]) - 1);
                }

                flipAcc.add(flip);
            }
        }

        return result;
    }

}
