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

    /*
???.### 1,1,3 - 1 arrangement
.??..??...?##. 1,1,3 - 16384 arrangements
?#?#?#?#?#?#?#? 1,3,1,6 - 1 arrangement
????.#...#... 4,1,1 - 16 arrangements
????.######..#####. 1,6,5 - 2500 arrangements
?###???????? 3,2,1 - 506250 arrangements

?.#

si = 0
r = 1


     */

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
        assertEquals(525152, Day12.part3(input.split("\n")));
    }

    @Test
    void solve_part2() {
        // This will take forever, not the right approach...
        assertEquals(-1, Day12.part3(Util.INSTANCE.readFile("day12")));
    }

}
