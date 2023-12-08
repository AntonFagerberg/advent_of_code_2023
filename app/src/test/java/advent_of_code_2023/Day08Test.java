package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Test {

    @Test
    void solve_part1_example() {
        String input = """
            LLR
                        
            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)""";

        assertEquals(6, Day08.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(13939, Day08.part1(Util.INSTANCE.readFile("day08")));
    }

    @Test
    void solve_part2_example() {
        String input = """
                LR
                                
                11A = (11B, XXX)
                11B = (XXX, 11Z)
                11Z = (11B, XXX)
                22A = (22B, XXX)
                22B = (22C, 22C)
                22C = (22Z, 22Z)
                22Z = (22B, 22B)
                XXX = (XXX, XXX)""";

        assertEquals(6, Day08.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(8906539031197L, Day08.part2(Util.INSTANCE.readFile("day08")));
    }

}
