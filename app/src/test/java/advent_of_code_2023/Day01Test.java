package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Test {

    @Test
    void solve_part1_example() {
        var input = """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet""";

        assertEquals(142, Day01.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        var input = Util.INSTANCE.readFile("day01");
        assertEquals(55130, Day01.part1(input));
    }

    @Test
    void solve_part2_example() {
        var input = """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen""";

        assertEquals(281, Day01.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        var input = Util.INSTANCE.readFile("day01");
        assertEquals(54985, Day01.part2(input));
    }

}
