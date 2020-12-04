package com.simongarton.advent_of_code.days;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Passport {

    // could be done more efficently as a map but then supporting code gets more complex
    private String byr; // (Birth Year)
    private String iyr; // (Issue Year)
    private String eyr; // (Expiration Year)
    private String hgt; // (Height)
    private String hcl; // (Hair Color)
    private String ecl; // (Eye Color)
    private String pid; // (Passport ID)
    private String cid; // (Country ID)

    public Passport(List<String> lines) {
        for (String line : lines) {
            processLine(line);
        }
    }

    private void processLine(String line) {
        String[] parts = line.split(" ");
        for (String part : parts) {
            processPart(part);
        }
    }

    public boolean valid() {
        if (byr == null || !byrIsValid()) return false;
        if (iyr == null || !iyrIsValid()) return false;
        if (eyr == null || !eyrIsValid()) return false;
        if (hgt == null || !hgtIsValid()) return false;
        if (hcl == null || !hclIsValid()) return false;
        if (ecl == null || !eclIsValid()) return false;
        if (pid == null || !pidIsValid()) return false;
        return true;
    }

    private boolean byrIsValid() {
        int year = Integer.parseInt(byr);
        return (year >= 1920 && year <= 2002);
    }

    private boolean iyrIsValid() {
        int year = Integer.parseInt(iyr);
        return (year >= 2010 && year <= 2020);
    }

    private boolean eyrIsValid() {
        int year = Integer.parseInt(eyr);
        return (year >= 2020 && year <= 2030);
    }

    private boolean hgtIsValid() {
        if (hgt.contains("cm")) {
            return hgtIsValidCM();
        }
        if (hgt.contains("in")) {
            return hgtIsValidIN();
        }
        return false;
    }

    private boolean hgtIsValidIN() {
        String measurement = hgt.replace("in", "");
        if (!hgt.equalsIgnoreCase(measurement + "in")) {
            return false;
        }
        int measure = Integer.parseInt(measurement);
        return (measure >= 59 && measure <= 76);
    }

    private boolean hgtIsValidCM() {
        String measurement = hgt.replace("cm", "");
        if (!hgt.equalsIgnoreCase(measurement + "cm")) {
            return false;
        }
        int measure = Integer.parseInt(measurement);
        return (measure >= 150 && measure <= 193);
    }

    public boolean hclIsValid() {
        return hcl.matches("#[0-9a-f]{6}");
    }

    public boolean eclIsValid() {
        String[] values = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};
        return Arrays.asList(values).contains(ecl);
    }

    public boolean pidIsValid() {
        return pid.matches("[0-9]{9}");
    }

    private void processPart(String part) {
        String[] details = part.split(":");
        String key = details[0];
        String value = details[1];
        switch (key) {
            case "byr":
                byr = value;
                break;
            case "iyr":
                iyr = value;
                break;
            case "eyr":
                eyr = value;
                break;
            case "hgt":
                hgt = value;
                break;
            case "hcl":
                hcl = value;
                break;
            case "ecl":
                ecl = value;
                break;
            case "pid":
                pid = value;
                break;
            case "cid":
                cid = value;
                break;
        }
    }
}
