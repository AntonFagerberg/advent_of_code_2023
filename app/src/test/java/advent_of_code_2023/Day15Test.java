package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Test {

    String input = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";

    @Test
    void solve_part1_example() {
        assertEquals(1320, Day15.part1(input.split(",")));
    }

    @Test
    void solve_part1_example1_hash() {
        assertEquals(52, Day15.part1("HASH".split(",")));
    }

    @Test
    void solve_part1() {
        assertEquals(516804, Day15.part1(Util.INSTANCE.readFile("day15")[0].split(",")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(145, Day15.part2(input.split(",")));
    }

    @Test
    void solve_part2() {
        assertEquals(231844, Day15.part2(Util.INSTANCE.readFile("day15")[0].split(",")));
    }

}
