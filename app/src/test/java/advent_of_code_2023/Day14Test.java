package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Test {

    String input = """
            O....#....
            O.OO#....#
            .....##...
            OO.#O....O
            .O.....O#.
            O.#..O.#.#
            ..O..#O..O
            .......O..
            #....###..
            #OO..#....""";

    @Test
    void solve_part1_example() {
        assertEquals(136, Day14.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(106997, Day14.part1(Util.INSTANCE.readFile("day14")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(64, Day14.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(99641, Day14.part2(Util.INSTANCE.readFile("day14")));
    }

}
