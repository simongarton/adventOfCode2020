package com.simongarton.advent_of_code.days;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Day6 {

    private static String FILENAME = "data/day6.txt";
    private static boolean DEBUG = false;

    public void run() {
        System.out.println("Day 6: Custom Customs\n");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        part1(lines);
        part2(lines);
    }

    private void part1(List<String> lines) {
        int total = 0;
        Set<String> answers = new HashSet<>();
        for (String line : lines) {
            if (line.length() == 0) {
                total += answers.size();
                answers.clear();
                continue;
            }
            for (int i = 0; i < line.length(); i++) {
                answers.add(line.substring(i, i + 1));
            }
        }
        total += answers.size();
        answers.clear();
        System.out.println("Total part 1 : " + total);
    }

    private void part2(List<String> lines) {
        int total = 0;
        int people = 0;
        Map<String, Integer> answers = new HashMap<>();
        for (String line : lines) {
            if (line.length() == 0) {
                int group = 0;
                for (Map.Entry<String, Integer> entry : answers.entrySet()) {
                    if (entry.getValue().equals(people)) {
                        group += 1;
                    }
                }
                total += group;
                answers.clear();
                people = 0;
                continue;
            }
            for (int i = 0; i < line.length(); i++) {
                String key = line.substring(i, i + 1);
                if (!answers.containsKey(key)) {
                    answers.put(key, 1);
                } else {
                    answers.put(key, answers.get(key) + 1);
                }
            }
            people = people + 1;
        }
        int group = 0;
        for (Map.Entry<String, Integer> entry : answers.entrySet()) {
            if (entry.getValue().equals(people)) {
                group += 1;
            }
        }
        total += group;
        answers.clear();
        System.out.println("Total part 2 : " + total);
    }
}
