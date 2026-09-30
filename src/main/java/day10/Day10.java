package day10;

import utils.MyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    private static void prepareData(List<String> input) {
        for (String currentMachine : input) {
            String lightDiagram = currentMachine.substring(1, currentMachine.indexOf("]"));
            String joltageReq = currentMachine.substring(
                    currentMachine.indexOf("{") + 1, currentMachine.indexOf("}"));
            Machine machine = new Machine(lightDiagram, joltageReq);
            String[] buttonArray = currentMachine.substring(
                            currentMachine.indexOf("]") + 1, currentMachine.indexOf("{"))
                    .strip()
                    .split(" ");
            List<Integer> button = new ArrayList<>();
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

    static void partTwo() {
        System.out.println("\nPART II:");

    }

    static void partOne() {
        System.out.println("PART I:");
        int size = MACHINE_LIST.size();
        int fewestPressSum = 0;


        for (Machine machine : MACHINE_LIST) {
            String diagramToAchieve = machine.getLightDiagram();
            List<List<Integer>> buttons = machine.getButtonList();
            int presses = findFewestButtonPresses(diagramToAchieve, buttons);
            fewestPressSum += presses;
//            System.out.println("presses = " + presses);
        }
        System.out.println("fewestPressSum = " + fewestPressSum);

    }

    private static int findFewestButtonPresses(String diagramToAchieve, List<List<Integer>> buttons) {
        int fewest = Integer.MAX_VALUE;
        int numberOfButtons = buttons.size();

        for (int i = 0; i < 10000; i++) {
            char[] currentDiagram = new char[diagramToAchieve.length()];
            for (int j = 0; j < diagramToAchieve.length(); j++) {
                currentDiagram[j] = LIGHT_OFF;
            } // indicator lights are all initially off

            int presses = 0;
            while (!checkIfEqual(diagramToAchieve, currentDiagram)) {
                Random random = new Random();
                int index = random.nextInt(numberOfButtons);
                pressButton(currentDiagram, buttons.get(index));
                presses++;
            }
            if (presses < fewest) fewest = presses;
        }
        return fewest;
    }

    private static char[] pressButton(char[] diagram, List<Integer> button) {
        for (int i = 0; i < button.size(); i++) {
            int lightNumber = button.get(i);
            if (diagram[lightNumber] == LIGHT_OFF) {
                diagram[lightNumber] = LIGHT_ON;
            } else {
                diagram[lightNumber] = LIGHT_OFF;
            }
        }
        return diagram;
    }

    private static boolean checkIfEqual(String diagramToAchieve, char[] currentDiagram) {
        for (int i = 0; i < diagramToAchieve.length(); i++) {
            if (currentDiagram[i] != diagramToAchieve.charAt(i)) return false;
        }
        return true;
    }


    public static void main(String[] args) {
        String pathToInputFile = "src/main/resources/day10.txt";
        List<String> inputLines = MyUtils.getInputLines(pathToInputFile);

        prepareData(inputLines);
        partOne();
        partTwo();
    }
}
