package advent_of_code_2023;

import java.util.*;
import java.util.function.Predicate;

public class DayX {

    static long part1(String[] input) {
        var connection = new HashMap<String, List<String>>();
        var flipFlopState = new HashMap<String, Boolean>();
//        var conjunction = new HashMap<String, List<String>>();
        var conjunctionState = new HashMap<String, HashMap<String, Boolean>>(); // needs to be ordered?
//        var broadcaster = new ArrayList<String>();

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

//        System.out.println(connection);
//        System.out.println(flipFlopState);
//        System.out.println(conjunctionState);

        var highCount = 0;
        var lowCount = 0;

        for (int c = 0; c < 1000; c++) {
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


                    // Flip-flop modules (prefix %) are either on or off; they are initially off.
                    // If a flip-flop module receives a high pulse, it is ignored and nothing happens.
                    // However, if a flip-flop module receives a low pulse, it flips between on and off.
                    // If it was off, it turns on and sends a high pulse. If it was on, it turns off and sends a low pulse.
                    if (flipFlopIsHigh != null) {
                        if (!isHigh) {
                            flipFlopState.put(name, !flipFlopIsHigh);
                            connections.forEach(next -> state.addLast("%s|%s|%s".formatted(next, !flipFlopIsHigh, name)));
                        }
                        // Conjunction modules (prefix &) remember the type of the most recent pulse received from each of their
                        // connected input modules; they initially default to remembering a low pulse for each input.
                        // When a pulse is received, the conjunction module first updates its memory for that input.
                        // Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse.
                    } else if (cState != null) {
                        var sender = parts[2];
                        cState.put(sender, isHigh);

                        var allHigh = cState.values().stream().reduce(true, (a, b) -> a && b);

                        connections.forEach(next -> state.addLast("%s|%s|%s".formatted(next, !allHigh, name)));
                    } else if (connections != null) {
                        connections.forEach(next -> state.addLast("%s|%s|%s".formatted(next, isHigh, name)));
                    }
                }

//                System.out.println("---");
//                System.out.println(Arrays.toString(parts));
//                System.out.println(flipFlopState);
//                System.out.println(conjunctionState);
            }
        }

        long sum = highCount * lowCount;
        System.out.println("high: %s, low: %s, sum: %s".formatted(highCount, lowCount, sum));

        return sum;
    }

    static int part2(String[] input) {
        return -1;
    }

}
