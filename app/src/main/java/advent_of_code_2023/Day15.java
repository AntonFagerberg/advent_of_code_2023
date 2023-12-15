package advent_of_code_2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day15 {

    static int part1(String[] input) {
        int result = 0;
        for (var s : input) {
            var hash = 0;
            for (var c : s.toCharArray()) {
                hash += c;
                hash *= 17;
                hash %= 256;
            }
            result += hash;
        }

        return result;
    }

    static int part2(String[] input) {
        var boxes = new HashMap<Integer, List<String>>();
        var labelValues = new HashMap<String, Integer>();
        for (var s : input) {
            var parts = s.split("([=\\-])");
            String label = parts[0];
            if (s.contains("=")) {
                int hash = 0;
                labelValues.put(label, Integer.parseInt(parts[1]));
                for (var c : label.toCharArray()) {
                    hash += c;
                    hash *= 17;
                    hash %= 256;
                }
                List<String> boxContent = boxes.getOrDefault(hash, new ArrayList<>());
                if (!boxContent.contains(label)) {
                    boxContent.add(label);
                }
                boxes.put(hash, boxContent);
            } else if (s.contains("-")) {
                boxes.values().forEach(content -> content.remove(label));
            }

        }

        var result = 0;

        for (int b = 0; b < 256; b++) {
            var box = boxes.get(b);
            if (box != null) {
                for (int l = 0; l < box.size(); l++) {
                    result += (b + 1) * (l + 1) * labelValues.get(box.get(l));
                }
            }
        }

        return result;
    }

}
