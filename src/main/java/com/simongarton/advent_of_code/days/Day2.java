package com.simongarton.advent_of_code.days;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Day2 {

    private static String FILENAME = "data/day2.txt";

    public void run() {
        System.out.println("Day 2 - Password Philosophy\n");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Part 1 : " + countValid1(lines));
        System.out.println("Part 2 : " + countValid2(lines));
    }

    private int countValid1(List<String> lines) {
        int valid = 0;
        for (String line : lines) {
            if (validLine1(line)) {
                valid++;
            }
        }
        return valid;
    }

    private int countValid2(List<String> lines) {
        int valid = 0;
        for (String line : lines) {
            if (validLine2(line)) {
                valid++;
            }
        }
        return valid;
    }

    private boolean validLine1(String line) {
        String[] parts = line.split(" ");
        String[] ranges = parts[0].split("-");
        int low = Integer.parseInt(ranges[0]);
        int high = Integer.parseInt(ranges[1]);
        String letter = parts[1].replace(":", "");
        String target = parts[2];
        int count = count(target, letter);
        return count >= low && count <= high;
    }

    public boolean validLine2(String line) {
        String[] parts = line.split(" ");
        String[] ranges = parts[0].split("-");
        int low = Integer.parseInt(ranges[0]) - 1;
        int high = Integer.parseInt(ranges[1]) - 1;
        String letter = parts[1].replace(":", "");
        String target = parts[2];
        if (high >= target.length()) {
            return false;
        }
        int score = 0;
        if (target.substring(low, low + 1).equalsIgnoreCase(letter)) {
            score++;
        }
        if (target.substring(high, high + 1).equalsIgnoreCase(letter)) {
            score++;
        }
        return score == 1;
    }

    private int count(String input, String search) {
        return (input.length() - input.replace(search, "").length()) / search.length();
    }
}
