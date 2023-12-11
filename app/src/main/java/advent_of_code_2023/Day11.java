package advent_of_code_2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day11 {

    static long solve(String[] input, int expand) {
        expand -= 1;

        var xEmpty = new HashSet<Integer>();
        var yEmpty = new HashSet<Integer>();

        for (int y = 0; y < input.length; y++) {
            boolean allXEmpty = true;
            for (int x = 0; x < input[y].length(); x++) {
                if (input[y].charAt(x) == '#') {
                    allXEmpty = false;
                    break;
                }
            }

            if (allXEmpty) {
                yEmpty.add(y);
            }
        }

        for (int x = 0; x < input[0].length(); x++) {
            boolean allYEmpty = true;
            for (String s : input) {
                if (s.charAt(x) == '#') {
                    allYEmpty = false;
                    break;
                }
            }

            if (allYEmpty) {
                xEmpty.add(x);
            }
        }

        var galaxies = new ArrayList<List<Integer>>();

        for (int x = 0; x < input[0].length(); x++) {
            for (int y = 0; y < input.length; y++) {
                if (input[y].charAt(x) == '#') {
                    galaxies.add(List.of(x, y));
                }
            }
        }

        long sum = 0;
        for (int i = 0; i < galaxies.size() - 1; i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                int x1 = galaxies.get(i).get(0);
                int x2 = galaxies.get(j).get(0);

                int y1 = galaxies.get(i).get(1);
                int y2 = galaxies.get(j).get(1);

                int xDiff = Math.abs(x1 - x2);
                int yDiff = Math.abs(y1 - y2);

                int xMax = Math.max(x1, x2);
                int xMin = Math.min(x1, x2);

                int yMax = Math.max(y1, y2);
                int yMin = Math.min(y1, y2);

                for (var xx : xEmpty) {
                    if (xx > xMin && xx < xMax) {
                        sum += expand;
                    }
                }

                for (var yy : yEmpty) {
                    if (yy > yMin && yy < yMax) {
                        sum += expand;
                    }
                }

                sum += xDiff + yDiff;
            }
        }

        return sum;
    }

}
