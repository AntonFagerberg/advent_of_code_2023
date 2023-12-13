package advent_of_code_2023;

import java.util.ArrayList;
import java.util.List;

public class Day13 {

    static int part1(String[] input) {
        var blocks = new ArrayList<List<String>>();

        var current = new ArrayList<String>();
        for (var l : input) {
            if (l.equals("")) {
                blocks.add(current);
                current = new ArrayList<>();
            } else {
                current.add(l);
            }
        }
        blocks.add(current);

        int i = 0;

        for (var b : blocks) {
            System.out.println("---");
            for (var y = 1; y < b.size(); y++) {
                boolean m = true;
                for (var yy = 0; yy < b.size() - 1; yy++) {
                    try {
                        if (!b.get(y + yy).equals(b.get(y - (yy + 1)))) {
                            m = false;
                            break;
                        }
                    } catch (IndexOutOfBoundsException e) {

                    }
                }

                if (m) {
                    System.out.println("y: " + y);
                    i += y * 100;
                }

//                System.out.println(y + " -> " + m);
            }

            for (var x = 1; x < b.get(0).length(); x++) {
                boolean m = true;
                for (var xx = 0; xx < b.get(0).length() - 1; xx++) {
                    try {
                        for (String s : b) {
                            if (s.charAt(x + xx) != s.charAt(x - (xx + 1))) {
                                m = false;
                                break;
                            }
                        }
                    } catch (StringIndexOutOfBoundsException e) {
//                        System.out.println(e);
                    }

//                    if (!m) {
//                        break;
//                    }
                }

//                System.out.println(x + " -> " + m);

                if (m) {
                    System.out.println("x: " + x);
                    i += x;
                }
            }
        }

        return i;
    }

    static int part2(String[] input) {
        var blocks = new ArrayList<List<String>>();

        var current = new ArrayList<String>();
        for (var l : input) {
            if (l.equals("")) {
                blocks.add(current);
                current = new ArrayList<>();
            } else {
                current.add(l);
            }
        }
        blocks.add(current);

        int i = 0;

        for (var bb : blocks) {
            for (int xd = 0; xd < bb.getFirst().length(); xd++) {
                for (int yd = 0; yd < bb.size(); yd++) {

                    var b = new ArrayList<String>();
                    for (int y = 0; y < bb.size(); y++) {
                        var s = "";
                        for (int x = 0; x < bb.getFirst().length(); x++) {
                            char s1 = bb.get(y).charAt(x);
                            if (x == xd && y == yd) {
                                if (s1 == '.') {
                                    s += "#";
                                } else {
                                    s += ".";
                                }
                            } else {
                                s += s1;
                            }
                        }
                        b.add(s);
                    }

                    System.out.println("---");
                    for (var y = 1; y < b.size(); y++) {
                        boolean m = true;
                        for (var yy = 0; yy < b.size() - 1; yy++) {
                            try {
                                if (!b.get(y + yy).equals(b.get(y - (yy + 1)))) {
                                    m = false;
                                    break;
                                }
                            } catch (IndexOutOfBoundsException e) {

                            }
                        }

                        if (m) {
                            System.out.println("y: " + y);
                            i += y * 100;
                        }

//                System.out.println(y + " -> " + m);
                    }

                    for (var x = 1; x < b.get(0).length(); x++) {
                        boolean m = true;
                        for (var xx = 0; xx < b.get(0).length() - 1; xx++) {
                            try {
                                for (String s : b) {
                                    if (s.charAt(x + xx) != s.charAt(x - (xx + 1))) {
                                        m = false;
                                        break;
                                    }
                                }
                            } catch (StringIndexOutOfBoundsException e) {
//                        System.out.println(e);
                            }

//                    if (!m) {
//                        break;
//                    }
                        }

//                System.out.println(x + " -> " + m);

                        if (m) {
                            System.out.println("x: " + x);
                            i += x;
                        }
                    }
                }
            }
        }

        return i;
    }

}
