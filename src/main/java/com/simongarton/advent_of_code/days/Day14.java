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
    }

    @Data
    public static class CPU {

        private String mask;
        Map<Integer, Long> memory = new HashMap<>();

        public void processInstruction(String line) {
            String[] parts = line.split(" = ");
            if (parts[0].equalsIgnoreCase("mask")) {
                setMask(parts[1]);
            } else {
                int address = Integer.parseInt(parts[0].replace("mem[", "").replace("]", ""));
                setValue(address, Long.parseLong(parts[1]));
            }
        }

        public void setMask(String mask) {
            this.mask = mask;
        }

        public void setValue(int address, long value) {
            long newValue = applyMask(value);
            memory.put(address, newValue);
        }

        private long applyMask(long value) {
            Integer36 integer36 = new Integer36(value);
            String bitsValue = integer36.getBits();
            StringBuilder newValue = new StringBuilder();
            for (int i = 0; i < 36; i++) {
                if (mask.substring(i, i + 1).equalsIgnoreCase("X")) {
                    newValue.append(bitsValue.charAt(i));
                } else {
                    newValue.append(mask.charAt(i));
                }
            }
            Integer36 result = new Integer36(newValue.toString());
            return result.getValue();
        }

        public long getTotalValue() {
            return memory.values().stream().reduce(0L, Long::sum);
        }
    }
}
