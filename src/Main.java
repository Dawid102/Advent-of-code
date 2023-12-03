import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        day1();
        day2();
    }

    private static void day2() {
        Scanner day2 = getScanner("input/day_2.txt");
        int sumID = 0;
        int sumPower = 0;
        while (day2.hasNext()) {
            var data = day2.nextLine();
            Game game = new Game(data);
//            System.out.println(data);
//            System.out.println(game);
            if (game.values[0] <= 12 && game.values[1] <= 13 && game.values[2] <= 14) {
                sumID += game.id;
            }
            // Part 2
            sumPower += game.values[0] * game.values[1] * game.values[2];
        }
        System.out.println(sumID);
        System.out.println(sumPower);
    }

    private static void day1() {
        Scanner day1 = getScanner("input/day_1.txt");
        int score = 0;
        while (day1.hasNext()) {
            var data = day1.nextLine();
            int first = 0;
            boolean firstSet = false;
            int last = 0;
            // Part 2
                data = data.replaceAll("one", "one1one");
                data = data.replaceAll("two", "two2two");
                data = data.replaceAll("three", "three3three");
                data = data.replaceAll("four", "four4four");
                data = data.replaceAll("five", "five5five");
                data = data.replaceAll("six", "six6six");
                data = data.replaceAll("seven", "seven7seven");
                data = data.replaceAll("eight", "eight8eight");
                data = data.replaceAll("nine", "nine9nine");

            // Part 1
            char[] array = data.toCharArray();
            for (char ch : array) {
                boolean isDigit = Character.isDigit(ch);
                if (isDigit) {
                    if (!firstSet) {
                        first = Character.digit(ch,10);
                        firstSet = true;
                    }
                    //System.out.println(ch);
                    last = Character.digit(ch, 10);
                }

            }
            //System.out.println(data);
            int value = Integer.parseInt(first+""+last);
            System.out.println(value);
            score += value;
        }
        System.out.println(score);
    }

    public static Scanner getScanner(String fileName) {
        File file = new File(fileName);
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new Error("File not found");
        }
    }

    public static class Game {
        public int id;
        public int[] values = new int[3];
        Game(int id, int r, int g, int b) {
            this.id = id;
            this.values[0] = r;
            this.values[1] = g;
            this.values[2] = b;
        }
        Game(String s) {
            String[] split = s.split(":");
            this.id = Integer.parseInt(split[0].substring(5));
            String[] games = split[1].split(";");
            this.values = new int[]{0, 0, 0};
            for (String game : games) {
                var cubes = game.split(",");
                for (String cube : cubes) {
                    cube = cube.trim();
                    int value = Integer.parseInt(cube.substring(0,cube.indexOf(" ")));
                    if (value > this.values[getColor(cube).ordinal()]) {
                        this.values[getColor(cube).ordinal()] = value;
                    }
                }
            }
        }

        Colors getColor(String s) {
            if (s.endsWith("red")) return Colors.Red;
            if (s.endsWith("green")) return Colors.Green;
            if (s.endsWith("blue")) return Colors.Blue;
            return null;
        }

        @Override
        public String toString() {
            return "id "+ id + " values " + Arrays.toString(values);
        }

        enum Colors {Red, Green, Blue};
    }
}