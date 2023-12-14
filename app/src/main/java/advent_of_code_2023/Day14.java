package advent_of_code_2023;

import java.util.HashMap;
import java.util.HashSet;

public class Day14 {

    static int part1(String[] input) {
        var m = new HashMap<String, Integer>();
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                if (y == 0) {
                    int i = 0;
                    int c = 0;
                    while (true) {
                        char c1 = input[i].charAt(x);
                        if (c1 == 'O') {
                            c++;
                        }

                        if (c1 == '#' || i == input.length - 1) {
                            break;
                        }

                        i++;
                    }

                    if (c != 0) {
                        m.put("%s,%s".formatted(x, y), c);
                    }
                }

                if (input[y].charAt(x) == '#' && y != input.length - 1) {
                    int i = y + 1;
                    int c = 0;
                    while (true) {
                        char c1 = input[i].charAt(x);
                        if (c1 == 'O') {
                            c++;
                        }

                        if (c1 == '#' || i == input.length - 1) {
                            break;
                        }

                        i++;
                    }

                    if (c != 0) {
                        m.put("%s,%s".formatted(x, y + 1), c);
                    }
                }
            }
        }

        var result = 0;
        for (var e : m.entrySet()) {
            var y = Integer.parseInt(e.getKey().split(",")[1]);
            var c = input.length - y;
            for (int d = 0; d < e.getValue(); d++) {
                result += c - d;
            }
        }

        return result;
    }

    static String toKey(int x, int y) {
        return "%s,%s".formatted(x, y);
    }

    static int xKey(String s) {
        return Integer.parseInt(s.split(",")[0]);
    }

    static int yKey(String s) {
        return Integer.parseInt(s.split(",")[1]);
    }

    static String[] tilt(String[] input) {
        var cubes = new HashSet<String>();
        var rounds = new HashSet<String>();

        var yMax = input.length;
        var xMax = input[0].length();

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                if (input[y].charAt(x) == '#') {
                    cubes.add(toKey(x, y));
                } else if (input[y].charAt(x) == 'O') {
                    rounds.add(toKey(x, y));
                }
            }
        }

        var roundsNext = new HashSet<String>();

        for (int x = 0; x < xMax; x++) {
            int y = 0;
            int i = -1;
            while (true) {
                if (y == yMax) {
                    break;
                }

                String key = toKey(x, y);
                if (rounds.contains(key)) {
                    i++;
                }

                if (cubes.contains(key)) {
                    break;
                }

                y++;
            }

            for (; i >= 0; i--) {
                roundsNext.add(toKey(x, i));
            }
        }

        for (var line : cubes) {
            var cX = xKey(line);
            var cY = yKey(line);

            var y = cY + 1;
            int i = -1;
            while (true) {
                if (y >= yMax) {
                    break;
                }
                String key = toKey(cX, y);
                if (rounds.contains(key)) {
                    i++;
                }

                if (cubes.contains(key)) {
                    break;
                }

                y++;
            }

            for (; i >= 0; i--) {
                roundsNext.add(toKey(cX, cY + 1 + i));
            }
        }

        var r = new String[yMax];
        for (int y = 0; y < input.length; y++) {
            var sb = new StringBuilder();
            for (int x = 0; x < input[y].length(); x++) {
                if (roundsNext.contains(toKey(x, y))) {
                    sb.append('O');
                } else if (cubes.contains(toKey(x, y))) {
                    sb.append('#');
                } else {
                    sb.append('.');
                }
            }
            r[y] = sb.toString();
        }

        return r;
    }

    static String[] rotate(String[] input) {
        var r = new String[input[0].length()];

        for (int x = 0; x < input[0].length(); x++) {
            var sb = new StringBuilder();
            for (int y = 0; y < input.length; y++) {
                sb.append(input[y].charAt(x));
            }
            r[x] = sb.reverse().toString();
        }

        return r;
    }

    static int part2(String[] input) {
        var originalInput = input;

        var cache = new HashMap<String, Integer>();
        var maxCycles = 1000000000;
        int cycleLength = 0;
        int seen = -1;
        int i = 0;
        while (i != maxCycles) {
            input = rotate(tilt(input));
            input = rotate(tilt(input));
            input = rotate(tilt(input));
            input = rotate(tilt(input));

            var gridAsLine = String.join("\n", input);
            if (!cache.containsKey(gridAsLine)) {
                cache.put(gridAsLine, i);
            } else {
                int cachedValue = cache.get(gridAsLine);
                if (seen == cachedValue) {
                    break;
                } else if (seen == -1) {
                    seen = cachedValue;
                }

                cycleLength++;
            }

            i++;
        }

        var magicNr = (1000000000 - seen) % cycleLength + seen;

        input = originalInput;

        for (int j = 0; j < magicNr; j++) {
            input = rotate(tilt(input));
            input = rotate(tilt(input));
            input = rotate(tilt(input));
            input = rotate(tilt(input));
        }

        int result = 0;

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                if (input[y].charAt(x) == 'O') {
                    result += input.length - y;
                }
            }
        }

        return result;
    }

}
