package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {

    String input = """
            ???.### 1,1,3
            .??..??...?##. 1,1,3
            ?#?#?#?#?#?#?#? 1,3,1,6
            ????.#...#... 4,1,1
            ????.######..#####. 1,6,5
            ?###???????? 3,2,1""";

    @Test
    void solve_part1_example() {
        assertEquals(21, Day12.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(6935, Day12.part1(Util.INSTANCE.readFile("day12")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(525152, Day12.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(-1, Day12.part2(Util.INSTANCE.readFile("day12")));
    }

}
