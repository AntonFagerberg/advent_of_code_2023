package advent_of_code_2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public class Day03 {

    static int part1(String[] input) {
        BiFunction<Integer, Integer, Optional<Integer>> getDigit = (line, index) -> {
            try {
                return Optional.of(Integer.parseInt(String.valueOf(input[line].charAt(index))));
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                return Optional.empty();
            }
        };

        BiFunction<Integer, Integer, Optional<Character>> getPart = (line, index) -> {
            try {
                var part = input[line].charAt(index);

                if (getDigit.apply(line, index).isPresent() || part == '.') {
                    return Optional.empty();
                }

                return Optional.of(part);

            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                return Optional.empty();
            }
        };

        var result = 0;

        for (int line = 0; line < input.length; line++) {
            var number = "";
            Optional<Character> adjacent = Optional.empty();

            for (var index = 0; index < input[line].length(); index++) {
                var digit = getDigit.apply(line, index);

                if (digit.isPresent()) {
                    number += digit.get();

                    for (var i = line - 1; i <= line + 1; i++) {
                        for (var j = index - 1; j <= index + 1; j++) {
                            if (adjacent.isEmpty()) {
                                adjacent = getPart.apply(i, j);
                            }
                        }
                    }
                } else {
                    if (adjacent.isPresent()) {
                        result += Integer.parseInt(number);
                    }

                    number = "";
                    adjacent = Optional.empty();
                }

                if (index == input[line].length() - 1) {
                    if (adjacent.isPresent()) {
                        result += Integer.parseInt(number);
                    }

                    number = "";
                    adjacent = Optional.empty();
                }
            }
        }

        return result;
    }

    static int part2(String[] input) {
        BiFunction<Integer, Integer, Optional<Integer>> getDigit = (line, index) -> {
            try {
                return Optional.of(Integer.parseInt(String.valueOf(input[line].charAt(index))));
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                return Optional.empty();
            }
        };

        BiFunction<Integer, Integer, Optional<Character>> getPart = (line, index) -> {
            try {
                var part = input[line].charAt(index);

                if (getDigit.apply(line, index).isPresent() || part == '.') {
                    return Optional.empty();
                }

                return Optional.of(part);

            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                return Optional.empty();
            }
        };

        var map = new HashMap<String, List<Integer>>();

        for (int line = 0; line < input.length; line++) {
            var number = "";
            var key = "";
            Optional<Character> adjacent = Optional.empty();

            for (var index = 0; index < input[line].length(); index++) {
                var digit = getDigit.apply(line, index);

                if (digit.isPresent()) {
                    number += digit.get();

                    for (var i = line - 1; i <= line + 1; i++) {
                        for (var j = index - 1; j <= index + 1; j++) {
                            if (adjacent.isEmpty()) {
                                adjacent = getPart.apply(i, j).filter(c -> c == '*');

                                key = "%s,%s".formatted(i, j);
                                if (adjacent.isPresent() && !map.containsKey(key)) {
                                    map.put(key, new ArrayList<>());
                                }
                            }
                        }
                    }
                } else {
                    if (adjacent.isPresent()) {
                        map.get(key).add(Integer.parseInt(number));
                    }

                    number = "";
                    key = "";
                    adjacent = Optional.empty();
                }

                if (index == input[line].length() - 1) {
                    if (adjacent.isPresent()) {
                        map.get(key).add(Integer.parseInt(number));
                    }

                    number = "";
                    key = "";
                    adjacent = Optional.empty();
                }
            }
        }

        return map.values().stream()
                .filter(l -> l.size() == 2)
                .map(l -> l.stream().reduce(1, (a, b) -> a * b))
                .reduce(0, Integer::sum);
    }
}
