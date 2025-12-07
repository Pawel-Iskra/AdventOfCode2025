package day04;

import utils.MyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day04 {

    private static final char ROLL_OF_PAPER = '@';

    private static char[][] DIAGRAM;
    private static int ROWS;
    private static int COLS;


    private static boolean checkIfLessNeighboursThanFour(int row, int col) {
        int howManyAround = 0;
        for (int i = col - 1; i <= col + 1; i++) {
            if (row - 1 < 0) break;
            if (i < 0 || i >= COLS) continue;
            if (DIAGRAM[row - 1][i] == ROLL_OF_PAPER) howManyAround++;
        }
        for (int i = col - 1; i <= col + 1; i++) {
            if (row + 1 >= ROWS) break;
            if (i < 0 || i >= COLS) continue;
            if (DIAGRAM[row + 1][i] == ROLL_OF_PAPER) howManyAround++;
        }
        if ((col - 1 >= 0) && DIAGRAM[(row)][(col - 1)] == ROLL_OF_PAPER) howManyAround++;
        if ((col + 1 < COLS) && DIAGRAM[(row)][(col + 1)] == ROLL_OF_PAPER) howManyAround++;
        return howManyAround < 4;
    }

    private static List<String> makeRound() {
        List<String> coordinates = new ArrayList<>();
        int rows = DIAGRAM.length;
        int cols = DIAGRAM[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                StringBuilder strb = new StringBuilder();
                if (DIAGRAM[i][j] != ROLL_OF_PAPER) continue;
                if (checkIfLessNeighboursThanFour(i, j)) {
                    coordinates.add(strb.append(i).append(",").append(j).toString());
                }
            }
        }
        return coordinates;
    }

    private static void prepareData(List<String> input) {
        ROWS = input.size();
        COLS = input.get(0).length();
        DIAGRAM = new char[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                DIAGRAM[i][j] = input.get(i).charAt(j);
            }
        }
    }


    public static void partTwo() {
        System.out.println("\nPART II:");
        List<String> coordinates;
        int result = 0;
        while ((coordinates = makeRound()).size() > 0) {
            result += coordinates.size();
            for (String currentCoordinates : coordinates) {
                String[] coords = currentCoordinates.split(",");
                int row = Integer.parseInt(coords[0]);
                int col = Integer.parseInt(coords[1]);
                DIAGRAM[row][col] = '.';
            }
        }
        System.out.println("result = " + result);
    }

    public static void partOne() {
        System.out.println("PART I:");
        int result = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (DIAGRAM[i][j] != ROLL_OF_PAPER) continue;
                if (checkIfLessNeighboursThanFour(i, j)) {
                    result++;
                }
            }
        }
        System.out.println("result = " + result);
    }


    public static void main(String[] args) throws IOException {
        String pathToInputFile = "src/main/resources/day04.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        prepareData(inputLines);
        partOne();
        partTwo();
    }
}
