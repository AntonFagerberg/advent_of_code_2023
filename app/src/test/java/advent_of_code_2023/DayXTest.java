package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DayXTest {

    String input = """
            ...........
            .....###.#.
            .###.##..#.
            ..#.#...#..
            ....#.#....
            .##..S####.
            .##..#...#.
            .......##..
            .##.#.####.
            .##..##.##.
            ...........""";

    /*

    ......................
    .....###.#.
    .###.##.O#.
    .O#O#O.O#..
    O.O.#.#.O..
    .##O.O####.
    .##.O#O..#.
    .O.O.O.##..
    .##.#.####.
    .##O.##.##.
    ...........

     */

    @Test
    void solve_part1_example() {
        assertEquals(16, DayX.part1(input.split("\n"), 6));
    }

    @Test
    void solve_part1() {
        assertEquals(3600, DayX.part1(Util.INSTANCE.readFile("dayX"), 64));
    }

    @Test
    void solve_part2_example() {
//        for (int i = 1; i  < 250; i++) {
//            System.out.println("%s: %s".formatted(i, DayX.part2(input.split("\n"), i)));
//        }

//        System.getenv().putIfAbsent("DEBUG", "true");
//        assertEquals(16, DayX.part2(input.split("\n"), 6));
//        assertEquals(50, DayX.part2(input.split("\n"), 10));
        assertEquals(1594, DayX.part2(input.split("\n"), 50));
//        assertEquals(6536, DayX.part2(input.split("\n"), 100));
//        assertEquals(16733044, DayX.part2(input.split("\n"), 5000));
    }

    @Test
    void solve_part2() {
        assertEquals(-1, DayX.part2(Util.INSTANCE.readFile("dayX"), 1));
    }

}
