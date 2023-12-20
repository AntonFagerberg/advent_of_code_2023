package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day20Test {

    String input = """
            broadcaster -> a
            %a -> inv, con
            &inv -> b
            %b -> con
            &con -> output""";

    @Test
    void solve_part1_example() {
        assertEquals(11687500, Day20.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(856482136, Day20.part1(Util.INSTANCE.readFile("day20")));
    }

    @Test
    void solve_part2() {
        assertEquals(224046542165867L, Day20.part2(Util.INSTANCE.readFile("day20")));
    }

}
