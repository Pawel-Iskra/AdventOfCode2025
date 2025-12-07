package day06;

import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class Day06 {

    private static final String ADD = "+";
    private static final String MULTIPLY = "*";
    private static final String ANY_BLANK_PATTERN = "\\s+";

    private static String[] OPERATORS;
    private static char[][] WORKSHEET;
    private static int[][] NUMBERS;
    private static int NUMBERS_IN_ONE_ROW;
    private static int ROWS;
    private static int COLS;


    static class NumbersFromColumn {
        private List<Long> numbers;
        private int columnNumber;

        NumbersFromColumn(final List<Long> numbers, final int columnNumber) {
            this.numbers = numbers;
            this.columnNumber = columnNumber;
        }

        List<Long> getNumbers() {
            return numbers;
        }

        int getColumnNumber() {
            return columnNumber;
        }
    }

    private static NumbersFromColumn getCurrentNumbersReadingInColumnsForOperatorAndCurrentColumnValue(int columnStart) {
        List<Long> numbers = new ArrayList<>();
        while (true) {
            StringBuilder strb = new StringBuilder();
            for (int j = 0; j < ROWS - 1; j++) {
                strb.append(WORKSHEET[j][columnStart]);
            }
            columnStart++;
            if (strb.toString().isBlank()) break;
            numbers.add(Long.valueOf(strb.toString().replaceAll(" ", "")));
            if (columnStart >= COLS) break;
        }
        return new NumbersFromColumn(numbers, columnStart);
    }

    private static void prepareData(List<String> input) {
        ROWS = input.size();
        NUMBERS_IN_ONE_ROW = input.get(0).strip().split(ANY_BLANK_PATTERN).length;
        NUMBERS = new int[ROWS][NUMBERS_IN_ONE_ROW];
        OPERATORS = input.get(ROWS - 1).strip().split(ANY_BLANK_PATTERN);
        for (int i = 0; i < ROWS - 1; i++) {
            String[] currentLine = input.get(i).strip().split(ANY_BLANK_PATTERN);
            for (int j = 0; j < currentLine.length; j++) {
                NUMBERS[i][j] = Integer.parseInt(currentLine[j].strip());
            }
        }
        COLS = input.get(0).length();
        WORKSHEET = new char[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                WORKSHEET[i][j] = input.get(i).charAt(j);
            }
        }
    }

    public static void partTwo() {
        System.out.println("\nPART II:");
        long finalResult = 0;
        int currentColumn = 0;
        for (String currentOperator : OPERATORS) {
            long result = 0;
            NumbersFromColumn numbersFromColumn = getCurrentNumbersReadingInColumnsForOperatorAndCurrentColumnValue(currentColumn);
            List<Long> currentNumbers = numbersFromColumn.getNumbers();
            currentColumn = numbersFromColumn.getColumnNumber();
            switch (currentOperator) {
                case MULTIPLY -> result = currentNumbers.stream().reduce(1L, (a, b) -> a * b);
                case ADD -> result = currentNumbers.stream().mapToLong(Long::longValue).sum();
            }
            finalResult += result;
        }
        System.out.println("finalResult = " + finalResult);
    }

    public static void partOne() {
        System.out.println("PART I:");
        long finalResult = 0;
        for (int i = 0; i < OPERATORS.length; i++) {
            long result = 1;
            switch (OPERATORS[i]) {
                case MULTIPLY -> {
                    for (int j = 0; j < ROWS - 1; j++) {
                        result *= NUMBERS[j][i];
                    }
                }
                case ADD -> {
                    result = 0;
                    for (int j = 0; j < ROWS - 1; j++) {
                        result += NUMBERS[j][i];
                    }
                }
            }
            finalResult += result;
        }
        System.out.println("finalResult = " + finalResult);
    }


    public static void main(String[] args) {
        String pathToInputFile = "src/main/resources/day06.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        prepareData(inputLines);
        partOne();
        partTwo();
    }
}
