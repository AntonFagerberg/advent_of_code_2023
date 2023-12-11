package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {

    @Test
    void solve_part1_example() {
        String input = """
                ...#......
                .......#..
                #.........
                ..........
                ......#...
                .#........
                .........#
                ..........
                .......#..
                #...#.....""";

        assertEquals(374, Day11.solve(input.split("\n"), 2));
    }

    @Test
    void solve_part1() {
        assertEquals(9370588, Day11.solve(Util.INSTANCE.readFile("day11"), 2));
    }

    @Test
    void solve_part2_example() {
        String input = """
                ...#......
                .......#..
                #.........
                ..........
                ......#...
                .#........
                .........#
                ..........
                .......#..
                #...#.....""";
        assertEquals(8410, Day11.solve(input.split("\n"), 100));
    }

    @Test
    void solve_part2() {
        // 9370588 too low
        assertEquals(746207878188L, Day11.solve(Util.INSTANCE.readFile("day11"), 1_000_000));
    }

}
