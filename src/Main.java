import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Main {
    public static void main(String[] args) {
//        day1();
//        day2();
//        day3();
//        day4();
//        day5(); TODO:
        day6();
    }

    private static void day6() {
        Scanner day6 = getScanner("input/day_6.txt");
        ArrayList<Integer> waysToBeat = new ArrayList<>();
        String data = day6.nextLine();
        int[] time = Arrays.stream(data.substring(5).strip().split(" ")).filter(v -> v != "").mapToInt(Integer::parseInt).toArray();
        String  timeSt = Arrays.stream(data.substring(5).strip().split(" ")).filter(v -> v != "").collect(Collectors.joining());
        long time2 = Long.parseLong(timeSt);
        String data2 = day6.nextLine();
        int[] dist = Arrays.stream(data2.substring(10).strip().split(" ")).filter(v -> v != "").mapToInt(Integer::parseInt).toArray();
        long dist2 = Long.parseLong(Arrays.stream(data2.substring(10).strip().split(" ")).filter(v -> v != "").collect(Collectors.joining()));
        System.out.println(Arrays.toString(dist));
        System.out.println(dist2);
        System.out.println(Arrays.toString(time));
        System.out.println(time2);
        for (int i = 0; i < time.length; i++) {
            int score = 0;
            int timeI = time[i];
            int distI = dist[i];
            for (int j = 0; j < timeI; j++) {
                int remainingTime = timeI-j;
                if (remainingTime * j > distI) {
                    score++;
                }
            }
            waysToBeat.add(score);
        }
        int score2 = 0;
        for (int i = 0; i < time2; i++) {
            long remaining = time2-i;
            if (remaining * i > dist2) {
                score2++;
            }
        }
//        System.out.println(waysToBeat);
        int multi = 1;
        for (int a : waysToBeat) {
            multi *= a;
        }
        System.out.println(multi);
        System.out.println(score2);
    }

    private static void day5() {
        Scanner day5 = getScanner("input/day_5.txt");
        ArrayList<ArrayList<ArrayTo>> arrayTo = new ArrayList<>();
//                                     Type  Id in seeds
        // Part 1
        long[][] seedToLocation = new long[8][20];
        String[] seeds = null;
        int mode = 0;
        while (day5.hasNext()) {
            String data = day5.nextLine();
            if (data == "") continue;
            if (data.contains(":")) {
                if (data.startsWith("seeds")) {
                    for (long[] longs : seedToLocation) {
                        Arrays.fill(longs, -1);
                    }
                    seeds = data.split(":")[1].trim().split(" ");
                    String[] finalSeeds = seeds;
                    var seedsAmount = IntStream.range(0, seeds.length)
                            .filter(i -> (i % 2) == 1)
                            .mapToObj(i -> finalSeeds[i])
                            .mapToInt(Integer::parseInt);
                    var seeds3 = IntStream.range(0, seeds.length)
                            .filter(i -> (i % 2) == 0)
                            .mapToObj(i -> finalSeeds[i])
                            .mapToLong(Long::parseLong);
                    ArrayList<ArrayTo> array = new ArrayList<>();
                    int i = 0;
                    for (long a : seeds3.toArray()) {
                        array.add(new ArrayTo(a, a, Arrays.stream(seeds).mapToLong(Long::parseLong).toArray()[i*2+1]));
                        i++;
                    }
                    arrayTo.add(array);

//                    System.out.println(Arrays.toString(seeds));
                    System.out.println(seedsAmount.sum());
                    seedToLocation[0] = Arrays.stream(seeds).mapToLong(Long::parseLong).toArray();

                } else {
                    int finalMode1 = mode;
                    if (mode != 0) {
                        arrayTo.get(mode-1).stream().filter(v -> !v.modified).forEach(v -> {
                            arrayTo.get(finalMode1).add(v);
                        });
                        for (int i = 0; i < seedToLocation[mode].length; i++) {
                            if (seedToLocation[mode][i] == -1) {
                                seedToLocation[mode][i] = seedToLocation[mode - 1][i];
                            }
                        }
                    }
                    mode++;
                }
            } else {
                String[] num = data.trim().split(" ");
//                System.out.println(Arrays.toString(num));
                long id = Long.parseLong(num[0]);
                long min = Long.parseLong(num[1]);
                long range = Long.parseLong(num[2]);
                long max = min + range - 1;
                int finalMode = mode -1;
                ArrayTo element = new ArrayTo(id,min,range);
                // Part 2
                ArrayList<ArrayTo> toAdd = new ArrayList<>();
                arrayTo.get(finalMode).stream().filter(v -> v.contains(min,max)).forEach(v -> {
//                    System.out.println(v + " " + min + " " + max + " " + v.contains(min,max));
                    System.err.println(Arrays.toString(num));
                    System.err.println(v);
                    if (element.start >= v.value && element.start <= v.value + v.size - 1) {
                        long value = element.value;
                        long start = element.start;
                        if (element.end >= v.value+v.size-1) {
                            //A
                            v.modified = true;
                            long size = v.value + v.size - 1 - element.start;
                            toAdd.add(new ArrayTo(value,start,size));
                            if (size > v.size) throw new RuntimeException("A");
//                            toAdd.add(new ArrayTo(v.value,v.start,start-v.value+1));
                            System.err.println(value + " " + start + " " + size);

                        } else {
                            //B
                            v.modified = true;
                            long size = element.size;
                            if (size > v.size) throw new RuntimeException("A");
                            toAdd.add(new ArrayTo(value,start,size));
//                            toAdd.add(new ArrayTo(v.value,v.start,start-v.value+1));
//                            if (v.value+v.size-1 > element.end) {
//                                toAdd.add(new ArrayTo(element.end + 1, element.end, v.value + v.size - 1 - element.end));
//                            }
                            System.err.println(value + " " + start + " " + size);

                        }
                    } else if (element.start <= v.value && element.end >= v.value) {
                        long value = v.value + element.valueAtZero;
                        long start = v.value;
                        if (element.end >= v.value+v.size-1) {
                            //C
                            v.modified = true;
                            System.err.println("C");
                            long size = v.size;
                            toAdd.add(new ArrayTo(value,start,size));
                            System.err.println(value + " " + start + " " + size);

                        } else {
                            //D
                            v.modified = true;
                            System.err.println("D");
                            long size = element.end - v.value + 1;
                            if (size > v.size) throw new RuntimeException("A");
                            toAdd.add(new ArrayTo(value,start,size));
//                            if (v.value+v.size-1 > element.end) {
//                                toAdd.add(new ArrayTo(v.value+size,element.end+1,v.value+v.size- element.end));
//                            }
                            System.err.println(value + " " + start + " " + size);

                        }
                    } 
/*
                    if (v.contains(min, max)) {
                        if (v.value <= element.start && v.value + v.size - 1 >= element.end) {
                            System.err.println("A");
                            v.modified = true;
                            long value = v.value+element.valueAtZero;
                            long start = v.value;
                            long size = v.size;
                            toAdd.add(new ArrayTo(value,start,size));
                            System.err.println(value + " " + start + " " + size);
                        } else if (v.value <= element.start && v.value+v.size-1 >= element.start) {
                            System.err.println("B");
                            v.modified = true;
                            long value = v.value+element.valueAtZero;
                            long start = v.value;
                            long size = element.end-v.value;
                            toAdd.add(new ArrayTo(value,start,size));
                            System.err.println(value + " " + start + " " + size);
                        } else if (v.value + v.size - 1 >= element.end && v.value <= element.end) {
                            System.err.println("C");
                            v.modified = true;
                            long value = v.start-element.start+ element.value;
                            long start = element.start;
                            long size = v.size-element.start+v.value;
                            toAdd.add(new ArrayTo(value,start,size));
                            System.err.println(value + " " + start + " " + size);
                        } else {
                            System.err.println("MAtch not found");
                        }
*/
/*
                        if (v.value >= min && v.value+v.size-1 <= max) {
                        System.err.println(1);
                            v.modified = true;
                            long value = v.value+element.valueAtZero;
                            long start = v.value;
                            long size = v.size;
                            toAdd.add(new ArrayTo(value,start,size));
                            System.err.println(value + " " + start + " " + size);
                        } else if (v.value >= min && v.value <= element.end) {
                            v.modified = true;
                            long value = v.value+element.valueAtZero;
                            long start = v.value;
                            long size = element.end-v.value;
                            toAdd.add(new ArrayTo(value,start,size));
                            System.err.println(2);
                            System.err.println(value + " " + start + " " + size);
                        } else if (v.value+v.size -1 <= max && v.value+v.size-1 >= element.start) {
                            v.modified = true;
                            long value = v.start-element.start+ element.value;
                            long start = element.start;
                            long size = v.size-element.start+v.value;
                            System.err.println(3);
                            toAdd.add(new ArrayTo(value,start,size));
                            System.err.println(value + " " + start + " " + size);
                        } else {
//                            System.err.println(4);
                        }
*/

//                    toAdd.add(new ArrayTo(v.value,v.start,v.size));
                });
                if (arrayTo.size() == mode) {
                    arrayTo.add(toAdd);
                } else {
                    arrayTo.getLast().addAll(toAdd);
                }
//                System.out.println(toAdd);


                // Part 1
                Arrays.stream(seedToLocation[finalMode]).forEach(longs -> {
                    if (longs <= max && longs >= min) {
                        long prevId = LongStream.range(0, seedToLocation[finalMode].length).filter(i -> seedToLocation[finalMode][(int) i] == longs).findFirst().orElse(-1);
//                        System.out.println(prevId);
                        if (prevId == -1) return;
                        long diff = id - min;
                        long value = longs + diff;
                        seedToLocation[finalMode +1][(int) prevId] = value;
                    }
                });
            }
        }
        for (int i = 0; i < seedToLocation[mode].length; i++) {
            if (seedToLocation[mode][i] == -1) {
                seedToLocation[mode][i] = seedToLocation[mode-1][i];
            }
        }
//        System.out.println(Arrays.stream(seedToLocation[mode]).min().orElse(-1L));
//        System.out.println(Arrays.deepToString(seedToLocation));
//        System.out.println(arrayTo);
        System.out.println(arrayTo.get(mode).stream().mapToLong(v -> v.value).min());
        System.out.println(Arrays.toString(arrayTo.stream().mapToLong(v -> v.stream().mapToLong(w -> w.size).sum()).toArray()));
//        System.out.println(arrayTo.get(1).stream().mapToLong(w -> w.size).sum());
    }
    public static class ArrayTo {
        long value;
        long start;
        long size;
        long end;
        boolean modified;
        long valueAtZero;
        ArrayTo(long value, long start, long size) {
            this.value = value;
            this.start = start;
            this.size = size;
            this.end = start + size - 1;
            this.valueAtZero = value - start;
        }
        public boolean contains(long min, long max) {
            return (value >= min || value+size-1 <= max);
        }

        @Override
        public String toString() {
            return "ArrayTo{" +
                    "value=" + value +
                    ", start=" + start +
                    ", size=" + size +
                    ", end=" + end +
                    '}';
        }

        public void setStart(long start) {
            this.start = start;
        }

        public void setEnd(long end) {
            this.end = end;
        }
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