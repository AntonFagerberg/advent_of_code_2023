package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16Test {

    String input = """
            .|...\\....
            |.-.\\.....
            .....|-...
            ........|.
            ..........
            .........\\
            ..../.\\\\..
            .-.-/..|..
            .|....-|.\\
            ..//.|....""";

    @Test
    void solve_part1_example() {
        assertEquals(46, Day16.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(6855, Day16.part1(Util.INSTANCE.readFile("day16")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(51, Day16.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(7513, Day16.part2(Util.INSTANCE.readFile("day16")));
    }

}
