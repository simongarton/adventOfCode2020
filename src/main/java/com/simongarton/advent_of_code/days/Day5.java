package com.simongarton.advent_of_code.days;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Day5 {

    private static String FILENAME = "data/day5.txt";
    private static boolean DEBUG = false;

    public void run() {
        System.out.println("Day 5: Binary Boarding\n");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        long maxId = 0;
        Map<Long, String> ids = new HashMap<>();
        for (String line : lines) {
            long id = this.analysePass(line);
            if (id > maxId) {
                maxId = id;
            }
            ids.put(id, line);
        }
        System.out.println("max " + maxId);
        for (long l = 1; l < maxId - 1; l++) {
            if ((ids.get(l - 1) != null) &&
                    (ids.get(l) == null) &&
                    (ids.get(l + 1) != null)) {
                System.out.println(l + " : no pass.");
            }
        }
    }

    private long analysePass(String line) {
        long row = binarySplit(line, 7, 0, 0, 127, "F", "B");
        long col = binarySplit(line, 3, 7, 0, 7, "L", "R");
        return ((row * 8) + col);
    }

    private long binarySplit(String line, int limit, int offset, long min, long max, String lower, String upper) {
        for (int i = 0; i < limit; i++) {
            long range = (max - min) / 2;
            if (line.substring(offset + i, offset + i + 1).equalsIgnoreCase(lower)) {
                max = min + range;
            } else {
                min = min + range + 1;
            }
        }
        return min;
    }
}
