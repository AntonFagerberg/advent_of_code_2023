package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07Test {

    String input = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483""";

    @Test
    void solve_part1_example() {
        assertEquals(6440, Day07.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(251029473, Day07.part1(Util.INSTANCE.readFile("day07")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(5905, Day07.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(251003917, Day07.part2(Util.INSTANCE.readFile("day07")));
    }

}
