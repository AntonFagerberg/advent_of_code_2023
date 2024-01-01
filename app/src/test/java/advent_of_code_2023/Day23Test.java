package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day23Test {

    String input = """
            #.#####################
            #.......#########...###
            #######.#########.#.###
            ###.....#.>.>.###.#.###
            ###v#####.#v#.###.#.###
            ###.>...#.#.#.....#...#
            ###v###.#.#.#########.#
            ###...#.#.#.......#...#
            #####.#.#.#######.#.###
            #.....#.#.#.......#...#
            #.#####.#.#.#########v#
            #.#...#...#...###...>.#
            #.#.#v#######v###.###v#
            #...#.>.#...>.>.#.###.#
            #####v#.#.###v#.#.###.#
            #.....#...#...#.#.#...#
            #.#########.###.#.#.###
            #...###...#...#...#.###
            ###.###.#.###v#####v###
            #...#...#.#.>.>.#.>.###
            #.###.###.#.###.#.#v###
            #.....###...###...#...#
            #####################.#""";

    @Test
    void solve_part1_example() {
        assertEquals(94, Day23.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(2246, Day23.part1(Util.INSTANCE.readFile("day23")));
    }
    @Test
    void solve_part2_example() {
        assertEquals(154, Day23.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(6622, Day23.part2(Util.INSTANCE.readFile("day23")));
    }


}
