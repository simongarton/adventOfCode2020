package com.simongarton.advent_of_code.days;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Day7 {

    private static String FILENAME = "data/day7.txt";

    private List<Rule> rules = new ArrayList<>();

    public void run() {
        System.out.println("Day 7: Handy Haversacks\n");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadRules(lines);
        String type = "shiny gold";
        System.out.println("shiny gold" + " can be held by " + canHold(type) + " other bag types.");
        System.out.println("shiny gold" + " holds " + holds(type) + " other bags.");
    }

    private long canHold(String type) {
        return rules.stream().filter(r -> r.canHold(type, 0)).count();
    }

    private int holds(String type) {
        return getOrCreateRule(type).holdsOtherBags();
    }

    private void loadRules(List<String> lines) {
        lines.forEach(this::addRule);
        lines.forEach(this::configureRule);
    }

    private void configureRule(String line) {
        String working = line.replace("bags", "")
                .replace("bag", "")
                .replace(".", "")
                .trim();
        String[] parts = working.split("contain ");
        String type = parts[0].trim();
        String[] contains = parts[1].split(",");
        Rule outer = getOrCreateRule(type);
        for (String contain : contains) {
            String trimContain = contain.trim();
            if (trimContain.equalsIgnoreCase("no other")) {
                continue;
            }
            // assuming none > 9
            int count = Integer.parseInt(trimContain.substring(0, 1));
            String containType = trimContain.substring(2);
            Rule inner = getOrCreateRule(containType);
            outer.addCanContain(inner, count);
        }
    }

    private void addRule(String line) {
        String working = line.replace("bags", "")
                .replace("bag", "")
                .replace(".", "")
                .trim();
        String[] parts = working.split("contain ");
        String type = parts[0].trim();
        getOrCreateRule(type);
    }

    private Rule getOrCreateRule(String type) {
        Optional<Rule> optionalRule = rules.stream().filter(r -> r.getType().equalsIgnoreCase(type)).findFirst();
        if (optionalRule.isPresent()) {
            return optionalRule.get();
        }
        Rule rule = new Rule(type);
        rules.add(rule);
        return rule;
    }

    @Data
    public static class Rule {
        private String type;
        private Map<Rule, Integer> canContain = new HashMap<>();

        public Rule(String type) {
            this.type = type;
        }

        public void addCanContain(Rule rule, int count) {
            canContain.put(rule, count);
        }

        public boolean canHold(String bagType, int level) {
            if (bagType.equalsIgnoreCase(type) && level > 0) {
                return true;
            }
            for (Map.Entry<Rule, Integer> entry : canContain.entrySet()) {
                if (entry.getKey().canHold(bagType, level + 1)) {
                    return true;
                }
            }
            return false;
        }

        public int holdsOtherBags() {
            int holds = 0;
            for (Map.Entry<Rule, Integer> entry : canContain.entrySet()) {
                holds = holds + entry.getValue() + entry.getValue() * entry.getKey().holdsOtherBags();
            }
            return holds;
        }
    }
}
