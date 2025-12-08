package day02;

import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class Day02 {


    private static List<Long> RANGES_START;
    private static List<Long> RANGES_END;
    private static int LIST_SIZE;


    private static boolean checkIdForPartOne(String currentId) {
        String left = currentId.substring(0, currentId.length() / 2);
        String right = currentId.substring(currentId.length() / 2);
        return left.equals(right);
    }

    private static boolean checkIdForPartTwo(String currentId) {
        int size = currentId.length();
        for (int i = 1; i < size; i++) {
            String currentIdCopy = currentId;
            String currentPattern = currentIdCopy.substring(0, i);
            currentIdCopy = currentIdCopy.replaceAll(currentPattern, "");
            if (currentIdCopy.length() == 0) return true;
        }
        return false;
    }

    private static void prepareData(List<String> input) {
        RANGES_START = new ArrayList<>();
        RANGES_END = new ArrayList<>();
        String[] idRanges = input.get(0).split(",");
        for (String currentRanges : idRanges) {
            int indexOfSeparator = currentRanges.indexOf("-");
            RANGES_START.add(Long.parseLong(currentRanges.substring(0, indexOfSeparator)));
            RANGES_END.add(Long.parseLong(currentRanges.substring(indexOfSeparator + 1)));
        }
        LIST_SIZE = RANGES_START.size();
    }


    public static void partTwo() {
        System.out.println("\nPART II:");
        long sum = 0;
        for (int i = 0; i < LIST_SIZE; i++) {
            long start = RANGES_START.get(i);
            long end = RANGES_END.get(i);
            for (long j = start; j <= end; j++) {
                String currentId = String.valueOf(j);
                if (checkIdForPartTwo(currentId)) {
                    sum += j;
                }
            }
        }
        System.out.println("sum = " + sum);
    }


    public static void partOne() {
        System.out.println("PART I:");
        long sum = 0;
        for (int i = 0; i < LIST_SIZE; i++) {
            long start = RANGES_START.get(i);
            long end = RANGES_END.get(i);
            for (long j = start; j <= end; j++) {
                String currentId = String.valueOf(j);
                if (currentId.length() % 2 == 0) {
                    if (checkIdForPartOne(currentId)) {
                        sum += j;
                    }
                }
            }
        }
        System.out.println("sum = " + sum);
    }


    public static void main(String[] args) {
        String pathToInputFile = "src/main/resources/day02.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        prepareData(inputLines);
        partOne();
        partTwo();
    }
}
