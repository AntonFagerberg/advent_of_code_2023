package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day22Test {

    String input = """
            1,0,1~1,2,1
            0,0,2~2,0,2
            0,2,3~2,2,3
            0,0,4~0,2,4
            2,0,5~2,2,5
            0,1,6~2,1,6
            1,1,8~1,1,9""";

    @Test
    void solve_part1_example() {
        assertEquals(5, Day22.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(492, Day22.part1(Util.INSTANCE.readFile("day22")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(7, Day22.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(86556, Day22.part2(Util.INSTANCE.readFile("day22")));
    }

}
