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
        private final List<Integer> joltageConfig;

        public Machine(String lightDiagram, List<Integer> joltageConfig) {
            this.lightDiagram = lightDiagram;
            this.buttonList = new ArrayList<>();
            this.joltageConfig = new ArrayList<>(joltageConfig);
        }

        public String getLightDiagram() {
            return lightDiagram;
        }

        public List<List<Integer>> getButtonList() {
            return buttonList;
        }

        public List<Integer> getJoltageConfig() {
            return joltageConfig;
        }

        public void addButton(List<Integer> button) {
            buttonList.add(button);
        }

        @Override
        public String toString() {
            return "\nMachine{" +
                    "lightDiagram='" + lightDiagram + '\'' +
                    ", buttonList=" + buttonList +
                    ", joltageReq='" + joltageConfig + '\'' +
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


    private static int findFewestButtonPressesForLightDiagram(String targetDiagram, List<List<Integer>> buttons) {
        int fewest = Integer.MAX_VALUE;
        int numberOfButtons = buttons.size();
        int[][] buttonCombinations = getAllCombinations(numberOfButtons);

        for (int[] currentCombination : buttonCombinations) {
            char[] currentDiagram = new char[targetDiagram.length()];
            Arrays.fill(currentDiagram, LIGHT_OFF); // indicator lights are all initially off
            int presses = 0;

            for (int j = 0; j < numberOfButtons; j++) {
                if (currentCombination[j] == 1) {
                    pressLightButton(currentDiagram, buttons.get(j));
                    presses++;
                }
            }
            if (checkIfEqualLightDiagram(targetDiagram, currentDiagram)) {
                fewest = Math.min(fewest, presses);
            }
        }
        return fewest;
    }

    private static void pressLightButton(char[] diagram, List<Integer> button) {
        for (int lightNumber : button) {
            if (diagram[lightNumber] == LIGHT_OFF) {
                diagram[lightNumber] = LIGHT_ON;
            } else {
                diagram[lightNumber] = LIGHT_OFF;
            }
        }
    }

    private static boolean checkIfEqualLightDiagram(String diagramToAchieve, char[] currentDiagram) {
        for (int i = 0; i < diagramToAchieve.length(); i++) {
            if (currentDiagram[i] != diagramToAchieve.charAt(i)) return false;
        }
        return true;
    }


    private static void prepareData(List<String> input) {
        for (String currentMachineData : input) {
            String lightDiagram = currentMachineData.substring(1, currentMachineData.indexOf("]"));
            String[] joltageConfigAsArray = currentMachineData
                    .substring(currentMachineData.indexOf("{") + 1, currentMachineData.indexOf("}"))
                    .split(",");
            List<Integer> joltageConfig = Arrays.stream(joltageConfigAsArray)
                    .map(Integer::parseInt)
                    .toList();
            Machine machine = new Machine(lightDiagram, joltageConfig);

            String[] buttonArray = currentMachineData
                    .substring(currentMachineData.indexOf("]") + 1, currentMachineData.indexOf("{"))
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
        System.out.println("MACHINE_LIST = " + MACHINE_LIST);
    }

    private static boolean checkIfEqualJoltage(int[] currentJoltageConfig, List<Integer> targetJoltageConfig) {
        for (int i = 0; i < targetJoltageConfig.size(); i++) {
            if (currentJoltageConfig[i] != targetJoltageConfig.get(i)) return false;
        }
        return true;
    }

    private static void pressJoltageButton(int[] currentJoltageConfig, List<Integer> button) {
        for (int currentButtonPart : button) {
            currentJoltageConfig[currentButtonPart]++;
        }
    }

    private static boolean checkIfAnyJoltageIsOverLimit(int[] currentJoltageConfig, List<Integer> targetJoltageConfig) {
        for (int i = 0; i < targetJoltageConfig.size(); i++) {
            if (currentJoltageConfig[i] > targetJoltageConfig.get(i)) return true;
        }
        return false;
    }

    private static boolean checkIfAnyButtonIsOverLimit(int[] currentButtonsPress, int[] maxButtonsPress) {
        for (int i = 0; i < currentButtonsPress.length; i++) {
            if (currentButtonsPress[i] > maxButtonsPress[i]) return true;
        }
        return false;
    }

    private static int findFewestButtonPressesForJoltageConfig(List<Integer> targetJoltageConfig, List<List<Integer>> buttons) {
        int fewest = 10_000_000;
        int sizeJoltage = targetJoltageConfig.size();
        int minButtonsPress = targetJoltageConfig.stream()
                .max(Integer::compareTo)
                .orElse(0);
        int[] maxButtonsPress = new int[buttons.size()];
        for (int i = 0; i < buttons.size(); i++) {
            List<Integer> currentButton = buttons.get(i);
            maxButtonsPress[i] = currentButton.stream()
                    .map(targetJoltageConfig::get)
                    .min(Integer::compareTo)
                    .orElse(Integer.MAX_VALUE);
        }
        System.out.println("\ntargetJoltageConfig = " + targetJoltageConfig);
        System.out.println("minButtonsPress = " + minButtonsPress);
        System.out.println("buttons = " + buttons);
        System.out.println("maxButtonsPress = " + Arrays.toString(maxButtonsPress));

        int[] currentButtonPressCombination = new int[maxButtonsPress.length];
        while (true) {
//            System.out.println("currentButtonPressCombination = " + Arrays.toString(currentButtonPressCombination));
            int pressesInCurrentCombination = Arrays.stream(currentButtonPressCombination).sum();
            int presses = 0;
            int[] currentJoltageConfig = new int[sizeJoltage];
            boolean exceeded = false;
            if (fewest > pressesInCurrentCombination && pressesInCurrentCombination >= minButtonsPress) {
                for (int j = 0; j < currentButtonPressCombination.length; j++) {
                    for (int k = 0; k < currentButtonPressCombination[j]; k++) {
                        pressJoltageButton(currentJoltageConfig, buttons.get(j));
                    }
                    if (checkIfAnyJoltageIsOverLimit(currentJoltageConfig, targetJoltageConfig)) {
                        exceeded = true;
                        break;
                    }
                }
                if (!exceeded && checkIfEqualJoltage(currentJoltageConfig, targetJoltageConfig)) {
                    presses = pressesInCurrentCombination;
                    System.out.println("presses = " + presses);
                    fewest = Math.min(presses, fewest);
                }
            }

            // counters fpr each button press
            int index = currentButtonPressCombination.length - 1;
            while (index >= 0) {
                if (currentButtonPressCombination[index] < maxButtonsPress[index]) {
                    currentButtonPressCombination[index]++;
                    break;
                }
                currentButtonPressCombination[index] = 0;
                index--;
            }
            if (index < 0) {
                break;
            }
        }
        return fewest;
    }

    /*
        FEWEST:
        0-> 49
        1-> 60
        2-> 40
        3 ->
    */


    private static List<int[]> generateAllCombinations(int[] maxValues) {
        List<int[]> result = new ArrayList<>();
        result.add(new int[maxValues.length]);

        for (int i = 0; i < maxValues.length; i++) {
            List<int[]> newResult = new ArrayList<>();

            for (int[] combination : result) {
                for (int value = 0; value <= maxValues[i]; value++) {
                    int[] newCombination = combination.clone();
                    newCombination[i] = value;
                    newResult.add(newCombination);
                }
            }
            result = newResult;
        }
        return result;
    }

    static void partOne() {
        System.out.println("PART I:");
        int fewestPressSum = 0;

        for (Machine machine : MACHINE_LIST) {
            String diagramToAchieve = machine.getLightDiagram();
            List<List<Integer>> buttons = machine.getButtonList();
            int presses = findFewestButtonPressesForLightDiagram(diagramToAchieve, buttons);
            fewestPressSum += presses;
        }
        System.out.println("fewestPressSum = " + fewestPressSum);
    }


    static void partTwo() {
        System.out.println("\nPART II:");
        int fewestPressSum = 0;
        int index = 0;
        for (Machine machine : MACHINE_LIST) {
            List<Integer> joltageConfig = machine.getJoltageConfig();
            List<List<Integer>> buttons = machine.getButtonList();
            int presses = findFewestButtonPressesForJoltageConfig(joltageConfig, buttons);
            System.out.println(index + " -> presses = " + presses);
            fewestPressSum += presses;
            index++;
        }
        System.out.println("fewestPressSum = " + fewestPressSum);
    }

    static void main() {
        String pathToInputFile = "src/main/resources/day10.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        prepareData(inputLines);
        partOne();
        partTwo();
    }
}
