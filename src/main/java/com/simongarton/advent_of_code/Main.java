package com.simongarton.advent_of_code;

import com.simongarton.advent_of_code.days.*;

public class Main {

    public static void main(String[] args) {
        //day0();
        //day1();
        //day2();
        //day3();
        //day4();
        //day5();
        //day6();
        //day7();
        //day8();
        //day9();
        //day10();
        //day11();
        //day12();
        //day13();
        day14();
    }

    private static void day14() {
        Day14 day14 = new Day14();
        day14.run();
    }

    private static void day13() {
        Day13 day13 = new Day13();
        day13.run();
    }

    private static void day12() {
        Day12 day12 = new Day12();
        day12.run();
    }

    private static void day11() {
        Day11 day11 = new Day11();
        day11.run();
    }

    private static void day10() {
        Day10 day10 = new Day10();
        day10.run();
    }

    private static void day9() {
        Day9 day9 = new Day9();
        day9.run();
    }

    private static void day8() {
        Day8 day8 = new Day8();
        day8.run();
    }

    private static void day7() {
        Day7 day7 = new Day7();
        day7.run();
    }

    private static void day6() {
        Day6 day6 = new Day6();
        day6.run();
    }

    private static void day5() {
        Day5 day5 = new Day5();
        day5.run();
    }

    private static void day4() {
        Day4 day4 = new Day4();
        day4.run();
    }

    private static void day3() {
        Day3 day3 = new Day3();
        day3.run();
    }

    private static void day2() {
        Day2 day2 = new Day2();
        day2.run();
    }

    private static void day1() {
        Day1 day1 = new Day1();
        long result1 = day1.runPart1();
        System.out.println("day 1 part 1: " + result1);
        long result2 = day1.runPart2();
        System.out.println("day 1 part 2 : " + result2);
    }

    private static void day0() {
        Day0 day0 = new Day0();
        boolean result1 = day0.run();
        System.out.println("day 0 : " + result1);
    }
}
