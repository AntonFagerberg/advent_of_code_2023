package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Test {

    String input = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.
                        
            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#""";

    @Test
    void solve_part1_example() {

        // 29373 -> nope
        assertEquals(405, Day13.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(-1, Day13.part1(Util.INSTANCE.readFile("day13")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(400, Day13.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        // nope 4846499
        assertEquals(-1, Day13.part2(Util.INSTANCE.readFile("day13")));
    }

}
