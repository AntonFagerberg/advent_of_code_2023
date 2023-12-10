package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {

    @Test
    void solve_part1_example() {
    String input = """
            .....
            .S-7.
            .|.|.
            .L-J.
            .....""";

        assertEquals(4, Day10.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(6640, Day10.part1(Util.INSTANCE.readFile("day10")));
    }

    @Test
    void solve_part2_example() {
        String input = """
                .F----7F7F7F7F-7....
                .|F--7||||||||FJ....
                .||.FJ||||||||L7....
                FJL7L7LJLJ||LJ.L-7..
                L--J.L7...LJS7F-7L7.
                ....F-J..F7FJ|L7L7L7
                ....L7.F7||L7|.L7L7|
                .....|FJLJ|FJ|F7|.LJ
                ....FJL-7.||.||||...
                ....L---J.LJ.LJLJ...""";
        assertEquals(8, Day10.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(411, Day10.part2(Util.INSTANCE.readFile("day10")));
    }

}
