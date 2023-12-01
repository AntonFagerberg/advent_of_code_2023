package advent_of_code_2023;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day01 {

    static int part1(String[] lines) {
        var res = 0;
        for (var line : lines) {
            var numbers = Arrays.stream(line.split(""))
                    .flatMap(s -> {
                        try {
                            return Stream.of(Integer.parseInt(s));
                        } catch (NumberFormatException e) {
                            return Stream.empty();
                        }
                    })
                    .toList();

            res += Integer.parseInt("%s%s".formatted(numbers.getFirst(), numbers.getLast()));
        }

        return res;
    }

    static int part2(String[] lines) {
        var res = 0;
        var target = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9",
                "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

        for (var line : lines) {
            var min = -1;
            var max = -1;

            for (int i = 0; i < line.length(); i++) {
                for (var t : target) {
                    int endIndex = i + t.length();
                    if (endIndex <= line.length()) {
                        String substring = line.substring(i, endIndex);
                        if (substring.equals(t)) {
                            if (min == -1) {
                                min = 1 + (target.indexOf(substring) % 9);
                            }
                            max = 1 + (target.indexOf(substring) % 9);
                        }
                    }
                }
            }

            res += Integer.parseInt("%s%s".formatted(min, max));
        }

        return res;
    }

}
