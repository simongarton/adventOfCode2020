package com.simongarton.advent_of_code.days;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Day9 {

    private static String TEST_FILENAME = "data/day9-test.txt";
    private static int TEST_LENGTH = 5;
    private static String FILENAME = "data/day9.txt";
    private static int LENGTH = 25;

    public void run() {
        System.out.println("Day 9: Encoding Error\n");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(TEST_FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Long> numbers = lines.stream().map(Long::parseLong).collect(Collectors.toList());
        long error = findError(numbers, TEST_LENGTH);
        System.out.println("error = " + error);
        System.out.println("weakness = " + findWeakness(error, numbers));
        System.out.println();
        try {
            lines = Files.lines(Paths.get(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        numbers = lines.stream().map(Long::parseLong).collect(Collectors.toList());
        error = findError(numbers, LENGTH);
        System.out.println("error = " + error);
        System.out.println("weakness = " + findWeakness(error, numbers));
    }

    private long findWeakness(long target, List<Long> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                long total = 0;
                long lowest = numbers.get(i);
                long highest = 0;
                for (int k = i; k < j; k++) {
                    total += numbers.get(k);
                    if (lowest > numbers.get(k)) {
                        lowest = numbers.get(k);
                    }
                    if (highest < numbers.get(k)) {
                        highest = numbers.get(k);
                    }
                }
                if (total == target) {
                    return lowest + highest;
                }
            }
        }
        return 0L;
    }

    private long findError(List<Long> data, int length) {
        List<Long> numbers = new ArrayList<>();
        int index = 0;
        while (index < length) {
            numbers.add(data.get(index));
            index++;
        }
        while (index < data.size()) {
            long next = data.get(index);
            if (!valid(next, numbers)) {
                return next;
            }
            index++;
            numbers.remove(0);
            numbers.add(next);
        }
        return -1;
    }

    private boolean valid(long next, List<Long> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < numbers.size(); j++) {
                if (i == j) {
                    continue;
                }
                if (next == numbers.get(i) + numbers.get(j)) {
                    return true;
                }
            }
        }
        return false;
    }
}
