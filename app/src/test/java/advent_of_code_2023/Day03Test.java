package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test {

    @Test
    void solve_part1_example() {
        var input = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..""";

        assertEquals(4361, Day03.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(539590, Day03.part1(Util.INSTANCE.readFile("day03")));
    }

    @Test
    void solve_part2_example() {
        var input = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..""";

        assertEquals(467835, Day03.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(80703636, Day03.part2(Util.INSTANCE.readFile("day03")));
    }

}
