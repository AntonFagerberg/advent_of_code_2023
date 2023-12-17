package advent_of_code_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Test {

    String input = """
            2413432311323
            3215453535623
            3255245654254
            3446585845452
            4546657867536
            1438598798454
            4457876987766
            3637877979653
            4654967986887
            4564679986453
            1224686865563
            2546548887735
            4322674655533""";

    @Test
    void solve_part1_example() {
        assertEquals(102, Day17.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(859, Day17.part1(Util.INSTANCE.readFile("day17")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(94, Day17.part2(input.split("\n")));
    }

    @Test
    void solve_part2_example2() {
        var input = """
                111111111111
                999999999991
                999999999991
                999999999991
                999999999991""";
        assertEquals(71, Day17.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        // 1018 too low
        assertEquals(1027, Day17.part2(Util.INSTANCE.readFile("day17")));
    }

}
