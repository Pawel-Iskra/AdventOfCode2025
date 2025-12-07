package day07;

import utils.MyUtils;

import java.util.Arrays;
import java.util.List;

public class Day07 {

    private static final char SPLITTER = '^';
    private static final char BEAM = '|';
    private static final char START_S = 'S';

    private static char[][] DIAGRAM;
    private static long[][] VALUES;
    private static int ROWS;
    private static int COLS;
    private static int START_ROW;
    private static int START_COL;


    public static void partOne() {
        System.out.println("PART I:");
        int counter = 0;
        DIAGRAM[START_ROW + 1][START_COL] = BEAM; // step 1
        for (int i = START_ROW + 2; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (DIAGRAM[i - 1][j] == BEAM) {
                    if (DIAGRAM[i][j] == SPLITTER) {
                        DIAGRAM[i][j - 1] = BEAM;
                        DIAGRAM[i][j + 1] = BEAM;
                        counter++;
                    } else {
                        DIAGRAM[i][j] = BEAM;
                    }
                }
            }
        }
        System.out.println("counter = " + counter);
    }

    public static void partTwo() {
        System.out.println("\nPART II:");
        VALUES[START_ROW + 1][START_COL] = 1;
        for (int i = START_ROW + 2; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (DIAGRAM[i - 1][j] == BEAM) {
                    if (DIAGRAM[i][j] == SPLITTER) {
                        VALUES[i][j - 1] += VALUES[i - 1][j];
                        VALUES[i][j + 1] += VALUES[i - 1][j];
                    } else {
                        VALUES[i][j] += VALUES[i - 1][j];
                    }
                }
            }
        }
        long sum = Arrays.stream(VALUES[ROWS - 1]).sum();
        System.out.println("sum = " + sum);
    }

    private static void prepareData(List<String> input) {
        ROWS = input.size();
        COLS = input.get(0).length();
        DIAGRAM = new char[ROWS][COLS];
        VALUES = new long[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                DIAGRAM[i][j] = input.get(i).charAt(j);
                if (input.get(i).charAt(j) == START_S) {
                    START_ROW = i;
                    START_COL = j;
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        String pathToInputFile = "src/main/resources/day07.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        prepareData(inputLines);
        partOne();
        partTwo();
    }
}
