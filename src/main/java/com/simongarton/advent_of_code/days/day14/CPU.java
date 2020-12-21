package com.simongarton.advent_of_code.days.day14;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CPU {
    private String mask;
    Map<Integer, Long> memory = new HashMap<>();

    public void processInstruction(String line) {
        String[] parts = line.split(" = ");
        if (parts[0].equalsIgnoreCase("mask")) {
            setMask(parts[1]);
        } else {
            int address = Integer.parseInt(parts[0].replace("mem[", "").replace("]", ""));
            setMaskedValue(address, Long.parseLong(parts[1]));
        }
    }

    public void processFloatyInstruction(String line) {
        String[] parts = line.split(" = ");
        if (parts[0].equalsIgnoreCase("mask")) {
            setMask(parts[1]);
        } else {
            int address = Integer.parseInt(parts[0].replace("mem[", "").replace("]", ""));
            List<Integer> addresses = processAddresses(address);
            long value = Long.parseLong(parts[1]);
            for (int newAddress : addresses) {
                memory.put(newAddress, value);
            }
            System.out.println(mask + " : " + addresses.size() + " / " + memory.size() + " = " + getTotalValue());
        }
    }

    public List<Integer> processAddresses(int originalAddress) {
        List<Integer> addresses = new ArrayList<>();
        addresses.add(originalAddress);
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            if (c == '0') {
                continue;
            }
            if (c == '1') {
                addresses = handleBit1(addresses, i);
            }
            if (c == 'X') {
                addresses = handleBitX(addresses, i);
            }
        }
        return addresses;
    }

    private List<Integer> handleBit1(List<Integer> originalAddresses, int i) {
        List<Integer> addresses = new ArrayList<>();
        for (int originalAddress : originalAddresses) {
            addresses.add(setBit1(originalAddress, i));
        }
        return addresses;
    }

    private int setBit1(int originalAddress, int i) {
        Integer36 integer36 = new Integer36(originalAddress);
        String bits = integer36.getBits();
        String newBits = bits.substring(0, i) + "1" + bits.substring(i + 1);
        return new Integer36(newBits).getIntValue();
    }

    private int setBitX(int originalAddress, int i, boolean setBit) {
        Integer36 integer36 = new Integer36(originalAddress);
        String bits = integer36.getBits();
        String newBits = bits.substring(0, i) + (setBit ? "1" : "0") + bits.substring(i + 1);
        return new Integer36(newBits).getIntValue();
    }

    private List<Integer> handleBitX(List<Integer> originalAddresses, int i) {
        List<Integer> addresses = new ArrayList<>();
        for (int originalAddress : originalAddresses) {
            addresses.add(setBitX(originalAddress, i, false));
            addresses.add(setBitX(originalAddress, i, true));
        }
        return addresses;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public void setMaskedValue(int address, long value) {
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

