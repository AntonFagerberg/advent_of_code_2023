package advent_of_code_2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day09 {

    static int part1(String[] input) {
        int result = 0;
        for (var line : input) {
            var lists = new ArrayList<List<Integer>>();
            lists.add(new ArrayList<>(Arrays.stream(line.split(" ")).map(Integer::parseInt).toList()));
            while (lists.getLast().stream().anyMatch(i -> i != 0)) {
                var current = lists.getLast();
                var newList = new ArrayList<Integer>();
                for (var i = 0; i < current.size() - 1; i++) {
                    newList.add(current.get(i + 1) - current.get(i));
                }
                lists.add(newList);
            }

            lists.getLast().add(0);

            for (var i = lists.size() - 2; i >= 0; i--) {
                var j = lists.get(i).getLast() + lists.get(i + 1).getLast();
                lists.get(i).add(j);
            }

            result += lists.getFirst().getLast();
        }
        return result;
    }

    static int part2(String[] input) {
        int result = 0;
        for (var line : input) {
            var lists = new ArrayList<List<Integer>>();
            lists.add(new ArrayList<>(Arrays.stream(line.split(" ")).map(Integer::parseInt).toList()));
            while (lists.getLast().stream().anyMatch(i -> i != 0)) {
                var current = lists.getLast();
                var newList = new ArrayList<Integer>();
                for (var i = 0; i < current.size() - 1; i++) {
                    newList.add(current.get(i) - current.get(i + 1));
                }
                lists.add(newList);
            }

            lists.getLast().addFirst(0);

            for (var i = lists.size() - 2; i >= 0; i--) {
                var j = lists.get(i).getFirst() + lists.get(i + 1).getFirst();
                lists.get(i).addFirst(j);
            }

            result += lists.getFirst().getFirst();
        }
        return result;
    }

}
