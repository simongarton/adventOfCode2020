package com.simongarton.advent_of_code.days;

import com.simongarton.advent_of_code.days.day14.Integer36;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Integer36Test {

    @Test
    void getBits() {
        Integer36 integer36 = new Integer36(1);
        assertEquals("000000000000000000000000000000000001", integer36.getBits());

        integer36 = new Integer36(15);
        assertEquals("000000000000000000000000000000001111", integer36.getBits());
    }

    @Test
    void getValue() {
        Integer36 integer36 = new Integer36(1);
        assertEquals(1L, integer36.getValue());

        integer36 = new Integer36("000000000000000000000000000000000001");
        assertEquals(1L, integer36.getValue());

        integer36 = new Integer36("000000000000000000000000000000001111");
        assertEquals(15L, integer36.getValue());
    }
}