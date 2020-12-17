package com.simongarton.advent_of_code.days;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Day10 {

    private static String FILENAME = "data/day10-test.txt";
    private List<Adapter> adapters;
    private int combinations = 0;

    public void run() {
        System.out.println("Day 10: Adapter Array\n");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapters = lines.stream().map(Adapter::new).sorted(Comparator.comparing(Adapter::getRating)).collect(Collectors.toList());
        Map<Integer, Integer> differences = figureRatingOrder(adapters);
        int deviceAdapter = 3;
        if (!differences.containsKey(deviceAdapter)) {
            differences.put(deviceAdapter, 0);
        }
        differences.put(deviceAdapter, differences.get(deviceAdapter) + 1);
        for (Map.Entry<Integer, Integer> entry : differences.entrySet()) {
            System.out.println("difference " + entry.getKey() + " = count " + entry.getValue());
        }
        int maxRating = adapters.get(adapters.size() - 1).getRating();
        List<Adapter> usedAdapters = new ArrayList<>();
        //solveIteratively(adapters, maxRating, 0);
        combinations = 0;
        System.out.println(solveRecursively(usedAdapters, maxRating, 0));
    }

    private void solveIteratively(int maxRating, int rating) {
        Stack<Adapter> stack = new Stack<>();
        stack.push(adapters.get(0));
        while (stack.size() > 0) {
            Adapter a = stack.pop();
            for (Adapter availableAdapter : adapters) {
                if (availableAdapter.canAccept(a.rating)) {
                    stack.push(availableAdapter);
                }
            }
            testForFinish(a, stack, maxRating);
        }
    }

    private void testForFinish(Adapter adapter, Stack<Adapter> stack, int maxRating) {
        System.out.println("from " + adapter.getRating() + " : " + stack.stream().map(a -> Integer.toString(a.getRating())).collect(Collectors.joining(",")));
    }

    private int solveRecursively(List<Adapter> usedAdapters, int maxRating, int rating) {
        for (Adapter availableAdapter : adapters) {
            int oldRating = rating;
            if (usedAdapters.contains(availableAdapter)) {
                continue;
            }
            if (!availableAdapter.canAccept(rating)) {
                continue;
            }
            usedAdapters.add(availableAdapter);
            rating = availableAdapter.getRating();
            if (testForFinish(usedAdapters, maxRating)) {
                combinations++;
            }
            System.out.println("(0)," + usedAdapters.stream().map(a -> Integer.toString(a.getRating())).collect(Collectors.joining(",")) + " ...");
            solveRecursively(usedAdapters, maxRating, rating);
            usedAdapters.remove(availableAdapter);
            rating = oldRating;
        }
        return combinations;
    }

    private boolean testForFinish(List<Adapter> usedAdapters, int maxRating) {
        int lastRating = usedAdapters.get(usedAdapters.size() - 1).getRating();
        if (lastRating == maxRating) {
            System.out.println("(0)," + usedAdapters.stream().map(a -> Integer.toString(a.getRating())).collect(Collectors.joining(",")) + ",(" + (maxRating + 3) + ")");
            return true;
        }
        return false;
    }

    private Map<Integer, Integer> figureRatingOrder(List<Adapter> adapters) {
        int currentRating = 0;
        Map<Integer, Integer> difference = new HashMap<>();
        for (Adapter adapter : adapters) {
            if (!adapter.canAccept(currentRating)) {
                throw new RuntimeException("Can't add adapter " + adapter.getRating() + " at rating " + currentRating);
            }
            int diff = adapter.rating - currentRating;
            if (!difference.containsKey(diff)) {
                difference.put(diff, 0);
            }
            difference.put(diff, difference.get(diff) + 1);
            System.out.println("Added " + adapter.getRating() + " at " + currentRating);
            currentRating = adapter.rating;
        }
        return difference;
    }

    @Data
    public static class Adapter {

        private int rating;

        public Adapter(String rating) {
            this.rating = Integer.parseInt(rating);
        }

        public boolean canAccept(int currentRating) {
            int diff = rating - currentRating;
            return (diff >= 1 && diff <= 3);
        }
    }
}
