package advent_of_code_2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day06 {

    static int part1(String[] input) {
        var time = Arrays.stream(input[0].split(" +"))
                .flatMap(i -> {
                    try {
                        return Stream.of(Integer.parseInt(i));
                    } catch (NumberFormatException e) {
                        return Stream.empty();
                    }
                }).toList();

        var distance = Arrays.stream(input[1].split(" +"))
                .flatMap(i -> {
                    try {
                        return Stream.of(Integer.parseInt(i));
                    } catch (NumberFormatException e) {
                        return Stream.empty();
                    }
                }).toList();

        var res = new ArrayList<Integer>();
        for (int i = 0; i < time.size(); i++) {
            var t = time.get(i);
            var d = distance.get(i);
            var r = 0;
            for (int j = 1; j < t; j++) {
                if (j * (t - j) > d) {
                    r++;
                }
            }
            res.add(r);
        }

        return res.stream().reduce(1, (i, j) -> i * j);
    }

    static int part2(String[] input) {
        var time = Arrays.stream(input[0].split(" +"))
                .flatMap(i -> {
                    try {
                        return Stream.of(Integer.parseInt(i));
                    } catch (NumberFormatException e) {
                        return Stream.empty();
                    }
                })
                .map(Object::toString)
                .collect(Collectors.joining(""));

        var distance = Arrays.stream(input[1].split(" +"))
                .flatMap(i -> {
                    try {
                        return Stream.of(Integer.parseInt(i));
                    } catch (NumberFormatException e) {
                        return Stream.empty();
                    }
                })
                .map(Object::toString)
                .collect(Collectors.joining(""));

        var res = new ArrayList<Integer>();
        var t = Long.parseLong(time);
        var d = Long.parseLong(distance);
        var r = 0;
        for (long j = 1; j < t; j++) {
            if (j * (t - j) > d) {
                r++;
            }
        }
        res.add(r);

        return res.stream().reduce(1, (i, j) -> i * j);
    }

}
