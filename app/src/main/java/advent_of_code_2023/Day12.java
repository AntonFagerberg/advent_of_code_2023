package advent_of_code_2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

// Don't use this, I hacked this and did manual calculations to get the result...
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

    static long place(List<Integer> rem, int ri, int si, String l, int ll, int rr) {
        if (si > ll) {
            return 0L;
        }

        if (si == ll) {
            return ri == rr ? 1L : 0L;
        }

        if (ri == rr) {
            for (int i = si; i < ll; i++) {
                if (l.charAt(i) == '#') {
                    return 0L;
                }
            }

            return 1L;
        }

        if (rr - ri > 2*(ll - si)) {
            return 0L;
        }


        final var c = l.charAt(si);
        int si1 = si + 1;
        if (c == '.') {
            return place(rem, ri, si1, l, ll, rr);
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
        long a = 0;
        if (canPlace) {
            int ri1 = ri + 1;
            if (i1 == ll) {
                a = place(rem, ri1, i1, l, ll, rr);// + (mustPlace ? 0L : place(rem, ri, si1, l, ll, rr));
                //                return place(rem, ri + 1, si + r, l);// + place(rem, ri, si + 1, l);
            } else if (l.charAt(i1) != '#') {
                a = place(rem, ri1, si + r + 1, l, ll, rr);// + (mustPlace ? 0L : place(rem, ri, si1, l, ll, rr));
                //                return place(rem, ri + 1, si + r + 1, l) + place(rem, ri, si + 1, l);
            }

//            if (mustPlace) {
//                return a;
//            }

//            return a + place(rem, ri, si1, l, ll, rr);
        }

        if (mustPlace) {
            return a;
        }

        return a + place(rem, ri, si1, l, ll, rr);

//        return mustPlace ? 0L : place(rem, ri, si1, l, ll, rr);

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

            var copies = 5;

            for (int i = 0; i < copies; i++) {
                numbers.addAll(nrTemp);
            }

            String part = parts[0];
            var string = "";
            for (int i = 0; i < copies-1; i++) {
                string += part + "?";
            }
            string += part;

            final var s = string;
            final int iii = ii;
            e.execute(() -> {
                var iiii = iii;
                var start = System.currentTimeMillis();
                var r = place(numbers, 0, 0, s, s.length(), numbers.size());
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
            e.awaitTermination(100, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        return result.get();
    }

    /*
    ?????????.???? 1,1,3,1
    2 -> 6878
    3 -> 1477765
    4 -> 354800406

    1477765 / 6878 = 214,8538819424
    354800406 / 1477765 = 240,09257628919

    1477765 - 6878 = 1 470 887
    354800406 - 1477765 = 353 322 641



43758*17383860
    ????????????? 1,1,1,2
    2 -> 43758
    3 -> 17383860
    4 -> 7307872110 (15min)

45 -> 2
????????????????????????????????????????????? ->     21
?????????????????????????????????????????????? ->    231
??????????????????????????????????????????????? ->   1771
???????????????????????????????????????????????? ->  10626
????????????????????????????????????????????????? -> 53130
?????????????????????????????????????????????????? -> 230230

n		a(n)
20		1
45 21		21
46 22		231
47 23		1771
48 24		10626
49 25		53130
50 26		230230
51 27		888030
52 28		3108105
53 29		10015005
54 30		30045015
55 31		84672315
56 32		225792840
57 33		573166440
58 34		1391975640
59 35		3247943160
60 36		7307872110
61 37		15905368710
62 38		33578000610
63 39		68923264410
64 40		137846528820
65 41		269128937220
66 42		513791607420
67 43		960566918220
68 44		1761039350070

bin(45,20) = 3169870830126 !!!


45 - 69 = -24
     */

    // 408) ????????????? 1,1,1,2 -> 3169870830126
    // 775) ?????????.???? 1,1,3,1
    public static void main(String[] args) {
        // ?????????????
        // ?????????????????????????????????????????????????????????????????????
        // ????????????????????????????????????????????????????????????????????? -> l=69

        // 4x -> 329280

        var s = new String[]{"?????????.???? 1,1,3,1"};
        System.out.println(part3(s));
    }

}
