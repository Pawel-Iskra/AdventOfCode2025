package day09;

import utils.MyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day09 {


    private static List<Integer> RED_TILE_COORD_ROW_LIST;
    private static List<Integer> RED_TILE_COORD_COL_LIST;
    private static int NUMBER_OF_RED_TILES;
    private static Map<Integer, Span> ROWS_SPAN_MAP;
    private static Map<Integer, Span> COLS_SPAN_MAP;


    record Span(int min, int max) {
    }


    private static boolean checkIfRectangleIsInside(int rowStart, int rowEnd, int colStart, int colEnd) {
        for (int i = rowStart; i <= rowEnd; i++) {
            if (ROWS_SPAN_MAP.containsKey(i)) {
                Span currentSpan = ROWS_SPAN_MAP.get(i);
                if (colEnd > currentSpan.max) return false;
                if (colStart < currentSpan.min) return false;
            }
        }
        for (int i = colStart; i <= colEnd; i++) {
            if (COLS_SPAN_MAP.containsKey(i)) {
                Span currentSpan = COLS_SPAN_MAP.get(i);
                if (rowEnd > currentSpan.max) return false;
                if (rowStart < currentSpan.min) return false;
            }
        }
        return true;
    }

    private static void addValuesToSpanMaps(int rowStart, int rowEnd, int colStart, int colEnd) {
        for (int k = rowStart; k <= rowEnd; k++) {
            Span rowSpan = new Span(colStart, colEnd);
            ROWS_SPAN_MAP.computeIfPresent(k, (key, oldSpan) -> {
                int newMin = Math.min(oldSpan.min(), colStart);
                int newMax = Math.max(oldSpan.max(), colEnd);
                return new Span(newMin, newMax);
            });
            ROWS_SPAN_MAP.putIfAbsent(k, rowSpan);
        }
        for (int k = colStart; k <= colEnd; k++) {
            Span colSpan = new Span(rowStart, rowEnd);
            COLS_SPAN_MAP.computeIfPresent(k, (key, oldSpan) -> {
                int newMin = Math.min(oldSpan.min(), rowStart);
                int newMax = Math.max(oldSpan.max(), rowEnd);
                return new Span(newMin, newMax);
            });
            COLS_SPAN_MAP.putIfAbsent(k, colSpan);
        }
    }

    private static void prepareData(List<String> input) {
        RED_TILE_COORD_COL_LIST = new ArrayList<>();
        RED_TILE_COORD_ROW_LIST = new ArrayList<>();
        ROWS_SPAN_MAP = new HashMap<>();
        COLS_SPAN_MAP = new HashMap<>();
        for (String currentLine : input) {
            String[] coords = currentLine.split(",");
            int row = Integer.parseInt(coords[1]);
            int col = Integer.parseInt(coords[0]);
            RED_TILE_COORD_COL_LIST.add(col);
            RED_TILE_COORD_ROW_LIST.add(row);
        }
        NUMBER_OF_RED_TILES = RED_TILE_COORD_COL_LIST.size();

        for (int i = 0; i < NUMBER_OF_RED_TILES - 1; i++) {
            int firstRow = RED_TILE_COORD_ROW_LIST.get(i);
            int firstCol = RED_TILE_COORD_COL_LIST.get(i);
            int secondRow = RED_TILE_COORD_ROW_LIST.get(i + 1);
            int secondCol = RED_TILE_COORD_COL_LIST.get(i + 1);
            addValuesToSpanMaps(Math.min(firstRow, secondRow), Math.max(firstRow, secondRow),
                    Math.min(firstCol, secondCol), Math.max(firstCol, secondCol));
            if (i == 0) {
                secondRow = RED_TILE_COORD_ROW_LIST.get(NUMBER_OF_RED_TILES - 1);
                secondCol = RED_TILE_COORD_COL_LIST.get(NUMBER_OF_RED_TILES - 1);
                addValuesToSpanMaps(Math.min(firstRow, secondRow), Math.max(firstRow, secondRow),
                        Math.min(firstCol, secondCol), Math.max(firstCol, secondCol));
            }
        }
    }

    public static void partTwo() {
        System.out.println("\nPART II:");
        long maxArea = 0;
        for (int i = 0; i < NUMBER_OF_RED_TILES; i++) {
            int firstRow = RED_TILE_COORD_ROW_LIST.get(i);
            int firstCol = RED_TILE_COORD_COL_LIST.get(i);
            for (int j = i + 1; j < NUMBER_OF_RED_TILES - 1; j++) {
                int secondRow = RED_TILE_COORD_ROW_LIST.get(j);
                int secondCol = RED_TILE_COORD_COL_LIST.get(j);
                if (checkIfRectangleIsInside(
                        Math.min(firstRow, secondRow), Math.max(firstRow, secondRow),
                        Math.min(firstCol, secondCol), Math.max(firstCol, secondCol))) {
                    long currentArea = (long) (Math.abs(firstRow - secondRow) + 1) * (Math.abs(firstCol - secondCol) + 1);
                    if (currentArea > maxArea) maxArea = currentArea;
                }
            }
        }
        System.out.println("maxArea = " + maxArea);
    }

    public static void partOne() {
        System.out.println("PART I:");
        long maxArea = 0;
        for (int i = 0; i < NUMBER_OF_RED_TILES - 1; i++) {
            int firstRow = RED_TILE_COORD_ROW_LIST.get(i);
            int firstCol = RED_TILE_COORD_COL_LIST.get(i);
            for (int j = i + 1; j < NUMBER_OF_RED_TILES - 1; j++) {
                int secondRow = RED_TILE_COORD_ROW_LIST.get(j);
                int secondCol = RED_TILE_COORD_COL_LIST.get(j);
                long currentArea = (long) (Math.abs(firstRow - secondRow) + 1) * (Math.abs(firstCol - secondCol) + 1);
                if (currentArea > maxArea) maxArea = currentArea;
            }
        }
        System.out.println("maxArea = " + maxArea);
    }


    public static void main(String[] args) {
        String pathToInputFile = "src/main/resources/day09.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        prepareData(inputLines);
        partOne();
        partTwo();
    }
}
