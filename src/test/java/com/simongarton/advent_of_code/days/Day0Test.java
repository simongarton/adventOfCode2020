package com.simongarton.advent_of_code.days;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Day0Test {

    private Day0 day0;

    @BeforeEach
    void setup() {
        day0 = new Day0();
    }

    @Test
    void run() {
        assertTrue(day0.run());
    }
}