package advent_of_code_2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

public class Day05 {

    static class Range {
        long from;
        long to;
        long offset;

        @Override
        public String toString() {
            return "Range{" +
                    "from=" + from +
                    ", to=" + to +
                    ", offset=" + offset +
                    '}';
        }
    }

    static class Item {
        String from;
        String to;
        List<Range> ranges = new ArrayList<>();

        long get(long input) {
            for (var range : ranges) {
                if (input >= range.from && input < range.from + range.offset) {
                    return input + (range.to - range.from);
                }
            }

            return input;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", range=" + ranges +
                    '}';
        }
    }

    static List<Item> parse(String[] input) {
        var items = new ArrayList<Item>();
        var current = new Item();

        for (var i = 2; i < input.length; i++) {
            if (input[i].isBlank()) {

            } else if (input[i].contains("-")) {
                current = new Item();
                var parts = input[i].split("[- ]");
                current.from = parts[0];
                current.to = parts[2];
                items.add(current);
            } else {
                var parts = Arrays.stream(input[i].split(" "))
                        .map(Long::parseLong)
                        .toList();

                var range = new Range();
                range.from = parts.get(1);
                range.to = parts.get(0);
                range.offset = parts.get(2);

                current.ranges.add(range);
            }
        }

        return items;
    }

    static long part1(String[] input) {
        var seeds = Arrays.stream(input[0].split("(seeds:)? "))
                .filter(Predicate.not(String::isBlank))
                .map(Long::parseLong)
                .toList();

        var items = parse(input);

        var min = Long.MAX_VALUE;
        for (var seed : seeds) {
            for (var l : items) {
                seed = l.get(seed);
            }

            if (seed < min) {
                min = seed;
            }
        }

        return min;
    }

    static long part2(String[] input) throws InterruptedException {
        var seeds = Arrays.stream(input[0].split("(seeds:)? "))
                .filter(Predicate.not(String::isBlank))
                .map(Long::parseLong)
                .toList();

        var items = parse(input);

        final var min = new AtomicLong(Long.MAX_VALUE);

        try (var executors = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < seeds.size(); i += 2) {
                final var ii = i;
                executors.execute(() -> {
                    for (var j = seeds.get(ii); j < seeds.get(ii) + seeds.get(ii + 1); j++) {
                        long seed = j;
                        for (var l : items) {
                            seed = l.get(seed);
                        }

                        var success = false;
                        while (!success) {
                            var l = min.get();
                            if (seed < l) {
                                success = min.compareAndSet(l, seed);
                            } else {
                                success = true;
                            }
                        }
                    }
                });
            }

            executors.shutdown();
            executors.awaitTermination(1, TimeUnit.DAYS);
        }

        return min.get();
    }

}
