package advent_of_code_2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
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

    static long place(List<Integer> rem, int ri, int si, String l) {
        if (si > l.length()) {
            return 0L;
        }

        if (si == l.length()) {
            return ri == rem.size() ? 1L : 0L;
        }

        if (ri == rem.size()) {
            for (int i = si; i < l.length(); i++) {
                if (l.charAt(i) == '#') {
                    return 0L;
                }
            }

            return 1L;
        }

        var r = rem.get(ri);

        if (si + r > l.length()) {
            return 0L;
        }

        final var c = l.charAt(si);
        var mustPlace = c == '#';

        var canPlace = true;
        for (int i = 0; i < r; i++) {
            canPlace = canPlace && l.charAt(si + i) != '.';
        }

//        var res = 0L;
        if (canPlace) {
            if (si + r == l.length()) {
                return place(rem, ri + 1, si + r, l) + (mustPlace ? 0L : place(rem, ri, si + 1, l));
//                return place(rem, ri + 1, si + r, l);// + place(rem, ri, si + 1, l);
            } else if (l.charAt(si + r) != '#') {
                return place(rem, ri + 1, si + r + 1, l) + (mustPlace ? 0L : place(rem, ri, si + 1, l));
                //                return place(rem, ri + 1, si + r + 1, l) + place(rem, ri, si + 1, l);
            }
        }

        return mustPlace ? 0L : place(rem, ri, si + 1, l);

//        if (mustPlace) {
//            return res;
//        }

//        return res + place(rem, ri, si + 1, l);
    }

    static long part3(String[] input) {
        final var result = new AtomicLong(0L);
        final var done = new AtomicInteger(0);

        var ii = 1;

        var e = Executors.newVirtualThreadPerTaskExecutor();

        for (var line : input) {
            var start = System.currentTimeMillis();
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

            final var s = string;
            e.execute(() -> {
                var r = place(numbers, 0, 0, s);
                result.addAndGet(r);
                System.out.printf("%s of %s done%n", done.incrementAndGet(), input.length);
            });

//            System.out.println(r);
//
//            System.out.println("%s / %s: %s".formatted(ii++, input.length, (System.currentTimeMillis() - start) / 60000));
//            System.out.println("");
//            result += r;
        }

        try {
            e.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        return result.get();
    }

}
