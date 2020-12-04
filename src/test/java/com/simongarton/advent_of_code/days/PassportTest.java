package com.simongarton.advent_of_code.days;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PassportTest {

    @Test
    void hclIsValid() {
        Passport passport = new Passport(Collections.emptyList());
        passport.setHcl("123");
        assertFalse(passport.hclIsValid());
        passport.setHcl("#123456");
        assertTrue(passport.hclIsValid());
        passport.setHcl("#1234567");
        assertFalse(passport.hclIsValid());
        passport.setHcl("#1a3456");
        assertTrue(passport.hclIsValid());
        passport.setHcl("#1A3456");
        assertFalse(passport.hclIsValid());
    }

    @Test
    void pidIsValid() {
        Passport passport = new Passport(Collections.emptyList());
        passport.setPid("123");
        assertFalse(passport.pidIsValid());
        passport.setPid("121");
        assertFalse(passport.pidIsValid());
        passport.setPid("012345567");
        assertTrue(passport.pidIsValid());
    }

    @Test
    void badPassports() {
        List<String> lines = new ArrayList<>();
        lines.add("eyr:1972 cid:100");
        lines.add("hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926");
        Passport passport = new Passport(lines);
        assertFalse(passport.valid());

        lines.clear();
        lines.add("eyr:1972 cid:100");
        lines.add("hcl:#602927 eyr:1967 hgt:170cm");
        lines.add("ecl:grn pid:012533040 byr:1946");
        passport = new Passport(lines);
        assertFalse(passport.valid());

        lines.clear();
        lines.add("hcl:dab227 iyr:2012");
        lines.add("ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277");
        passport = new Passport(lines);
        assertFalse(passport.valid());

        lines.clear();
        lines.add("hgt:59cm ecl:zzz");
        lines.add("eyr:2038 hcl:74454a iyr:2023");
        lines.add("pid:3556412378 byr:2007");
        passport = new Passport(lines);
        assertFalse(passport.valid());
        lines.add("");
    }

    @Test
    void goodPassports() {
        List<String> lines = new ArrayList<>();
        lines.add("pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980");
        lines.add("hcl:#623a2f");
        Passport passport = new Passport(lines);
        assertTrue(passport.valid());

        lines.clear();
        lines.add("eyr:2029 ecl:blu cid:129 byr:1989");
        lines.add("iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm");
        passport = new Passport(lines);
        assertTrue(passport.valid());

        lines.clear();
        lines.add("hcl:#888785");
        lines.add("hgt:164cm byr:2001 iyr:2015 cid:88");
        lines.add("pid:545766238 ecl:hzl");
        lines.add("eyr:2022");
        passport = new Passport(lines);
        assertTrue(passport.valid());

        lines.clear();
        lines.add("iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719");
        passport = new Passport(lines);
        assertTrue(passport.valid());
    }
}