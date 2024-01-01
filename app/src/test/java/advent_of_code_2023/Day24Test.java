package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day24Test {

    String input = """
            19, 13, 30 @ -2,  1, -2
            18, 19, 22 @ -1, -1, -2
            20, 25, 34 @ -2, -2, -4
            12, 31, 28 @ -1, -2, -1
            20, 19, 15 @  1, -5, -3""";

    @Test
    void solve_part1_example() {
        assertEquals(2, Day24.part1(input.split("\n"), 7, 27));
    }

    @Test
    void solve_part1() {
        assertEquals(15107, Day24.part1(Util.INSTANCE.readFile("day24"), 200000000000000L, 400000000000000L));
    }

}
