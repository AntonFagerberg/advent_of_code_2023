package advent_of_code_2023;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

// This solution is a mess, and it's slooooow (it's not the right approach) - but it worked so moving on... ¯\_(ツ)_/¯
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

        return permutations(l, i + 1, target, max);
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

            ArrayList<Integer> numbers = new ArrayList<>(nrTemp.size() * 5);

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

    static Map<String, Long> cache = new HashMap<>();

    static long place(List<Integer> rem, int ri, int si, String l, int ll, int rr, List<Integer> sums) {
        var cKey = "%s|%s|%s|%s".formatted(rem, si, ri, l);

        var cV = cache.get(cKey);

        if (cV != null) {
            return cV;
        }

        if (si > ll) {
            return 0L;
        }

        if (si == ll) {
            var xxx =  ri == rr ? 1L : 0L;
            cache.put(cKey, xxx);
            return xxx;
        }

        if (ri == rr) {
            for (int i = si; i < ll; i++) {
                if (l.charAt(i) == '#') {
                    cache.put(cKey, 0L);
                    return 0L;
                }
            }

            cache.put(cKey, 1L);
            return 1L;
        }

        if (sums.get(ri) > ll - si + 1) {
            cache.put(cKey, 0L);
            return 0L;
        }

        // look ahead - does not need to be recalculated...
//        boolean seenDot = false;
//        var ii = 0;
//        for (int i = si; i < ll; i++) {
//            if (!seenDot) {
//                if (l.charAt(i) != '#') {
//                    seenDot = true;
//                }
//            } else {
//                if (l.charAt(i) == '#') {
//                    ii++;
//                    seenDot = false;
//                }
//            }
//
//            if (ii > rr - ri) {
//                return 0L;
//            }
//        }


        final var c = l.charAt(si);
        int si1 = si + 1;
        if (c == '.') {
            return place(rem, ri, si1, l, ll, rr, sums);
        }

        var r = rem.get(ri);

        int i1 = si + r;

        if (i1 > ll) {
            return 0L;
        }

        var mustPlace = c == '#';

        var canPlace = true;
        for (int i = 0; i < r; i++) {
            canPlace = l.charAt(si + i) != '.';
            if (!canPlace) {
                break;
            }
        }

        //        var res = 0L;
        var rrr = 0L;
        if (canPlace) {
            int ri1 = ri + 1;
            if (i1 == ll) {
                rrr = place(rem, ri1, i1, l, ll, rr, sums);// + (mustPlace ? 0L : place(rem, ri, si1, l, ll, rr, sums));
                //                return place(rem, ri + 1, si + r, l);// + place(rem, ri, si + 1, l);
            } else if (l.charAt(i1) != '#') {
                rrr = place(rem, ri1, si + r + 1, l, ll, rr, sums);/// + (mustPlace ? 0L : place(rem, ri, si1, l, ll, rr, sums));
                //                return place(rem, ri + 1, si + r + 1, l) + place(rem, ri, si + 1, l);
            }
        }

        if (mustPlace) {
            cache.put(cKey, rrr);
            return rrr;
        }

        var rk = rrr + place(rem, ri, si1, l, ll, rr, sums);
        cache.put(cKey, rk);
        return rk;



//        return mustPlace ? 0L : place(rem, ri, si1, l, ll, rr, sums);

        //        if (mustPlace) {
        //            return res;
        //        }

        //        return res + place(rem, ri, si + 1, l);
    }

    static long part3(String[] input) {
        final var result = new AtomicLong(0L);
        final var done = new AtomicInteger(0);

        var e = Executors.newVirtualThreadPerTaskExecutor();

        int ii = 0;
        for (var line : input) {

            var parts = line.split(" ");
            var nrTemp = Arrays.stream(parts[1].split(","))
                    .map(Integer::parseInt)
                    .toList();

            ArrayList<Integer> numbers = new ArrayList<>(nrTemp.size() * 5);
            ArrayList<Integer> sums = new ArrayList<>(nrTemp.size() * 5);

            for (int i = 0; i < 5; i++) {
                numbers.addAll(nrTemp);
            }

            var sumz = 0;
            for (var n : numbers.reversed()) {
                sumz += n;
                sums.add(sumz);
            }

            String part = parts[0];
            var string = "";
            for (int i = 0; i < 4; i++) {
                string += part + "?";
            }
            string += part;

            final var s = string;
            final int iii = ii;
            e.execute(() -> {
                var iiii = iii;
                var start = System.currentTimeMillis();
                var r = place(numbers, 0, 0, s, s.length(), numbers.size(), sums.reversed());
                var i = done.incrementAndGet();
                var l = result.addAndGet(r);
                System.out.printf("%s of %s done %s = %s (%s) %s%n", i, input.length, iiii, r, l,
                        (System.currentTimeMillis() - start) / (60 * 1000));
            });

            ii++;

            //            System.out.println(r);
            //
            //            System.out.println("%s / %s: %s".formatted(ii++, input.length, (System.currentTimeMillis() - start) / 60000));
            //            System.out.println("");
            //            result += r;
        }

        try {
            e.shutdown();
            e.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        return result.get();
    }

}
