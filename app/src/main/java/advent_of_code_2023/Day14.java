package advent_of_code_2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

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
//        var cubes = new HashSet<String>();
//        var rounds = new HashSet<String>();
//
//        for (int i = 0; i < 1; i++) {
//            var yMax = input.length;
//            var xMax = input[0].length();
//
//            for (int y = 0; y < input.length; y++) {
//                for (int x = 0; x < input[y].length(); x++) {
//                    if (input[y].charAt(x) == '#') {
//                        cubes.add(toKey(x, y));
//                    } else if (input[y].charAt(x) == 'O') {
//                        rounds.add(toKey(x, y));
//                    }
//                }
//            }
//
//            var roundsNext = new HashSet<String>();
//
//            for (int x = 0; x < xMax; x++) {
//                int y = 0;
//                int i = -1;
//                while (true) {
//                    if (y == yMax) {
//                        break;
//                    }
//
//                    String key = toKey(x, y);
//                    if (rounds.contains(key)) {
//                        i++;
//                    }
//
//                    if (cubes.contains(key)) {
//                        break;
//                    }
//
//                    y++;
//                }
//
//                for (; i >= 0; i--) {
//                    roundsNext.add(toKey(x, i));
//                }
//            }
//
//            for (var line : cubes) {
//                var cX = xKey(line);
//                var cY = yKey(line);
//
//                var y = cY + 1;
//                int i = -1;
//                while (true) {
//                    if (y >= yMax) {
//                        break;
//                    }
//                    String key = toKey(cX, y);
//                    if (rounds.contains(key)) {
//                        i++;
//                    }
//
//                    if (cubes.contains(key)) {
//                        break;
//                    }
//
//                    y++;
//                }
//
//                for (; i >= 0; i--) {
//                    roundsNext.add(toKey(cX, cY + 1 + i));
//                }
//            }
//
//            var result = 0;

        var cache = new HashMap<String, Integer>();

//        var t = 1000000000;
//        for (int i = 0; i < t; i++) {
////            if (i % 10000 == 0) {
////                System.out.println((i / (float)t)*100);
////            }
//            input = rotate(tilt(input));
//            input = rotate(tilt(input));
//            input = rotate(tilt(input));
//            input = rotate(tilt(input));
//
//            var s = Arrays.stream(input).collect(Collectors.joining("\n"));
//            if (!cache.containsKey(s)) {
//                cache.put(s, i);
//            } else {
//                System.out.println("Found: %s, (%s)".formatted(cache.get(s), i));
//            }
//
//            if (i > 1000) {
//                break;
//            }
//        }

        /**
         * shell> (1011-1000) % 6
         * $53 ==> 5
         *
         * jshell> (1011-1000) % 8
         * $54 ==> 3
         *
         * jshell> (1013-1000) % 8
         * $55 ==> 5
         *
         * jshell> (1157-1000) % 8
         * $56 ==> 5
         *
         * jshell> (1017 - 996)
         * $57 ==> 21
         *
         * jshell> (1017 - 996) % 6
         * $58 ==> 3
         *
         * jshell> (1017 - 996)
         * $59 ==> 21
         *
         * jshell> (1017 - 996) % 7
         * $60 ==> 0
         *
         * jshell> (1017 - 996) % 7 + 2
         * $61 ==> 2
         *
         * jshell> (1069 - 996) % 7 + 2
         * $62 ==> 5
         *
         * jshell> (1000000000 - 996) % 7 + 2
         * $63 ==> 6
         *
         * jshell> (655 - 634) % (183-175) + 175
         * $64 ==> 180
         *
         * jshell> (655 - 634) % (183-175 + 1) + 175
         * $65 ==> 178
         *
         * jshell> (654 - 634) % (183-175 + 1) + 175
         * $66 ==> 177
         *
         * jshell> (658 - 634) % (183-175 + 1) + 175
         * $67 ==> 181
         *
         * jshell> (1000000000 - 634) % (183-175 + 1) + 175
         * $68 ==> 181
         */

        for (int i = 0; i < 181; i++) {
            input = rotate(tilt(input));
            input = rotate(tilt(input));
            input = rotate(tilt(input));
            input = rotate(tilt(input));
        }

//        input = tilt(input);
//
//        Arrays.stream(input).forEach(System.out::println);
//
        int result = 0;

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length(); x++) {
                if (input[y].charAt(x) == 'O') {
                    result += input.length - y;
                }
            }
        }



//        1000 -> 6
//
//        System.out.println("---");
//
//        input = rotate(input);
//        input = rotate(input);
//        input = rotate(input);
//        input = rotate(input);
//
//        Arrays.stream(input).forEach(System.out::println);

        return result;


//
//        for (int y = 0; y < input.length; y++) {
//            for (int x = 0; x < input[y].length(); x++) {
//                if (roundsNext.contains(toKey(x, y))) {
//                    System.out.print('0');
//                } else if (cubes.contains(toKey(x, y))) {
//                    System.out.print('#');
//                } else {
//                    System.out.print('.');
//                }
//            }
//            System.out.println();
//        }

//            for (var e : roundsNext) {
//                result += yMax - yKey(e);
//            }

//            return result;
    }

}
