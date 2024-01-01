package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day21Test {

    String input = """
            ...........
            .....###.#.
            .###.##..#.
            ..#.#...#..
            ....#.#....
            .##..S####.
            .##..#...#.
            .......##..
            .##.#.####.
            .##..##.##.
            ...........""";

    @Test
    void solve_part1_example() {
        assertEquals(16, Day21.part1(input.split("\n"), 6));
    }

    @Test
    void solve_part1() {
        assertEquals(3600, Day21.part1(Util.INSTANCE.readFile("day21"), 64));
    }

}
