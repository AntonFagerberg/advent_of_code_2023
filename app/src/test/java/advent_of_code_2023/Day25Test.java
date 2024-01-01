package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day25Test {

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
    void solve_example() {
        assertEquals(54, Day25.solve(input.split("\n")));
    }
    @Test
    void solve() {
        assertEquals(600225, Day25.solve(Util.INSTANCE.readFile("day25")));
    }

}
