package advent_of_code_2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 {

    static Set<Integer> parseNumbers(String... numbers) {
        return Arrays.stream(numbers)
                .flatMap(number -> {
                    try {
                        return Stream.of(Integer.parseInt(number));
                    } catch (NumberFormatException e) {
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toSet());
    }

    static int part1(String[] input) {
        var result = 0;
        for (var line : input) {
            var parts = line.split("\\|");
            var got = parseNumbers(parts[0].split(" "));
            var win = parseNumbers(parts[1].split(" "));

            var match = got.stream()
                    .filter(win::contains)
                    .collect(Collectors.toSet())
                    .size();

            if (match > 0) {
                var j = 1;

                for (int i = 1; i < match; i++) {
                    j *= 2;
                }

                result += j;
            }

        }

        return result;
    }

    static int part2(String[] input) {
        var card = 0;
        var wins = new HashMap<Integer, Integer>();
        for (var line : input) {
            var parts = line.split("\\|");
            var got = parseNumbers(parts[0].split(" "));
            var win = parseNumbers(parts[1].split(" "));

            var match = got.stream()
                    .filter(win::contains)
                    .collect(Collectors.toSet())
                    .size();

            wins.put(card, match);

            card++;
        }

        var result = 0;

        for (var entry : wins.entrySet()) {
            result += 1;
            for (var i = 1; i <= entry.getValue(); i++) {
                result += recurse(entry.getKey() + i, wins);
            }
        }

        return result;
    }

    static int recurse(int card, Map<Integer, Integer> wins) {
        Integer nr = wins.get(card);

        if (nr == null) {
            return 0;
        }

        var result = 1;

        for (var i = 1; i <= nr; i++) {
            result += recurse(card + i, wins);
        }

        return result;
    }
}
