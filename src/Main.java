import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
//        day1();
//        day2();
//        day3();
        day4();
    }
    private static void day4() {
        long score = 0;
        ArrayList<Integer> machesArr = new ArrayList<>();
        Scanner day4 = getScanner("input/day_4.txt");
        while (day4.hasNext()) {
            int maches = 0;
            var data = day4.nextLine();
            var cardID = data.split(":")[0].substring(5).replaceAll(" ","");
            var numbers = data.split(":")[1];
            var winning = numbers.split("\\|")[0].trim().split(" ");
            String num = numbers.split("\\|")[1]+" ";
            for (String ch : winning) {
                if (ch != "") {
                    if (num.contains(String.valueOf(' '+ch+' '))) {
//                        System.out.println(num + " contains "+ch);
                        maches++;
                    };
                }
            }
            machesArr.add(Integer.parseInt(cardID)-1 ,maches);
            int score2;
            if (maches > 0) {
                score2 = (int) Math.pow(2, (maches - 1));
            } else { score2 = 0; }
//            System.out.println(maches + " " + score2);
            score += (long) score2;
        }
        System.out.println(Arrays.deepToString(new ArrayList[]{machesArr}));
        // Part 2
//        ArrayList<Integer> numOfCard = new ArrayList<>(250);
        int numOfCards[] = Arrays.stream(new int[machesArr.size()]).map(a -> a = 1).toArray();
        System.out.println(Arrays.toString(numOfCards));
        for (int k = 0; k < machesArr.size(); k++) {
            int maches = machesArr.get(k);
//            int maches = numOfCards[k];
            System.out.println(maches + " " + k);
//            numOfCard.add(k, 1);
            if (maches > 0) {
                for (int i = 1; i < (maches+1); i++) {
                        numOfCards[k+i] += numOfCards[k];
                }
            }
        }
        long sum = Arrays.stream(numOfCards).sum();

        System.out.println("Sum "+sum);

        System.out.println(score);
    }

    private static void day3() {
        Scanner day3 = getScanner("input/day_3.txt");
        char[][] schem = new char[140][139];
        Set<Integer> toAdd = new HashSet<>();
        ArrayList<List<Integer>> values = new ArrayList<>();
        int i = 0;
        int score = 0;
        int gearSum = 0;
        while (day3.hasNext()) {
            var data = day3.next();
            ArrayList<Integer> results = new ArrayList<>();
            schem[i] = data.toCharArray();

            for (int k = 0; k < data.length(); k++) {
                int result = 0;
                if (Character.isDigit(data.toCharArray()[k])) {
                    for (int j = 0; j < 10; j++) {
                        try {
                            int digit = Integer.parseInt(String.valueOf(data.charAt(k+j)));
//                            System.out.println(digit);
                            schem[i][k+j] = Character.forDigit(results.size(), 16);
                            result *= 10;
                            result += digit;
                        } catch (NumberFormatException a) {
                            k += j-1;
                            break;
                        } catch (StringIndexOutOfBoundsException b) {
                            break;
                        }
                    }
                    results.add(result);
//                    System.err.println(results.size());
//                    System.out.println("res2 "+result);
                } else {
//                    System.out.println("Not digit"+data.toCharArray()[k]+"k: "+k);
                }
            }
            values.add(results.stream().toList());

            i++;
        }
        for (int k = 0; k < schem.length; k++) {
            char[] chars = schem[k];
            for (int l = 0; l < chars.length; l++) {
                char ch = chars[l];
                if (Character.isDigit(ch) || ch == '.' || Character.isAlphabetic(ch)) {
                    continue;
                } else {
                    System.out.print(ch);
                    if (k > 0 && l > 0) {
                        char leftTop = schem[k-1][l-1];
                        char top = schem[k-1][l];
                        char rightTop = schem[k-1][l+1];
                        char left = schem[k][l-1];
                        char right = schem[k][l+1];
                        char leftBottom = schem[k+1][l-1];
                        char bottom = schem[k+1][l];
                        char rightBottom = schem[k+1][l+1];
                        if (isHex(leftTop)) {toAdd.add(getValue(leftTop)+16*(k-1));}
                        if (isHex(top)) {toAdd.add(getValue(top)+16*(k-1));}
                        if (isHex(rightTop)) {toAdd.add(getValue(rightTop)+16*(k-1));}
                        if (isHex(left)) {toAdd.add(getValue(left)+16*k);}
                        if (isHex(right)) {toAdd.add(getValue(right)+16*k);}
                        if (isHex(leftBottom)) {toAdd.add(getValue(leftBottom)+16*(k+1));}
                        if (isHex(bottom)) {toAdd.add(getValue(bottom)+16*(k+1));}
                        if (isHex(rightBottom)) {toAdd.add(getValue(rightBottom)+16*(k+1));}
                    }
                }
                // Part 2
                if (ch == '*') {
                    if (k > 0 && l > 0) {
                        HashSet<Integer> gearParts = new HashSet<>();
                        char leftTop = schem[k-1][l-1];
                        char top = schem[k-1][l];
                        char rightTop = schem[k-1][l+1];
                        char left = schem[k][l-1];
                        char right = schem[k][l+1];
                        char leftBottom = schem[k+1][l-1];
                        char bottom = schem[k+1][l];
                        char rightBottom = schem[k+1][l+1];
                        if (isHex(leftTop)) {gearParts.add(getValue(leftTop)+16*(k-1));}
                        if (isHex(top)) {gearParts.add(getValue(top)+16*(k-1));}
                        if (isHex(rightTop)) {gearParts.add(getValue(rightTop)+16*(k-1));}
                        if (isHex(left)) {gearParts.add(getValue(left)+16*k);}
                        if (isHex(right)) {gearParts.add(getValue(right)+16*k);}
                        if (isHex(leftBottom)) {gearParts.add(getValue(leftBottom)+16*(k+1));}
                        if (isHex(bottom)) {gearParts.add(getValue(bottom)+16*(k+1));}
                        if (isHex(rightBottom)) {gearParts.add(getValue(rightBottom)+16*(k+1));}
                        if (gearParts.size() == 2) {
                            int gearA = gearParts.stream().toList().get(0);
                            int gearB = gearParts.stream().toList().get(1);
                            int i1 = gearA % 16;
                            int i2 = (gearA - i1) / 16;
                            try {
                                int gearAA = values.get(i2).get(i1);
                                int i1b = gearB % 16;
                                int i2b = (gearB - i1b) / 16;
                                int gearBB = values.get(i2b).get(i1b);
                                gearSum += gearAA * gearBB;
                            } catch (Exception e) {
                            }

                        }
                    }
                }
            }
        }
        System.out.println(toAdd);
        for (int in : toAdd) {
            int i1 = in % 16;
            int i2 = (in - i1) / 16;
            try {
                score += values.get(i2).get(i1);
            } catch (Exception e) {
//                System.err.println(e);
            }
        }
        System.out.println("Score "+score);
        System.out.println("Gear sum " + gearSum);

//        System.out.println(values);
//        System.out.println(Arrays.deepToString(schem));
    }
    private static boolean isHex(char ch) {
        return Character.isDigit(ch) || Character.isAlphabetic(ch);
    }
    private static int getValue(char ch) {
        return Integer.parseInt(String.valueOf(ch),16);
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