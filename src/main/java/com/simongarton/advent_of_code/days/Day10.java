package com.simongarton.advent_of_code.days;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Day10 {

    private static String FILENAME = "data/day10.txt";
    private List<Adapter> adapters;
    private long combinations = 0;

    /*

    The second part caused issues with large files - recursion takes too long. I struggled for
    a bit and then went and read through some of the solutions in the subreddit for ideas.
    There were big clues being given by the challenge : the gaps were either 1 or 3 jolts, never 2.
    There are various approaches being taken in the subreddit. One of them - which I've now abandoned -
    looked at breaking the entire chain into chunks where the gap was 3 jolts - all paths will converge at these gaps.
    So if you can break the whole chain into 2 chunks; you need to find the number of unique paths in each chunk; and then
    multiply the two together.
    However, a more elegant solution using dynamic programming and memos to cache the results. The number of paths to any adapter
    will be the sum of the paths to up to 3 previous adapters that it can be reached by.
    So if I have this chain of adapters :

    0  1  2  3  5  8  9  10
    1  1                        # I can get to 1 from 0, so I store 1 against 1
    1  1  2                     # I can get to 2 from 0 or 1, so I store 2 against 2
    1  1  2  4                  # I can get to 3 once from 0 one from 1 and two ways from 2, so I store 4 against 3
          2  4  6               # I can get to 5 two ways from 2 and four ways from 3, so I store 6 against 5
                6  6            # I can only get to 8 the same six ways as I got to 5 ...
                   6  6         # ... and only to 9 the same six ways again ...
                   6  6  12     # ... but I can get to 10 six ways from 8 and six ways from 9, hence 12.

     */

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
        System.out.println("Part 1\n");
        for (Map.Entry<Integer, Integer> entry : differences.entrySet()) {
            System.out.println("  difference " + entry.getKey() + " = count " + entry.getValue());
        }
        int maxRating = adapters.get(adapters.size() - 1).getRating();
        List<Adapter> usedAdapters = new ArrayList<>();
        combinations = 0;
        System.out.println("\nPart 2\n");
        if (adapters.size() <= 20) {
            System.out.println("Recursive solution : " + solveRecursively(usedAdapters, maxRating, 0));
        }
        System.out.println("Memo solution : " + solveWithMemos());
    }

    private long solveWithMemos() {
        Map<Integer, Long> memo = new HashMap<>();
        memo.put(0, 1L);
        for (Adapter adapter : adapters) {
            int rating = adapter.getRating();
            long a = memo.containsKey(rating - 1) ? memo.get(rating - 1) : 0;
            long b = memo.containsKey(rating - 2) ? memo.get(rating - 2) : 0;
            long c = memo.containsKey(rating - 3) ? memo.get(rating - 3) : 0;
            memo.put(rating, a + b + c);
        }
        return memo.get(adapters.get(adapters.size() - 1).getRating());
    }

    private long solveRecursively(List<Adapter> usedAdapters, int maxRating, final int rating) {
        List<Adapter> possibleAdapters = adapters.stream().filter(a -> a.canAccept(rating)).collect(Collectors.toList());
        int workingRating = rating;
        for (Adapter possibleAdapter : possibleAdapters) {
            int oldRating = workingRating;
            if (usedAdapters.contains(possibleAdapter)) {
                continue;
            }
            if (!possibleAdapter.canAccept(workingRating)) {
                continue;
            }
            usedAdapters.add(possibleAdapter);
            workingRating = possibleAdapter.getRating();
            if (testForFinish(usedAdapters, maxRating)) {
                combinations++;
            }
            solveRecursively(usedAdapters, maxRating, workingRating);
            usedAdapters.remove(possibleAdapter);
            workingRating = oldRating;
        }
        return combinations;
    }

    private boolean testForFinish(List<Adapter> usedAdapters, int maxRating) {
        int lastRating = usedAdapters.get(usedAdapters.size() - 1).getRating();
        if (lastRating == maxRating) {
            //System.out.println("(0)," + usedAdapters.stream().map(a -> Integer.toString(a.getRating())).collect(Collectors.joining(",")) + ",(" + (maxRating + 3) + ")");
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
            currentRating = adapter.rating;
        }
        return difference;
    }

    @Data
    private static class Adapter {

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
