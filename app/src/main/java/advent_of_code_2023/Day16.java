package advent_of_code_2023;

import java.util.HashMap;
import java.util.Map;

public class Day16 {

    static Map<String, Integer> visited = new HashMap<>();
    static Map<String, Integer> xy = new HashMap<>();

    static void move(int xDir, int yDir, int x, int y, String[] input) {
        try {
            char c = input[y].charAt(x);
            var key = "%s,%s,%s,%s".formatted(x, y, xDir, yDir);

            if (visited.containsKey(key)) {
                return;
            }

            xy.put("%s,%s".formatted(x, y), 1);

            visited.put(key, visited.getOrDefault(key, 0));

            if (c == '.') {
                move(xDir, yDir, x + xDir, y + yDir, input);
                return;
            } else if (c == '|') {
                if (yDir != 0) {
                    move(xDir, yDir, x, y + yDir, input);
                    return;
                }

                move(0, 1, x, y + 1, input);
                move(0, -1, x, y - 1, input);
                return;
            } else if (c == '-') {
                if (xDir != 0) {
                    move(xDir, yDir, x + xDir, y, input);
                    return;
                }

                move(-1, 0, x - 1, y, input);
                move(1, 0, x + 1, y, input);
                return;
            } else if (c == '\\') {
                if (xDir == 1) {
                    move(0, 1, x, y + 1, input);
                    return;
                } else if (xDir == -1) {
                    move(0, -1, x, y - 1, input);
                    return;
                } else if (yDir == 1) {
                    move(1, 0, x + 1, y, input);
                    return;
                } else if (yDir == -1) {
                    move(-1, 0, x - 1, y, input);
                    return;
                }
            } else if (c == '/') {
                if (xDir == 1) {
                    move(0, -1, x, y - 1, input);
                    return;
                } else if (xDir == -1) {
                    move(0, 1, x, y + 1, input);
                    return;
                } else if (yDir == 1) {
                    move(-1, 0, x - 1, y, input);
                    return;
                } else if (yDir == -1) {
                    move(1, 0, x + 1, y, input);
                    return;
                }
            }

            throw new IllegalStateException(":(");

        } catch (IndexOutOfBoundsException e) {
        }
    }

    static int part1(String[] input) {
        xy.clear();
        visited.clear();
        move(1, 0, 0, 0, input);

        return xy.size();
    }

    static int part2(String[] input) {
        xy.clear();
        visited.clear();

        var max = 0;
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[0].length(); x++) {
                for (int xD = -1; xD <= 1; xD++) {
                    for (int yD = -1; yD <= 1; yD++) {
                        if ((x == 0 || y == 0 || x == input[0].length() - 1 || y == input.length - 1) && (xD == 0 || yD == 0) && xD != yD) {
                            move(xD, yD, x, y, input);
                            max = Math.max(xy.size(), max);
                            xy.clear();
                            visited.clear();
                        }
                    }
                }
            }
        }

        return max;
    }

}
