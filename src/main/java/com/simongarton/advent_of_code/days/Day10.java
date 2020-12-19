package com.simongarton.advent_of_code.days;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Day10 {

    private static String FILENAME = "data/day10-test-large.txt";
    private List<Adapter> adapters;
    private long combinations = 0;

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
            //System.out.println("difference " + entry.getKey() + " = count " + entry.getValue());
        }
        int maxRating = adapters.get(adapters.size() - 1).getRating();
        List<Adapter> usedAdapters = new ArrayList<>();
        combinations = 0;
        System.out.println("Recursive " + solveRecursively(adapters, usedAdapters, maxRating, 0));
        System.out.println("Chunks " + solveInChunks());
    }

    private long solveInChunks() {
        List<List<Adapter>> lists = new ArrayList<>();
        List<Adapter> workingList = new ArrayList<>();
        Adapter previous = null;
        System.out.println("Starting with  " + adapters.stream().map(a -> Integer.toString(a.getRating())).collect(Collectors.joining(",")));
        for (Adapter a : adapters) {
            if (workingList.isEmpty()) {
                workingList.add(a);
            } else {
                if (a.getRating() - previous.getRating() == 3) {
                    List<Adapter> subList = new ArrayList<>(workingList);
                    lists.add(subList);
                    workingList.clear();
                }
                workingList.add(a);
            }
            previous = a;
        }
        List<Adapter> subList = new ArrayList<>(workingList);
        lists.add(subList);
        long workingCombinations = 0;
        for (List<Adapter> list : lists) {
            combinations = 0;
            List<Adapter> usedAdapters = new ArrayList<>();
            int minRating = list.get(0).getRating();
            int maxRating = list.get(list.size() - 1).getRating();
            long thisCombinations = solveRecursively(list, usedAdapters, maxRating, minRating);
            System.out.println("Trying " + list.stream().map(a -> Integer.toString(a.getRating())).collect(Collectors.joining(",")) + " got " + thisCombinations);
            if (workingCombinations == 0) {
                workingCombinations = thisCombinations;
            } else {
                if (thisCombinations != 0) {
                    workingCombinations = workingCombinations * thisCombinations;
                }
            }
        }
        return workingCombinations;
    }

    private long solveRecursively(List<Adapter> availableAdapters, List<Adapter> usedAdapters, int maxRating, final int rating) {
        List<Adapter> possibleAdapters = availableAdapters.stream().filter(a -> a.canAccept(rating)).collect(Collectors.toList());
        int workingRating = rating;
        for (Adapter availableAdapter : possibleAdapters) {
            int oldRating = workingRating;
            if (usedAdapters.contains(availableAdapter)) {
                continue;
            }
            if (!availableAdapter.canAccept(workingRating)) {
                continue;
            }
            usedAdapters.add(availableAdapter);
            workingRating = availableAdapter.getRating();
            if (testForFinish(usedAdapters, maxRating)) {
                combinations++;
            } else {
                //System.out.println("(0)," + usedAdapters.stream().map(a -> Integer.toString(a.getRating())).collect(Collectors.joining(",")) + " ...");
            }
            solveRecursively(availableAdapters, usedAdapters, maxRating, workingRating);
            usedAdapters.remove(availableAdapter);
            workingRating = oldRating;
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
            //System.out.println("Added " + adapter.getRating() + " at " + currentRating);
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
