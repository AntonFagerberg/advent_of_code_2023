package advent_of_code_2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day19Test {

    String input = """
            px{a<2006:qkq,m>2090:A,rfg}
            pv{a>1716:R,A}
            lnx{m>1548:A,A}
            rfg{s<537:gd,x>2440:R,A}
            qs{s>3448:A,lnx}
            qkq{x<1416:A,crn}
            crn{x>2662:A,R}
            in{s<1351:px,qqz}
            qqz{s>2770:qs,m<1801:hdj,R}
            gd{a>3333:R,R}
            hdj{m>838:A,pv}
                        
            {x=787,m=2655,a=1222,s=2876}
            {x=1679,m=44,a=2067,s=496}
            {x=2036,m=264,a=79,s=2244}
            {x=2461,m=1339,a=466,s=291}
            {x=2127,m=1623,a=2188,s=1013}""";

    @Test
    void solve_part1_example() {
        assertEquals(19114, Day19.part1(input.split("\n")));
    }

    @Test
    void solve_part1() {
        assertEquals(263678, Day19.part1(Util.INSTANCE.readFile("day19")));
    }

    @Test
    void solve_part2_example() {
        assertEquals(167409079868000L, Day19.part2(input.split("\n")));
    }

    @Test
    void solve_part2() {
        // 167409079868000 too high
        // 167409079868000
        assertEquals(125455345557345L, Day19.part2(Util.INSTANCE.readFile("day19")));
    }

}
