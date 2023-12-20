package advent_of_code_2023;

import java.util.*;
import java.util.function.Predicate;

public class DayX {

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
        var highCount = 0;
        var lowCount = 0;

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

                if (name.equals("output")) {
                } else {
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
        }

        long sum = highCount * lowCount;
        System.out.println("high: %s, low: %s, sum: %s".formatted(highCount, lowCount, sum));

        return sum;
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

                if (name.equals("output")) { // remove?

                } else {
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
                        if (!isHigh) {
                            result = count;
                        }
                    }
                }
            }
        }

        return result;
    }

}
