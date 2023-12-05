package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05Test {

    String input = """
                seeds: 79 14 55 13
                                
                seed-to-soil map:
                50 98 2
                52 50 48
                                
                soil-to-fertilizer map:
                0 15 37
                37 52 2
                39 0 15
                                
                fertilizer-to-water map:
                49 53 8
                0 11 42
                42 0 7
                57 7 4
                                
                water-to-light map:
                88 18 7
                18 25 70
                                
                light-to-temperature map:
                45 77 23
                81 45 19
                68 64 13
                                
                temperature-to-humidity map:
                0 69 1
                1 0 69
                                
                humidity-to-location map:
                60 56 37
                """;

    @Test
    void solve_part1_example() {
        assertEquals(35, Day05.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(346433842, Day05.part1(Util.INSTANCE.readFile("day05")));
    }

    @Test
    void solve_part2_example() throws InterruptedException {
        assertEquals(46, Day05.part2(input.split("\n")));
    }

    @Test
    void solve_part2() throws InterruptedException {
        assertEquals(60294664, Day05.part2(Util.INSTANCE.readFile("day05")));
    }

}
