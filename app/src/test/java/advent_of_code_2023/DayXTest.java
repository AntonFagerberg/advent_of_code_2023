package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DayXTest {

    String input = """
            jqt: rhn xhk nvd
            rsh: frs pzl lsr
            xhk: hfx
            cmg: qnr nvd lhk bvb
            rhn: xhk bvb hfx
            bvb: xhk hfx
            pzl: lsr hfx nvd
            qnr: nvd
            ntq: jqt hfx bvb xhk
            nvd: lhk
            lsr: lhk
            rzs: qnr cmg lsr rsh
            frs: qnr lhk lsr
            """;

    @Test
    void solve_part1_example() throws InterruptedException {
        assertEquals(54, DayX.part1_3(input.split("\n")));
    }
    @Test
    void solve_part1_example_2() {
        assertEquals(54, DayX.part1_2(input.split("\n")));
    }

    @Test
    void solve_part1() throws InterruptedException {
        assertEquals(-1, DayX.part1_3(Util.INSTANCE.readFile("dayX")));
    }
    @Test
    void solve_part1_2() {
        assertEquals(-1, DayX.part1_2(Util.INSTANCE.readFile("dayX")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(-1, DayX.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        assertEquals(-1, DayX.part2(Util.INSTANCE.readFile("dayX")));
    }

}
