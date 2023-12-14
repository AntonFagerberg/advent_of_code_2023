package advent_of_code_2023;

import java.util.*;

public class Day12 {

    static Map<String, Long> cache = new HashMap<>();

    static long place(List<Integer> blocks, int blockIndex, int stringIndex, String string, int stringIndexMax, int blockIndexMax) {
        var cacheKey = "%s|%s|%s|%s".formatted(blocks, stringIndex, blockIndex, string);

        var cacheValue = cache.get(cacheKey);

        if (cacheValue != null) {
            return cacheValue;
        }

        if (stringIndex > stringIndexMax) {
            return 0L;
        }

        if (stringIndex == stringIndexMax) {
            return blockIndex == blockIndexMax ? 1L : 0L;
        }

        if (blockIndex == blockIndexMax) {
            for (int i = stringIndex; i < stringIndexMax; i++) {
                if (string.charAt(i) == '#') {
                    return 0L;
                }
            }

            return 1L;
        }

        final var currentChar = string.charAt(stringIndex);
        if (currentChar == '.') {
            return place(blocks, blockIndex, stringIndex + 1, string, stringIndexMax, blockIndexMax);
        }

        var blockSize = blocks.get(blockIndex);

        int StringIndexBlockSize = stringIndex + blockSize;

        if (StringIndexBlockSize > stringIndexMax) {
            return 0L;
        }

        var mustPlace = currentChar == '#';

        var canPlace = true;
        for (int i = 0; i < blockSize; i++) {
            canPlace = string.charAt(stringIndex + i) != '.';
            if (!canPlace) {
                break;
            }
        }

        var result = 0L;
        if (canPlace) {
            if (StringIndexBlockSize == stringIndexMax) {
                result = place(blocks, blockIndex + 1, StringIndexBlockSize, string, stringIndexMax, blockIndexMax);
            } else if (string.charAt(StringIndexBlockSize) != '#') {
                result = place(blocks, blockIndex + 1, stringIndex + blockSize + 1, string, stringIndexMax, blockIndexMax);
            }
        }

        if (!mustPlace) {
            result += place(blocks, blockIndex, stringIndex + 1, string, stringIndexMax, blockIndexMax);
        }

        cache.put(cacheKey, result);
        return result;
    }

    static long part1(String[] input) {
        return solve(input, 1);
    }

    static long part2(String[] input) {
        return solve(input, 5);
    }

    static long solve(String[] input, int repetitions) {
        long result = 0L;

        for (var line : input) {
            var parts = line.split(" ");
            var nrTemp = Arrays.stream(parts[1].split(","))
                    .map(Integer::parseInt)
                    .toList();

            ArrayList<Integer> numbers = new ArrayList<>(nrTemp.size() * 5);

            for (int i = 0; i < repetitions; i++) {
                numbers.addAll(nrTemp);
            }

            String part = parts[0];
            var stringBuilder = new StringBuilder();
            for (int i = 0; i < repetitions - 1; i++) {
                stringBuilder.append(part).append("?");
            }
            stringBuilder.append(part);

            final var string = stringBuilder.toString();
            result += place(numbers, 0, 0, string, string.length(), numbers.size());

        }

        return result;
    }

}
