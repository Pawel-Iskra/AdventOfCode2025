package day10;

import utils.MyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 {


    private static final char LIGHT_ON = '#';
    private static final char LIGHT_OFF = '.';
    private static final List<Machine> MACHINE_LIST = new ArrayList<>();


    static class Machine {
        private final String lightDiagram;
        private final List<List<Integer>> buttonList;
        private final String joltageReq;

        public Machine(String lightDiagram, String joltageReq) {
            this.lightDiagram = lightDiagram;
            this.buttonList = new ArrayList<>();
            this.joltageReq = joltageReq;
        }

        public String getLightDiagram() {
            return lightDiagram;
        }

        public List<List<Integer>> getButtonList() {
            return buttonList;
        }

        public String getJoltageReq() {
            return joltageReq;
        }

        public void addButton(List<Integer> button) {
            buttonList.add(button);
        }

        @Override
        public String toString() {
            return "\nMachine{" +
                    "lightDiagram='" + lightDiagram + '\'' +
                    ", buttonList=" + buttonList +
                    ", joltageReq='" + joltageReq + '\'' +
                    '}';
        }
    }

    static int[][] getAllCombinations(int size) {
        int combinations = (int) Math.pow(2, size);
        int[][] result = new int[combinations][size];

        for (int i = 0; i < combinations; i++) {
            int value = i;
            for (int j = size - 1; j >= 0; j--) {
                result[i][j] = value % 2;
                value /= 2;
            }
        }
        // hehe - each row is now binary representation of numbers from 0 to 2^size
        // which means the result is all possible 0/1 combinations in the given size
        return result;
    }


    private static int findFewestButtonPresses(String targetDiagram, List<List<Integer>> buttons) {
        int fewest = Integer.MAX_VALUE;
        int numberOfButtons = buttons.size();
        int[][] buttonCombinations = getAllCombinations(numberOfButtons);

        for (int[] currentCombination : buttonCombinations) {
            char[] currentDiagram = new char[targetDiagram.length()];
            Arrays.fill(currentDiagram, LIGHT_OFF); // indicator lights are all initially off
            int presses = 0;

            for (int j = 0; j < numberOfButtons; j++) {
                if (currentCombination[j] == 1) {
                    pressButton(currentDiagram, buttons.get(j));
                    presses++;
                }
            }
            if (checkIfEqual(targetDiagram, currentDiagram)) {
                fewest = Math.min(fewest, presses);
            }
        }
        return fewest;
    }

    private static void pressButton(char[] diagram, List<Integer> button) {
        for (int lightNumber : button) {
            if (diagram[lightNumber] == LIGHT_OFF) {
                diagram[lightNumber] = LIGHT_ON;
            } else {
                diagram[lightNumber] = LIGHT_OFF;
            }
        }
    }

    private static boolean checkIfEqual(String diagramToAchieve, char[] currentDiagram) {
        for (int i = 0; i < diagramToAchieve.length(); i++) {
            if (currentDiagram[i] != diagramToAchieve.charAt(i)) return false;
        }
        return true;
    }


    private static void prepareData(List<String> input) {
        for (String currentMachine : input) {
            String lightDiagram = currentMachine.substring(1, currentMachine.indexOf("]"));
            String joltageReq = currentMachine.substring(
                    currentMachine.indexOf("{") + 1, currentMachine.indexOf("}"));
            Machine machine = new Machine(lightDiagram, joltageReq);
            String[] buttonArray = currentMachine
                    .substring(currentMachine.indexOf("]") + 1, currentMachine.indexOf("{"))
                    .strip()
                    .split(" ");
            List<Integer> button;
            for (String currentButton : buttonArray) {
                String[] buttonAsArray = currentButton.split(",");
                button = new ArrayList<>();
                for (String buttonInt : buttonAsArray) {
                    button.add(Integer.parseInt(buttonInt.strip().replaceAll("[^0-9]", "")));
                }
                machine.addButton(button);
            }
            MACHINE_LIST.add(machine);
        }
//        System.out.println("MACHINE_LIST = " + MACHINE_LIST);
    }

    static void partOne() {
        System.out.println("PART I:");
        int fewestPressSum = 0;

        for (Machine machine : MACHINE_LIST) {
            String diagramToAchieve = machine.getLightDiagram();
            List<List<Integer>> buttons = machine.getButtonList();
            int presses = findFewestButtonPresses(diagramToAchieve, buttons);
            fewestPressSum += presses;
        }
        System.out.println("fewestPressSum = " + fewestPressSum);
    }

    static void partTwo() {
        System.out.println("\nPART II:");


    }

    static void main() {
        String pathToInputFile = "src/main/resources/day10.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        prepareData(inputLines);
        partOne();
        partTwo();
    }
}
