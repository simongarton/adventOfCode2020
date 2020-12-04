package com.simongarton.advent_of_code.days;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Day4 {

    private static String FILENAME = "data/day4.txt";
    private static boolean DEBUG = false;

    public void run() {
        System.out.println("Day 4: Passport Processing\n");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> section = new ArrayList<>();
        List<Passport> passports = new ArrayList<>();
        for (String line : lines) {
            if (line.equalsIgnoreCase("")) {
                Passport passport = new Passport(section);
                passports.add(passport);
                section.clear();
            } else {
                section.add(line);
            }
        }
        if (section.size() > 0) {
            Passport passport = new Passport(section);
            passports.add(passport);
            section.clear();
        }
        System.out.println("Valid : " + passports.stream().filter(Passport::valid).count());
    }

    @Data
    public static class Passport {

        // could be done more efficently as a map
        private String byr; // (Birth Year)
        private String iyr; // (Issue Year)
        private String eyr; // (Expiration Year)
        private String hgt; // (Height)
        private String hcl; // (Hair Color)
        private String ecl; // (Eye Color)
        private String pid; // (Passport ID)
        private String cid; // (Country ID)

        public Passport(List<String> lines) {
            for (String line : lines) {
                processLine(line);
            }
        }

        private void processLine(String line) {
            String[] parts = line.split(" ");
            for (String part : parts) {
                processPart(part);
            }
        }

        private boolean valid() {
            if (byr == null) return false;
            if (iyr == null) return false;
            if (eyr == null) return false;
            if (hgt == null) return false;
            if (hcl == null) return false;
            if (ecl == null) return false;
            if (pid == null) return false;
            return true;
        }

        private void processPart(String part) {
            String[] details = part.split(":");
            String key = details[0];
            String value = details[1];
            switch (key) {
                case "byr":
                    byr = value;
                    break;
                case "iyr":
                    iyr = value;
                    break;
                case "eyr":
                    eyr = value;
                    break;
                case "hgt":
                    hgt = value;
                    break;
                case "hcl":
                    hcl = value;
                    break;
                case "ecl":
                    ecl = value;
                    break;
                case "pid":
                    pid = value;
                    break;
                case "cid":
                    cid = value;
                    break;
            }
        }
    }
}
