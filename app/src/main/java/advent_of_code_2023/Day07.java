package advent_of_code_2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Day07 {

    static class Card implements Comparable<Card> {
        List<Integer> cards;
        String input;
        long bet;

        int prim;
        int sec;

        int score;

        Card(String input, long bet) {
            this.input = input;
            this.bet = bet;

            cards = Arrays.stream(input.split(""))
                    .map(s -> {
                        if (s.equals("T")) {
                            return 10;
                        }
                        if (s.equals("J")) {
                            return 11;
                        }
                        if (s.equals("Q")) {
                            return 12;
                        }
                        if (s.equals("K")) {
                            return 13;
                        }
                        if (s.equals("A")) {
                            return 14;
                        }
                        return Integer.parseInt(s);
                    })
                    .toList();

            var temp = new HashMap<Character, Integer>();

            for (char c : input.toCharArray()) {
                temp.put(c, 1 + temp.getOrDefault(c, 0));
            }

            for (var v : temp.values()) {
                if (v >= prim && v > 1) {
                    sec = prim;
                    prim = v;
                } else if (v >= sec && v > 1) {
                    sec = v;
                }
            }

            if (prim == 5) {
                score = 7;
            } else if (prim == 4) {
                score = 6;
            } else if (prim == 3 && sec == 2) {
                score = 5;
            } else if (prim == 3) {
                score = 4;
            } else if (prim == 2 && sec == 2) {
                score = 3;
            } else if (prim == 2) {
                score = 2;
            } else {
                score = 1;
            }
        }

        @Override
        public String toString() {
            return "Card{" +
                    "cards=" + cards +
                    ", input='" + input + '\'' +
                    ", bet=" + bet +
                    ", prim=" + prim +
                    ", sec=" + sec +
                    ", score=" + score +
                    '}';
        }

        @Override
        public int compareTo(Card o) {
            int scoreDiff = score - o.score;
            if (scoreDiff != 0) {
                return scoreDiff;
            }

            for (var i = 0; i < cards.size(); i++) {
                int diff = cards.get(i) - o.cards.get(i);

                if (diff != 0) {
                    return diff;
                }
            }

            throw new IllegalStateException(":(");
        }
    }

    static class Card2 implements Comparable<Card2> {
        List<Integer> cards;
        String input;
        long bet;

        int prim;
        int sec;

        int score;

        Card2(String input, long bet) {
            this.input = input;
            this.bet = bet;

            cards = Arrays.stream(input.split(""))
                    .map(s -> {
                        if (s.equals("T")) {
                            return 10;
                        }
                        if (s.equals("J")) {
                            return 1;
                        }
                        if (s.equals("Q")) {
                            return 12;
                        }
                        if (s.equals("K")) {
                            return 13;
                        }
                        if (s.equals("A")) {
                            return 14;
                        }
                        return Integer.parseInt(s);
                    })
                    .toList();

            var temp = new HashMap<Character, Integer>();

            for (char c : input.toCharArray()) {
                temp.put(c, 1 + temp.getOrDefault(c, 0));
            }

            for (var e : temp.entrySet()) {
                if (!e.getKey().equals('J')) {
                    var v = e.getValue();
                    if (v >= prim && v > 1) {
                        sec = prim;
                        prim = v;
                    } else if (v >= sec && v > 1) {
                        sec = v;
                    }
                }
            }

            if (temp.getOrDefault('J', 0) == 5) {
                prim = 5;
            } else if (prim == 0) {
                if (temp.containsKey('J')) {
                    prim = 1 + temp.get('J');
                }
            } else {
                prim += temp.getOrDefault('J', 0);
            }

            if (prim == 5) {
                score = 7;
            } else if (prim == 4) {
                score = 6;
            } else if (prim == 3 && sec == 2) {
                score = 5;
            } else if (prim == 3) {
                score = 4;
            } else if (prim == 2 && sec == 2) {
                score = 3;
            } else if (prim == 2) {
                score = 2;
            } else {
                score = 1;
            }
        }

        @Override
        public String toString() {
            return "Card{" +
                    "cards=" + cards +
                    ", input='" + input + '\'' +
                    ", bet=" + bet +
                    ", prim=" + prim +
                    ", sec=" + sec +
                    ", score=" + score +
                    '}';
        }

        @Override
        public int compareTo(Card2 o) {
            int scoreDiff = score - o.score;
            if (scoreDiff != 0) {
                return scoreDiff;
            }

            for (var i = 0; i < cards.size(); i++) {
                int diff = cards.get(i) - o.cards.get(i);

                if (diff != 0) {
                    return diff;
                }
            }

            throw new IllegalStateException(":(");
        }
    }

    static long part1(String[] input) {
        var cards = new PriorityQueue<Card>();

        for (var line : input) {
            var part = line.split(" ");
            cards.add(new Card(part[0], Integer.parseInt(part[1])));
        }


        long sum = 0;
        long i = 1;

        while (!cards.isEmpty()) {
            sum += i++ * cards.poll().bet;
        }

        return sum;
    }

    static long part2(String[] input) {
        var cards = new PriorityQueue<Card2>();

        for (var line : input) {
            var part = line.split(" ");
            cards.add(new Card2(part[0], Integer.parseInt(part[1])));
        }


        long sum = 0;
        long i = 1;

        while (!cards.isEmpty()) {
            sum += i++ * cards.poll().bet;
        }

        return sum;
    }

}
