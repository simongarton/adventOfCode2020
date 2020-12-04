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
}
