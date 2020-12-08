package com.simongarton.advent_of_code.days;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.simongarton.advent_of_code.days.Day8.InstructionType.*;

@Data
public class Day8 {

    private static String FILENAME = "data/day8.txt";

    private Computer computer;

    public void run() {
        System.out.println("Day 8: Handheld Halting\n");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        buildComputer(lines);
        boolean outcome = computer.run();
        if (outcome) {
            System.out.println("Part 1 : terminated normally with accumulator " + computer.accumulator);
        } else {
            System.out.println("Part 1 : stuck in loop with accumulator " + computer.accumulator);
        }
        computer.findBadInstruction(lines);
    }

    private void buildComputer(List<String> lines) {
        computer = new Computer();
        computer.loadProgram(lines);
    }

    public enum InstructionType {
        NOP,
        ACC,
        JMP
    }

    @Getter
    @AllArgsConstructor
    public static class Instruction {

        private InstructionType type;
        private final int value;
    }

    public static class Computer {

        private final List<Instruction> instructions;
        private int pointer;
        private int accumulator;
        private final Set<Integer> visited;

        public Computer() {
            instructions = new ArrayList<>();
            visited = new HashSet<>();
        }

        public boolean run() {
            while (true) {
                if (pointer >= instructions.size()) {
                    //System.out.printf("finished with p %d a %d\n", pointer, accumulator);
                    return true;
                }
                Instruction instruction = instructions.get(pointer);
                //System.out.printf("p %d a %d i %s v %d\n", pointer, accumulator, instruction.type, instruction.value);
                if (visited.contains(pointer)) {
                    //System.out.printf("loop with p %d a %d\n", pointer, accumulator);
                    return false;
                }
                visited.add(pointer);
                switch (instruction.type) {
                    case NOP:
                        pointer++;
                        break;
                    case ACC:
                        accumulator += instruction.value;
                        pointer++;
                        break;
                    case JMP:
                        pointer += instruction.value;
                        break;
                }
            }
        }

        public void findBadInstruction(List<String> lines) {
            test(lines, NOP, JMP);
            test(lines, JMP, NOP);
        }

        private void test(List<String> lines, InstructionType from, InstructionType to) {
            for (int i = 0; i < lines.size(); i++) {
                loadProgram(lines);
                Instruction instruction = instructions.get(i);
                if (instruction.type != from) {
                    continue;
                }
                instruction.type = to;
                boolean outcome = run();
                if (outcome) {
                    System.out.println("Part 2 : terminated normally changing " + i + " to " + to + " with accumulator " + accumulator);
                }
            }
        }

        public void loadProgram(List<String> lines) {
            instructions.clear();
            visited.clear();
            lines.forEach(this::processLine);
            pointer = 0;
            accumulator = 0;
        }

        private void processLine(String line) {
            String[] parts = line.split(" ");
            int value = Integer.parseInt(parts[1]);
            switch (parts[0]) {
                case "nop":
                    instructions.add(new Instruction(NOP, value));
                    break;
                case "acc":
                    instructions.add(new Instruction(ACC, value));
                    break;
                case "jmp":
                    instructions.add(new Instruction(JMP, value));
                    break;
                default:
                    throw new RuntimeException("unrecognised instruction " + parts[0]);
            }
        }
    }
}
