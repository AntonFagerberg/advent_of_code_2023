package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test {

    String input = """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45""";

    @Test
    void solve_part1_example() {
        assertEquals(114, Day09.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(1762065988, Day09.part1(Util.INSTANCE.readFile("day09")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(2, Day09.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(1066, Day09.part2(Util.INSTANCE.readFile("day09")));
    }

}
