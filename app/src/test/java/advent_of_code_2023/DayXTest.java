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
    void solve_part2_example() throws InterruptedException {
        assertEquals(154, DayX.part2(input.split("\n")));
    }

    @Test
    void solve_part2() throws InterruptedException {
        // 4722 too low
        // 5799 too low
        assertEquals(-1, DayX.part2(Util.INSTANCE.readFile("dayX")));
    }

    @Test
    void solve_part2_old() throws InterruptedException {
        // 4722 too low
        // 5799 too low
        // 6223 not right
        assertEquals(-1, DayX.part2_old(Util.INSTANCE.readFile("dayX")));
    }

    @Test
    void solve_part3() throws InterruptedException {
        // 4722 too low
        // 5799 too low
        // 6075?
        assertEquals(-1, DayX.part3(Util.INSTANCE.readFile("dayX")));
    }

    @Test
    void solve_part4() throws InterruptedException {
        // 4722 too low
        // 5799 too low
        // 6075?
        assertEquals(-1, DayX.part3_old(Util.INSTANCE.readFile("dayX")));
    }

    @Test
    void solve_part4_ex() throws InterruptedException {
        // 4722 too low
        // 5799 too low
        // 6575 -> nope
        assertEquals(154, DayX.part3_old(input.split("\n")));
    }

}
