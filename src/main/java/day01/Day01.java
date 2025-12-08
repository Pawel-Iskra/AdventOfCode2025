package day01;

import utils.MyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day01 {

    private static final int START_POINT = 50;

    private static List<String> DIRECTIONS;
    private static List<Integer> VALUES;
    private static int LIST_SIZE;


    private static void prepareData(List<String> input) {
        DIRECTIONS = new ArrayList<>();
        VALUES = new ArrayList<>();
        for (String currentLine : input) {
            DIRECTIONS.add(String.valueOf(currentLine.charAt(0)));
            VALUES.add(Integer.parseInt(currentLine.substring(1)));
        }
        LIST_SIZE = DIRECTIONS.size();
    }

    public static void partTwo() {
        System.out.println("\nPART II:");
        int counter = 0;
        int start = START_POINT;
        for (int i = 0; i < LIST_SIZE; i++) {
            String dir = DIRECTIONS.get(i);
            int value = VALUES.get(i);
            switch (dir) {
                case "L" -> {
                    for (int j = 0; j < value; j++) {
                        start = start - 1;
                        if (start < 0) start = start + 100;
                        if (start == 0) counter++;
                        start = start % 100;
                    }
                }
                case "R" -> {
                    for (int j = 0; j < value; j++) {
                        start = start + 1;
                        if (start == 100) start = 0;
                        if (start == 0) counter++;
                        start = start % 100;
                    }
                }
            }
        }
        System.out.println("counter = " + counter);
    }

    public static void partOne() {
        System.out.println("PART I:");
        int counter = 0;
        int start = START_POINT;
        for (int i = 0; i < LIST_SIZE; i++) {
            String dir = DIRECTIONS.get(i);
            int value = VALUES.get(i);
            switch (dir) {
                case "L" -> {
                    start = start - value;
                    if (start < 0) start = start + 100;
                }
                case "R" -> {
                    start = start + value;
                    if (start >= 100) start = start - 100;
                }
            }
            start = start % 100;
            if (start == 0) counter++;
        }
        System.out.println("counter = " + counter);
    }


    public static void main(String[] args) throws IOException {
        String pathToInputFile = "src/main/resources/day01.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        prepareData(inputLines);
        partOne();
        partTwo();
    }
}
