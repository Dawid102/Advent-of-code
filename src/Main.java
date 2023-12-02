import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        day1();
    }

    private static void day1() {
        Scanner day1 = getScanner("input/day_1.txt");
        int score = 0;
        while (day1.hasNext()) {
            var data = day1.nextLine();
            int first = 0;
            boolean firstSet = false;
            int last = 0;
                data = data.replaceAll("one", "one1one");
                data = data.replaceAll("two", "two2two");
                data = data.replaceAll("three", "three3three");
                data = data.replaceAll("four", "four4four");
                data = data.replaceAll("five", "five5five");
                data = data.replaceAll("six", "six6six");
                data = data.replaceAll("seven", "seven7seven");
                data = data.replaceAll("eight", "eight8eight");
                data = data.replaceAll("nine", "nine9nine");

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
}