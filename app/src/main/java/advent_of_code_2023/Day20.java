package advent_of_code_2023;

import java.util.*;
import java.util.function.Predicate;

public class Day20 {

    static long part1(String[] input) {
        var connection = new HashMap<String, List<String>>();
        var flipFlopState = new HashMap<String, Boolean>();
        var conjunctionState = new HashMap<String, HashMap<String, Boolean>>(); // needs to be ordered?

        for (var line : input) {
            var parts = Arrays.stream(line.split("[ ,>-]"))
                    .filter(Predicate.not(String::isEmpty))
                    .toList();

            if (line.startsWith("broadcaster")) {
                for (int i = 1; i < parts.size(); i++) {
                    var l = connection.getOrDefault("broadcaster", new ArrayList<>());
                    connection.putIfAbsent("broadcaster", l);
                    l.add(parts.get(i));
                }
            } else {
                var name = parts.getFirst().substring(1);
                for (int i = 1; i < parts.size(); i++) {
                    var l = connection.getOrDefault(name, new ArrayList<>());
                    connection.putIfAbsent(name, l);
                    l.add(parts.get(i));
                }

                if (line.startsWith("%")) {
                    flipFlopState.put(name, false);
                } else {
                    for (var line2 : input) {
                        var parts2 = Arrays.stream(line2.split("[ ,>-]"))
                                .filter(Predicate.not(String::isEmpty))
                                .toList();

                        for (int i = 1; i < parts2.size(); i++) {
                            if (parts2.get(i).equals(name)) {
                                var h = conjunctionState.getOrDefault(name, new HashMap<>());
                                conjunctionState.putIfAbsent(name, h);
                                h.put(parts2.getFirst().substring(1), false);
                            }
                        }
                    }
                }
            }
        }

        var state = new LinkedList<String>();
        var highCount = 0L;
        var lowCount = 0L;

        for (int c = 1; c <= 1000; c++) {
            state.add("broadcaster|%s|button".formatted(false));

            while (!state.isEmpty()) {
                var parts = state.removeFirst().split("\\|");

                var name = parts[0];
                var isHigh = Boolean.parseBoolean(parts[1]);

                if (isHigh) {
                    highCount++;
                } else {
                    lowCount++;
                }

                var flipFlopIsHigh = flipFlopState.get(name);
                var cState = conjunctionState.get(name);
                var connections = connection.get(name);


                if (flipFlopIsHigh != null) {
                    if (!isHigh) {
                        flipFlopState.put(name, !flipFlopIsHigh);
                        connections.forEach(next -> state.addLast("%s|%s|%s".formatted(next, !flipFlopIsHigh, name)));
                    }
                } else if (cState != null) {
                    var sender = parts[2];
                    cState.put(sender, isHigh);

                    var allHigh = cState.values().stream().reduce(true, (a, b) -> a && b);

                    connections.forEach(next -> state.addLast("%s|%s|%s".formatted(next, !allHigh, name)));
                } else if (connections != null) {
                    connections.forEach(next -> state.addLast("%s|%s|%s".formatted(next, isHigh, name)));
                }
            }
        }

        return highCount * lowCount;
    }

    static long part2(String[] input) {
        var connection = new HashMap<String, List<String>>();
        var flipFlopState = new HashMap<String, Boolean>();
        var conjunctionState = new HashMap<String, HashMap<String, Boolean>>(); // needs to be ordered?

        for (var line : input) {
            var parts = Arrays.stream(line.split("[ ,>-]"))
                    .filter(Predicate.not(String::isEmpty))
                    .toList();

            if (line.startsWith("broadcaster")) {
                for (int i = 1; i < parts.size(); i++) {
                    var l = connection.getOrDefault("broadcaster", new ArrayList<>());
                    connection.putIfAbsent("broadcaster", l);
                    l.add(parts.get(i));
                }
            } else {
                var name = parts.getFirst().substring(1);
                for (int i = 1; i < parts.size(); i++) {
                    var l = connection.getOrDefault(name, new ArrayList<>());
                    connection.putIfAbsent(name, l);
                    l.add(parts.get(i));
                }

                if (line.startsWith("%")) {
                    flipFlopState.put(name, false);
                } else {
                    for (var line2 : input) {
                        var parts2 = Arrays.stream(line2.split("[ ,>-]"))
                                .filter(Predicate.not(String::isEmpty))
                                .toList();

                        for (int i = 1; i < parts2.size(); i++) {
                            if (parts2.get(i).equals(name)) {
                                var h = conjunctionState.getOrDefault(name, new HashMap<>());
                                conjunctionState.putIfAbsent(name, h);
                                h.put(parts2.getFirst().substring(1), false);
                            }
                        }
                    }
                }
            }
        }

        var keepTrack = new HashMap<String, Long>();
        keepTrack.put("rx", -1L);

        while (keepTrack.size() < 2) {
            var keySet = Set.copyOf(keepTrack.keySet());
            keepTrack.clear();
            for (var e : connection.entrySet()) {
                if (keySet.stream().anyMatch(e.getValue()::contains)) {
                    keepTrack.put(e.getKey(), -1L);
                }
            }
        }

        var state = new LinkedList<String>();
        long result = -1;
        long count = 0;
        while (result < 0) {
            count++;
            state.add("broadcaster|%s|button".formatted(false));

            while (!state.isEmpty()) {
                var parts = state.removeFirst().split("\\|");

                var name = parts[0];
                var isHigh = Boolean.parseBoolean(parts[1]);

                var flipFlopIsHigh = flipFlopState.get(name);
                var cState = conjunctionState.get(name);
                var connections = connection.get(name);


                if (flipFlopIsHigh != null) {
                    if (!isHigh) {
                        flipFlopState.put(name, !flipFlopIsHigh);
                        connections.forEach(next -> state.addLast("%s|%s|%s".formatted(next, !flipFlopIsHigh, name)));
                    }
                } else if (cState != null) {
                    var sender = parts[2];
                    cState.put(sender, isHigh);

                    var allHigh = cState.values().stream().reduce(true, (a, b) -> a && b);

                    connections.forEach(next -> state.addLast("%s|%s|%s".formatted(next, !allHigh, name)));
                } else if (connections != null) {
                    connections.forEach(next -> state.addLast("%s|%s|%s".formatted(next, isHigh, name)));
                } else if (name.equals("rx")) {

                    for (var and : keepTrack.keySet()) {
                        if (keepTrack.get(and) <= 0 && !conjunctionState.get(and).values().stream().reduce(true, (a, b) -> a && b)) {
                            keepTrack.put(and, count);
                        }
                    }

                    if (keepTrack.values().stream().noneMatch(nr -> nr <= 0)) {
                        result = lcmRec(keepTrack.values().toArray(new Long[0]));
                    }
                }
            }
        }

        return result;
    }

    public static long lcmRec(Long[] numbers) {
        if (numbers.length == 2) {
            return lcm(numbers[0], numbers[1]);
        } else {
            return lcm(numbers[0], lcmRec(Arrays.copyOfRange(numbers, 1, numbers.length)));
        }
    }

    public static long lcm(long x, long y) {
        long max = Math.max(x, y);
        long min = Math.min(x, y);
        long lcm = max;

        while (lcm % min != 0) {
            lcm += max;
        }

        return lcm;
    }

}
