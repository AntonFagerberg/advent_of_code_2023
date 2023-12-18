package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day18Test {

    String input = """
            R 6 (#70c710)
            D 5 (#0dc571)
            L 2 (#5713f0)
            D 2 (#d2c081)
            R 2 (#59c680)
            D 2 (#411b91)
            L 5 (#8ceee2)
            U 2 (#caa173)
            L 1 (#1b58a2)
            U 2 (#caa171)
            R 2 (#7807d2)
            U 3 (#a77fa3)
            L 2 (#015232)
            U 2 (#7a21e3)""";

    @Test
    void solve_part1_example() {
        assertEquals(62, Day18.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(39194, Day18.part1(Util.INSTANCE.readFile("day18")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(952408144115L, Day18.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(78242031808225L, Day18.part2(Util.INSTANCE.readFile("day18")));
    }

}
