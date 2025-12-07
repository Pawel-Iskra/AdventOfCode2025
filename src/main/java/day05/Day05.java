package day05;

import utils.MyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day05 {

    private static List<IdRange> ID_RANGES = new ArrayList<>();
    private static List<Long> INGREDIENTS_IDS = new ArrayList<>();


    static class IdRange implements Comparable<IdRange> {
        private long start;
        private long end;

        IdRange(final long start, final long end) {
            this.start = start;
            this.end = end;
        }

        long getStart() {
            return start;
        }

        long getEnd() {
            return end;
        }

        @Override
        public int compareTo(IdRange other) {
            int compareResult = Long.compare(this.start, other.getStart());
            if (compareResult != 0) {
                return compareResult;
            }
            return Long.compare(this.end, other.getEnd());
        }

        @Override
        public String toString() {
            return "\nIdRange{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }


    public static void partOne() {
        System.out.println("PART I :");
        int counter = 0;
        for (Long currentId : INGREDIENTS_IDS) {
            for (IdRange currentRange : ID_RANGES) {
                long currentStart = currentRange.getStart();
                long currentEnd = currentRange.getEnd();
                if (currentId < currentStart) break;
                if (currentId <= currentEnd) {
                    counter++;
                    break;
                }
            }
        }
        System.out.println("counter = " + counter);
    }

    public static void partTwo() {
        System.out.println("\nPART II:");

        long previousStart = ID_RANGES.get(0).getStart();
        long previousEnd = ID_RANGES.get(0).getEnd();
        long counter = 0;
        counter += previousEnd - previousStart + 1;
        for (int i = 1; i < ID_RANGES.size(); i++) {
            long currentStart = ID_RANGES.get(i).getStart();
            long currentEnd = ID_RANGES.get(i).getEnd();

            if (currentStart <= previousEnd && currentEnd > previousEnd) {
                counter += currentEnd - previousEnd;
            } else if (currentStart > previousEnd) {
                counter += currentEnd - currentStart + 1;
            }
            previousEnd = currentEnd;
            previousStart = currentStart;
        }
        System.out.println("counter = " + counter);
    }

    private static void prepareData(List<String> input) {
        for (String currentLine : input) {
            if (currentLine.isBlank()) continue;
            if (currentLine.contains("-")) {
                String[] idsRange = currentLine.split("-");
                long start = Long.parseLong(idsRange[0]);
                long end = Long.parseLong(idsRange[1]);
                ID_RANGES.add(new IdRange(start, end));
            } else {
                INGREDIENTS_IDS.add(Long.parseLong(currentLine.strip()));
            }
        }
        Collections.sort(ID_RANGES);
        System.out.println("ID_RANGES = " + ID_RANGES);
    }


    public static void main(String[] args) {
        String pathToInputFile = "src/main/resources/day05.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        prepareData(inputLines);
        partOne();
        partTwo();
    }
}
