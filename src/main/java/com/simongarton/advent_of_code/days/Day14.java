package com.simongarton.advent_of_code.days;

import com.simongarton.advent_of_code.days.day14.CPU;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Day14 {

    private static String FILENAME = "data/day14.txt";

    public void run() {
        System.out.println("Day 14: Docking Data\n");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CPU cpu = new CPU();
        lines.forEach(cpu::processInstruction);
        System.out.println("Part 1 total value = " + cpu.getTotalValue());
        cpu = new CPU();
        lines.forEach(cpu::processFloatyInstruction);
        System.out.println("Part 2 total value = " + cpu.getTotalValue());
    }
}
