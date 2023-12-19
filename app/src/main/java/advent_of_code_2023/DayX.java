package advent_of_code_2023;

import java.util.*;
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


        return -1L;
    }

}
