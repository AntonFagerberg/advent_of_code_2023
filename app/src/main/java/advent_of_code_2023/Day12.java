package advent_of_code_2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day12 {

    static boolean partialValid(String input, List<Integer> target) {
        var substr = input.split("\\?")[0];
        var t = Arrays.stream(substr.split("\\.+"))
                .map(String::length)
                .filter(i -> i != 0)
                .toList();

        if (t.size() > target.size()) {
            return false;
        }

        for (int i = 0; i < t.size(); i++) {
            if (i == t.size() - 1) {
                if (t.get(i) > target.get(i)) {
                    return false;
                }
            } else {
                if (!t.get(i).equals(target.get(i))) {
                    return false;
                }
            }
        }

        return true;
    }

    static boolean valid(String input, List<Integer> target) {
        var t = Arrays.stream(input.split("\\.+"))
                .map(String::length)
                .filter(i -> i != 0)
                .toList();


        if (target.size() != t.size()) {
//            System.out.println("%s -> %s".formatted(input, "size"));
            return false;
        }

        for (int i = 0; i < target.size(); i++) {
            if (!t.get(i).equals(target.get(i))) {
//                System.out.println("%s -> %s".formatted(input, "list"));
                return false;
            }
        }

//        System.out.println("%s -> %s".formatted(input, true));
        return true;
    }

    static Stream<String> permutations(Stream<String> input, int i, List<Integer> target, int max) {
        if (i == max) {
            return input;
        }

        var l = input
                .flatMap(s -> {
                    var c = s.charAt(i);
                    if (c == '?') {
                        var a = s.substring(0, i);
                        var b = s.substring(i + 1);
                        return Stream.of(a + "#" + b, a + "." + b)
                                .filter(ss -> partialValid(ss, target));
                    }

                    return Stream.of(s);
                });

        return permutations(l, i+1, target, max);
    }

    static long part1(String[] input) {
        long result = 0L;

        for (var line : input) {
            var parts = line.split(" ");
            var numbers = Arrays.stream(parts[1].split(","))
                    .map(Integer::parseInt)
                    .toList();

//            System.out.println(" => " + parts[0]);

            var r = permutations(Stream.of(parts[0]), 0, numbers, parts[0].length())
                    .filter(s -> valid(s, numbers))
                    .count();

//            System.out.println(r);
            result += r;
        }

        return result;
    }

    static long part2(String[] input) {
        long result = 0L;

        var ii = 1;
        for (var line : input) {
            var parts = line.split(" ");
            var nrTemp = Arrays.stream(parts[1].split(","))
                    .map(Integer::parseInt)
                    .toList();

            ArrayList<Integer> numbers = new ArrayList<>(nrTemp.size() *5);

            for (int i = 0; i < 5; i++) {
                numbers.addAll(nrTemp);
            }


            String part = parts[0];
            var string = "";
            for (int i = 0; i < 4; i++) {
                string += part + "?";
            }
            string += part;

            System.out.println(string);
            System.out.println(numbers);

            var r = permutations(Stream.of(string), 0, numbers, string.length())
                    .filter(s -> valid(s, numbers))
                    .count();

            System.out.println("%s / %s".formatted(ii++, input.length));
            result += r;
        }

        return result;
    }

}
