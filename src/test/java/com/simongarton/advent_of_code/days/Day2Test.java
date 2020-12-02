package com.simongarton.advent_of_code.days;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {

    private Day2 day2;

    @BeforeEach
    void setup() {
        day2 = new Day2();
    }

    @Test
    void validLine2() {
        assertTrue(day2.validLine2("1-3 a: abcde"));
        assertFalse(day2.validLine2("1-3 b: cdefg"));
        assertFalse(day2.validLine2("2-9 c: ccccccccc"));
    }
}