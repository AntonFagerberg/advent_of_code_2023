package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Test {

    String input = """
            Time:      7  15   30
            Distance:  9  40  200
            """;

    @Test
    void solve_part1_example() {
        assertEquals(288, Day06.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(449820, Day06.part1(Util.INSTANCE.readFile("day06")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(71503, Day06.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(42250895, Day06.part2(Util.INSTANCE.readFile("day06")));
    }

}
