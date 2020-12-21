package com.simongarton.advent_of_code.days;

import lombok.Getter;

public class Integer36 {
    @Getter
    private final long value;

    public Integer36(long value) {
        this.value = value;
    }

    public Integer36(String bits) {
        this.value = parseBits(bits);
    }

    private long parseBits(String bits) {
        long value = 0;
        for (int i = 0; i < 36; i++) {
            if (bits.charAt(i) == '1') {
                value = value + (1L << (35 - i));
            }
        }
        return value;
    }

    public String getBits() {
        StringBuilder bits = new StringBuilder();
        long runningValue = value;
        for (int power = 35; power >= 0; power--) {
            if (runningValue >= (1L << power)) {
                bits.append("1");
                runningValue -= (1L << power);
            } else {
                bits.append("0");
            }
        }
        return bits.toString();
    }
}
