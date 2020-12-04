package com.simongarton.advent_of_code.days;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Day3 {

    private static String FILENAME = "data/day3.txt";
    private static boolean DEBUG = false;

    public void run() {
        System.out.println("Day 3: Toboggan Trajectory\n");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Part 1 : " + countTrees(lines, 3, 1) + " trees.");
        long totalTrees = countTrees(lines, 1, 1);
        totalTrees = totalTrees * countTrees(lines, 3, 1);
        totalTrees = totalTrees * countTrees(lines, 5, 1);
        totalTrees = totalTrees * countTrees(lines, 7, 1);
        totalTrees = totalTrees * countTrees(lines, 1, 2);
        System.out.println("Part 2 : " + totalTrees + " trees.");
    }

    private int countTrees(List<String> lines, int right, int down) {
        int row = 0;
        int trees = 0;
        int col = 0;
        if (DEBUG) {
            System.out.println(lines.get(0));
        }
        while (true) {
            row = row + down;
            if (row >= lines.size()) {
                break;
            }
            col = col + right;
            String line = lines.get(row);
            String plot = line.substring(col % line.length(), col % line.length() + 1);
            String what = "O";
            if (plot.equalsIgnoreCase("#")) {
                trees++;
                what = "X";
            }
            StringBuilder output = new StringBuilder();
            while (output.length() <= col) {
                output.append(line);
            }
            if (DEBUG) {
                String result = output.substring(0, col) + what + output.substring(col + 1);
                System.out.println(result);
            }
        }
        return trees;
    }
}
