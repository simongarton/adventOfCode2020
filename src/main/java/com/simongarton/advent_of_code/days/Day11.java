package com.simongarton.advent_of_code.days;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Day11 {

    private static String FILENAME = "data/day11.txt";

    public void run() {
        System.out.println("Day 11: Seating System\n");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Part 1 : " + runToSolution(lines, 1));
        System.out.println("Part 2 : " + runToSolution(lines, 2));
    }

    private int runToSolution(List<String> lines, int part) {
        int iterations = 0;
        List<String> working = new ArrayList<>(lines);
        //print(0, working);
        while (true) {
            List<String> updated = new ArrayList<>(working);
            updated = part == 1 ? update1(updated) : update2(updated);
            iterations++;
            if (noChange(working, updated)) {
                break;
            }
            working = new ArrayList<>(updated);
            //print(iterations, working);
        }
        return countOccupied(working);
    }

    private int countOccupied(List<String> current) {
        int occupied = 0;
        for (String s : current) {
            for (int col = 0; col < s.length(); col++) {
                if (s.substring(col, col + 1).equalsIgnoreCase("#")) {
                    occupied++;
                }
            }
        }
        return occupied;
    }

    private void print(int iterations, List<String> working) {
        System.out.println("iteration " + iterations + "\n");
        working.forEach(System.out::println);
        System.out.println("");
    }

    private List<String> update1(List<String> current) {
        List<String> updated = new ArrayList<>();
        for (int row = 0; row < current.size(); row++) {
            StringBuilder newRow = new StringBuilder();
            for (int col = 0; col < current.get(row).length(); col++) {
                String currentSeat = current.get(row).substring(col, col + 1);
                String replaceSeat = ".";
                int adjacent = countAdjacent(current, row, col);
                if (currentSeat.equalsIgnoreCase("L")) {
                    replaceSeat = adjacent == 0 ? "#" : "L";
                }
                if (currentSeat.equalsIgnoreCase("#")) {
                    replaceSeat = adjacent >= 4 ? "L" : "#";
                }
                newRow.append(replaceSeat);
            }
            updated.add(newRow.toString());
        }
        return updated;
    }

    private List<String> update2(List<String> current) {
        List<String> updated = new ArrayList<>();
        for (int row = 0; row < current.size(); row++) {
            StringBuilder newRow = new StringBuilder();
            for (int col = 0; col < current.get(row).length(); col++) {
                String currentSeat = current.get(row).substring(col, col + 1);
                String replaceSeat = ".";
                int adjacent = countVisible(current, row, col);
                if (currentSeat.equalsIgnoreCase("L")) {
                    replaceSeat = adjacent == 0 ? "#" : "L";
                }
                if (currentSeat.equalsIgnoreCase("#")) {
                    replaceSeat = adjacent >= 5 ? "L" : "#";
                }
                newRow.append(replaceSeat);
            }
            updated.add(newRow.toString());
        }
        return updated;
    }

    private int countAdjacent(List<String> current, int row, int col) {
        int neighbours = 0;
        neighbours += countNeighbour(current, row + 1, col + 1);
        neighbours += countNeighbour(current, row + 1, col + 0);
        neighbours += countNeighbour(current, row + 1, col - 1);
        neighbours += countNeighbour(current, row + 0, col + 1);
        neighbours += countNeighbour(current, row + 0, col - 1);
        neighbours += countNeighbour(current, row - 1, col + 1);
        neighbours += countNeighbour(current, row - 1, col + 0);
        neighbours += countNeighbour(current, row - 1, col - 1);
        return neighbours;
    }

    private int countVisible(List<String> current, int row, int col) {
        int neighbours = 0;
        neighbours += countVisibleNeighbour(current, row, col, 1, 1);
        neighbours += countVisibleNeighbour(current, row, col, 1, 0);
        neighbours += countVisibleNeighbour(current, row, col, 1, -1);
        neighbours += countVisibleNeighbour(current, row, col, 0, 1);
        neighbours += countVisibleNeighbour(current, row, col, 0, -1);
        neighbours += countVisibleNeighbour(current, row, col, -1, 1);
        neighbours += countVisibleNeighbour(current, row, col, -1, 0);
        neighbours += countVisibleNeighbour(current, row, col, -1, -1);
        return neighbours;
    }

    private int countVisibleNeighbour(List<String> current, int row, int col, int rowDelta, int colDelta) {
        while (true) {
            row = row + rowDelta;
            col = col + colDelta;
            if (row < 0 || row >= current.size()) {
                return 0;
            }
            String line = current.get(row);
            if (col < 0 || col >= line.length()) {
                return 0;
            }
            String seat = line.substring(col, col + 1);
            if (seat.equalsIgnoreCase(".")) {
                continue;
            }
            return seat.equalsIgnoreCase("#") ? 1 : 0;
        }
    }

    private int countNeighbour(List<String> current, int row, int col) {
        if (row < 0 || row >= current.size()) {
            return 0;
        }
        String line = current.get(row);
        if (col < 0 || col >= line.length()) {
            return 0;
        }
        return line.substring(col, col + 1).equalsIgnoreCase("#") ? 1 : 0;
    }

    private boolean noChange(List<String> working, List<String> updated) {
        if (working.size() != updated.size()) {
            throw new RuntimeException("Wrong number of rows");
        }
        for (int i = 0; i < working.size(); i++) {
            if (working.get(i).length() != updated.get(i).length()) {
                throw new RuntimeException("Wrong number of cols at line " + i);
            }
            if (!working.get(i).equalsIgnoreCase(updated.get(i))) {
                return false;
            }
        }
        return true;
    }
}
