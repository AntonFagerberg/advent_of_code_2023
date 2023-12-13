package advent_of_code_2023;

import java.util.ArrayList;
import java.util.List;

public class Day13 {

    static int part1(String[] input) {
        var blocks = new ArrayList<List<String>>();
        var current = new ArrayList<String>();

        for (var line : input) {
            if (line.isEmpty()) {
                blocks.add(current);
                current = new ArrayList<>();
            } else {
                current.add(line);
            }
        }
        blocks.add(current);

        int result = 0;

        for (var block : blocks) {
            for (var y = 1; y < block.size(); y++) {
                boolean mirrored = true;
                for (var yy = 0; yy < block.size() - 1; yy++) {
                    try {
                        if (!block.get(y + yy).equals(block.get(y - (yy + 1)))) {
                            mirrored = false;
                            break;
                        }
                    } catch (IndexOutOfBoundsException e) {

                    }
                }

                if (mirrored) {
                    result += y * 100;
                }

            }

            for (var x = 1; x < block.getFirst().length(); x++) {
                boolean mirrored = true;
                for (var xx = 0; xx < block.getFirst().length() - 1; xx++) {
                    try {
                        for (var line : block) {
                            if (line.charAt(x + xx) != line.charAt(x - (xx + 1))) {
                                mirrored = false;
                                break;
                            }
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                    }
                }

                if (mirrored) {
                    result += x;
                }
            }
        }

        return result;
    }

    static int part2(String[] input) {
        var blocks = new ArrayList<List<String>>();
        var current = new ArrayList<String>();

        for (var line : input) {
            if (line.isEmpty()) {
                blocks.add(current);
                current = new ArrayList<>();
            } else {
                current.add(line);
            }
        }
        blocks.add(current);

        var previousMirrors = new ArrayList<String>();
        for (var block : blocks) {
            for (var y = 1; y < block.size(); y++) {
                boolean mirrors = true;
                for (var yy = 0; yy < block.size() - 1; yy++) {
                    try {
                        if (!block.get(y + yy).equals(block.get(y - (yy + 1)))) {
                            mirrors = false;
                            break;
                        }
                    } catch (IndexOutOfBoundsException e) {

                    }
                }

                if (mirrors) {
                    previousMirrors.add("y="+y);
                }
            }

            for (var x = 1; x < block.getFirst().length(); x++) {
                boolean mirrors = true;
                for (var xx = 0; xx < block.getFirst().length() - 1; xx++) {
                    try {
                        for (var line : block) {
                            if (line.charAt(x + xx) != line.charAt(x - (xx + 1))) {
                                mirrors = false;
                                break;
                            }
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                    }
                }

                if (mirrors) {
                    previousMirrors.add("x="+x);
                }
            }
        }

        int result = 0;
        var blockIndex = 0;
        for (var bb : blocks) {
            for (int xd = 0; xd < bb.getFirst().length(); xd++) {
                for (int yd = 0; yd < bb.size(); yd++) {

                    var fixedBlock = new ArrayList<String>();
                    for (int y = 0; y < bb.size(); y++) {
                        var newLine = new StringBuilder();
                        for (int x = 0; x < bb.getFirst().length(); x++) {
                            char currentChar = bb.get(y).charAt(x);
                            if (x == xd && y == yd) {
                                if (currentChar == '.') {
                                    newLine.append("#");
                                } else {
                                    newLine.append(".");
                                }
                            } else {
                                newLine.append(currentChar);
                            }
                        }

                        fixedBlock.add(newLine.toString());
                    }

                    for (var y = 1; y < fixedBlock.size(); y++) {
                        boolean mirrored = true;
                        for (var yy = 0; yy < fixedBlock.size() - 1; yy++) {
                            try {
                                if (!fixedBlock.get(y + yy).equals(fixedBlock.get(y - (yy + 1)))) {
                                    mirrored = false;
                                    break;
                                }
                            } catch (IndexOutOfBoundsException e) {

                            }
                        }

                        if (mirrored) {
                            if (!previousMirrors.get(blockIndex).equals("y="+y)) {
                                result += y * 100;
                            }
                        }

                    }

                    for (var x = 1; x < fixedBlock.getFirst().length(); x++) {
                        boolean mirrored = true;
                        for (var xx = 0; xx < fixedBlock.getFirst().length() - 1; xx++) {
                            try {
                                for (String s : fixedBlock) {
                                    if (s.charAt(x + xx) != s.charAt(x - (xx + 1))) {
                                        mirrored = false;
                                        break;
                                    }
                                }
                            } catch (StringIndexOutOfBoundsException e) {
                            }
                        }

                        if (mirrored) {
                            if (!previousMirrors.get(blockIndex).equals("x="+x)) {
                                result += x;
                            }
                        }
                    }
                }
            }

            blockIndex++;
        }

        return result / 2; // because the same smudge removal works on both mirror images and I was too lazy to fix it in the loop
    }

}
