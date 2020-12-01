package com.simongarton.advent_of_code.days;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class Day1 {

    private static String FILENAME = "data/day1.txt";

    public long runPart1() {
        List<Long> longList = getLongs();
        long start = System.currentTimeMillis();
        Result result1 = part1AsList(longList);
        System.out.println("part1AsList result " + result1 + " in " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        Result result2 = part1AsSet(longList);
        System.out.println("part1AsSet result " + result2 + " in " + (System.currentTimeMillis() - start));
        return result1.multiplied();
    }

    private Result part1AsList(List<Long> longList) {
        Collections.sort(longList);
        for (Long first : longList) {
            for (Long second : longList) {
                if (first + second == 2020) {
                    return new Result(first, second, null);
                }
            }
        }
        throw new RuntimeException("no luck.");
    }

    private Result part1AsSet(List<Long> longList) {
        Set<Long> longSet = new HashSet<>(longList);
        for (Long first : longList) {
            long second = 2020 - first;
            if (longSet.contains(second)) {
                return new Result(first, second, null);
            }
        }
        throw new RuntimeException("no luck.");
    }

    private List<Long> getLongs() {
        List<Long> longList = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(FILENAME))) {
            longList = lines.mapToLong(Long::new).boxed().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return longList;
    }

    public long runPart2() {
        List<Long> longList = getLongs();
        long start = System.currentTimeMillis();
        Result result1 = part2AsList(longList);
        System.out.println("part2AsList result " + result1 + " in " + (System.currentTimeMillis() - start));
        return result1.multiplied();
    }

    private Result part2AsList(List<Long> longList) {
        Collections.sort(longList);
        for (Long first : longList) {
            for (Long second : longList) {
                for (Long third : longList) {
                    if (first + second + third == 2020) {
                        return new Result(first, second, third);
                    }
                }
            }
        }
        throw new RuntimeException("no luck.");
    }

    @Data
    @AllArgsConstructor
    private static class Result {
        private Long first;
        private Long second;
        private Long third;

        public long multiplied() {
            if (third == null) {
                return first * second;
            } else {
                return first * second * third;
            }
        }

        @Override
        public String toString() {
            return "Result{" +
                    "first=" + first +
                    ", second=" + second +
                    (third == null ? "" : " third=" + third) +
                    ", multiplied=" + multiplied() +
                    '}';
        }
    }
}
