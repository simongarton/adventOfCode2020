package com.simongarton.advent_of_code.days;

import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.simongarton.advent_of_code.days.Day12.Direction.EAST;

@Data
public class Day12 {

    private static String FILENAME = "data/day12.txt";

    @Getter
    public enum Direction {

        NORTH(0),
        EAST(90),
        SOUTH(180),
        WEST(270);

        private final int bearing;

        Direction(int bearing) {
            this.bearing = bearing;
        }

        public static Direction getFromBearing(int bearing) {
            return Arrays.stream(values()).filter(b -> b.bearing == (bearing % 360)).findFirst().orElse(null);
        }
    }

    public void run() {
        System.out.println("Day 12: Rain Risk\n");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Ship ship = new Ship(EAST, 0, 0);
        System.out.println("Part 1 : " + ship.followDirections(lines));
        ship = new Ship(EAST, 0, 0);
        System.out.println("Part 2 : " + ship.followWaypointDirections(lines));
    }

    @Getter
    public static class Ship {

        private int east;
        private int north;
        private Direction direction;
        private int waypointEast;
        private int waypointNorth;

        public Ship(Direction direction, int east, int north) {
            this.direction = direction;
            this.east = east;
            this.north = north;
            this.waypointEast = east + 10;
            this.waypointNorth = north + 1;
        }

        public long followDirections(List<String> lines) {
            System.out.println("Starting at " + getPosition());
            for (String line : lines) {
                followDirection(line);
                System.out.println("  after " + line + " at " + getPosition());
            }
            return Math.abs((long) east) + Math.abs((long) north);
        }

        public long followWaypointDirections(List<String> lines) {
            System.out.println("Starting at " + getPosition());
            for (String line : lines) {
                followWaypointDirection(line);
                System.out.println("  after " + line + " at " + getPosition());
            }
            return Math.abs((long) east) + Math.abs((long) north);
        }

        private void followDirection(String line) {
            String instruction = line.substring(0, 1);
            int number = Integer.parseInt(line.substring(1));
            switch (instruction) {
                case "N":
                    north = north + number;
                    break;
                case "S":
                    north = north - number;
                    break;
                case "E":
                    east = east + number;
                    break;
                case "W":
                    east = east - number;
                    break;
                case "F":
                    switch (direction) {
                        case NORTH:
                            north = north + number;
                            break;
                        case SOUTH:
                            north = north - number;
                            break;
                        case EAST:
                            east = east + number;
                            break;
                        case WEST:
                            east = east - number;
                            break;
                    }
                    break;
                case "L":
                    direction = Direction.getFromBearing(360 + direction.getBearing() - number);
                    break;
                case "R":
                    direction = Direction.getFromBearing(direction.getBearing() + number);
                    break;
                default:
                    throw new RuntimeException("unrecognised instruction " + instruction);
            }
        }

        private void followWaypointDirection(String line) {
            String instruction = line.substring(0, 1);
            int number = Integer.parseInt(line.substring(1));
            switch (instruction) {
                case "N":
                    waypointNorth = waypointNorth + number;
                    break;
                case "S":
                    waypointNorth = waypointNorth - number;
                    break;
                case "E":
                    waypointEast = waypointEast + number;
                    break;
                case "W":
                    waypointEast = waypointEast - number;
                    break;
                case "F":
                    int deltaEast = waypointEast - east;
                    int deltaNorth = waypointNorth - north;
                    north = north + (waypointNorth - north) * number;
                    east = east + (waypointEast - east) * number;
                    waypointNorth = north + deltaNorth;
                    waypointEast = east + deltaEast;
                    break;
                case "L":
                    rotateWaypoint(360 - number);
                    break;
                case "R":
                    rotateWaypoint(number);
                    break;
                default:
                    throw new RuntimeException("unrecognised instruction " + instruction);
            }
        }

        private void rotateWaypoint(int angle) {
            int deltaEast = waypointEast - east;
            int deltaNorth = waypointNorth - north;
            switch (angle) {
                case 0:
                    break;
                case 90:
                    waypointEast = east + deltaNorth;
                    waypointNorth = north - deltaEast;
                    break;
                case 180:
                    waypointEast = east - deltaEast;
                    waypointNorth = north - deltaNorth;
                    break;
                case 270:
                    waypointEast = east - deltaNorth;
                    waypointNorth = north + deltaEast;
                    break;
                default:
                    throw new RuntimeException("Unhandled angle " + angle);
            }
        }

        private String getPosition() {
            return east + "," + north + " facing " + direction + " waypoint at " + waypointEast + "," + waypointNorth;
        }
    }
}
