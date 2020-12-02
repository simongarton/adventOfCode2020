package com.simongarton.advent_of_code;

import com.simongarton.advent_of_code.days.Day0;
import com.simongarton.advent_of_code.days.Day1;
import com.simongarton.advent_of_code.days.Day2;

public class Main {

    public static void main(String[] args) {
        //day0();
        //day1();
        day2();
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
