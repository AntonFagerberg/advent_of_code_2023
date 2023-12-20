package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DayXTest {

    String input = """
            broadcaster -> a
            %a -> inv, con
            &inv -> b
            %b -> con
            &con -> output""";

    @Test
    void solve_part1_example() {
        assertEquals(11687500, DayX.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(856482136, DayX.part1(Util.INSTANCE.readFile("dayX")));
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
