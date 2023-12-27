package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DayXTest {

    String input = """
            19, 13, 30 @ -2,  1, -2
            18, 19, 22 @ -1, -1, -2
            20, 25, 34 @ -2, -2, -4
            12, 31, 28 @ -1, -2, -1
            20, 19, 15 @  1, -5, -3""";

    @Test
    void solve_part1_example() {
        assertEquals(2, DayX.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(-1, DayX.part1(Util.INSTANCE.readFile("dayX")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(-1, DayX.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(-1, DayX.part2(Util.INSTANCE.readFile("dayX")));
    }

}
