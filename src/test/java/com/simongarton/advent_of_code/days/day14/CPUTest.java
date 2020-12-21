package com.simongarton.advent_of_code.days.day14;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {

    @Test
    void processAddresses() {

        CPU cpu = new CPU();
        cpu.setMask("000000000000000000000000000000000000");
        List<Integer> addresses = cpu.processAddresses(1);
        assertEquals(1, addresses.size());
        assertEquals(1, addresses.get(0));
        cpu.setMask("000000000000000000000000000000000010");
        addresses = cpu.processAddresses(1);
        assertEquals(1, addresses.size());
        assertEquals(3, addresses.get(0));
        cpu.setMask("0000000000000000000000000000000000X0");
        addresses = cpu.processAddresses(1);
        assertEquals(2, addresses.size());
        assertEquals(1, addresses.get(0));
        assertEquals(3, addresses.get(1));
    }
}