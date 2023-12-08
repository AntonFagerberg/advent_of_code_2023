package advent_of_code_2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day08 {

    static int part1(String[] input) {
        var movement = input[0];
        var m = new HashMap<String, List<String>>();
        for (int i = 2; i < input.length; i++) {
            var parts = input[i].split("[ =,()]+");
            m.put(parts[0], List.of(parts[1], parts[2]));
        }
        int i = 0;
        var current = "AAA";

        while (!current.equals("ZZZ")) {
            var options = m.get(current);
            int l = i % movement.length();
            if (movement.charAt(l) == 'L') {
                current = options.get(0);
            } else if (movement.charAt(l) == 'R') {
                current = options.get(1);
            } else {
                throw new IllegalStateException(":/");
            }
            i++;
        }

        return i;
    }

    static long part2(String[] input) {
        var movement = Arrays.stream(input[0].split(""))
                .map(s -> s.equals("L") ? 0 : 1)
                .toList();

        var m = new HashMap<String, List<String>>();
        for (int i = 2; i < input.length; i++) {
            var parts = input[i].split("[ =,()]+");
            m.put(parts[0], List.of(parts[1], parts[2]));
        }
        long i = 0;
        int l = 0;

        var current = m.keySet().stream().filter(s -> s.endsWith("A")).toList();
        var loops = new HashMap<String, Long>();

        while (loops.size() != current.size()) {
            var nextCurrent = new ArrayList<String>(current.size());

            for (String c : current) {
                if (c.endsWith("Z")) {
                    var key = "%s|%s".formatted(c, l);
                    loops.putIfAbsent(key, i);
                }

                nextCurrent.add(m.get(c).get(movement.get(l)));
            }

            current = nextCurrent;

            i++;

            l = (int) (i % movement.size());
        }

        Set<Long> values = new HashSet<>(loops.values());
        // My solution (which worked...) before looking up the lcm algorithm
        //        var min = values.stream().min(Long::compareTo).get();
        //        var ii = min;
        //
        //        while (true) {
        //            final var iii = ii;
        //            if (values.stream().allMatch(x -> iii % x == 0)) {
        //                break;
        //            }
        //
        //            ii += min;
        //        }
        //        return ii;

        final Long[] array = values.toArray(new Long[0]);
        return lcmRec(array);
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
