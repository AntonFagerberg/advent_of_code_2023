package advent_of_code_2023;

import java.util.HashMap;
import java.util.Map;

public class Day02 {

    static int part1(String[] input) {
        var m = new HashMap<>(Map.of("red", 12, "green", 13, "blue", 14));
        var result = 0;
        var game = -1;
        for (var line : input) {
            var ok = true;
            String[] split = line.split("[:;,]? ");

            for (int i = 0; i < split.length; i += 2) {
                if (split[i].equals("Game")) {
                    game = Integer.parseInt(split[i + 1]);
                } else {
                    var value = m.get(split[i + 1]);
                    value -= Integer.parseInt(split[i]);
                    if (value < 0) {
                        ok = false;
                        break;
                    }
                }
            }

            if (ok) {
                result += game;
            }
        }

        return result;
    }

    static int part2(String[] input) {
        int result = 0;
        for (var line : input) {
            var m = new HashMap<>(Map.of("red", 0, "green", 0, "blue", 0));
            String[] split = line.split("[:;,]? ");

            for (int i = 0; i < split.length; i += 2) {
                if (!split[i].equals("Game")) {
                    var value = m.get(split[i + 1]);
                    var newValue = Integer.parseInt(split[i]);

                    if (newValue > value) {
                        m.put(split[i + 1], newValue);
                    }
                }

            }

            result += m.values().stream().reduce(1, (i1, i2) -> i1 * i2);
        }

        return result;
    }
}
