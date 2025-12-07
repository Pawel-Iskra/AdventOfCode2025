package day03;

import utils.MyUtils;

import java.util.List;

public class Day03 {


    private static int findHighestDigit(String input) {
        return input.chars()
                .map(c -> c - '0')
                .boxed()
                .sorted()
                .toList()
                .get(input.length() - 1);
    }

    public static void partOne(List<String> input) {
        System.out.println("PART I:");
        int sumOfVoltage = 0;
        for (String currentLine : input) {
            int length = currentLine.length();
            int maxVoltage = 0;
            for (int j = 0; j < length - 1; j++) {
                for (int k = j + 1; k < length; k++) {
                    StringBuilder strb = new StringBuilder();
                    int currentVoltage = Integer.parseInt(strb
                            .append(currentLine.charAt(j))
                            .append(currentLine.charAt(k))
                            .toString());
                    if (currentVoltage > maxVoltage) maxVoltage = currentVoltage;
                }
            }
            sumOfVoltage += maxVoltage;
        }
        System.out.println("sumOfVoltage = " + sumOfVoltage);
    }

    public static void partTwo(List<String> input) {
        System.out.println("\nPART II:");
        long sumOfVoltage = 0L;
        for (String currentLine : input) {
            StringBuilder strb = new StringBuilder();
            String left = currentLine.substring(0, currentLine.length() - 11);
            int firstDigit = findHighestDigit(left);
            strb.append(firstDigit);
            String restOfCurrentLine = currentLine.substring(currentLine.indexOf(firstDigit + 48) + 1);
            for (int j = 10; j >= 0; j--) {
                if (restOfCurrentLine.length() <= j) {
                    strb.append(restOfCurrentLine);
                    break;
                }
                int nextDigit = findHighestDigit(restOfCurrentLine.substring(0, restOfCurrentLine.length() - j));
                strb.append(nextDigit);
                restOfCurrentLine = restOfCurrentLine.substring(restOfCurrentLine.indexOf(nextDigit + 48) + 1);
            }
            sumOfVoltage += Long.parseLong(strb.toString());
        }
        System.out.println("sumOfVoltage = " + sumOfVoltage);
    }


    public static void main(String[] args) {
        String pathToInputFile = "src/main/resources/day03.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        partOne(inputLines);
        partTwo(inputLines);
    }
}
