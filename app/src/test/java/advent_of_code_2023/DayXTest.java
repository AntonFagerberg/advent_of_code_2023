package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DayXTest {

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
        assertEquals(94, DayX.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(-1, DayX.part1(Util.INSTANCE.readFile("dayX")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(154, DayX.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        // 4722 too low
        // 5515 too low
        assertEquals(-1, DayX.part2(Util.INSTANCE.readFile("dayX")));
    }

}
