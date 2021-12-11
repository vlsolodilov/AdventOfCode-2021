package day11;

/*
It seems like the individual flashes aren't bright enough to navigate. However, you might have a better option: the flashes seem to be synchronizing!

In the example above, the first time all octopuses flash simultaneously is step 195:

After step 193:
5877777777
8877777777
7777777777
7777777777
7777777777
7777777777
7777777777
7777777777
7777777777
7777777777

After step 194:
6988888888
9988888888
8888888888
8888888888
8888888888
8888888888
8888888888
8888888888
8888888888
8888888888

After step 195:
0000000000
0000000000
0000000000
0000000000
0000000000
0000000000
0000000000
0000000000
0000000000
0000000000

If you can calculate the exact moments when the octopuses will all flash simultaneously, you should be able to navigate through the cavern. What is the first step during which all octopuses flash?
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Task20 {
    private  static List<int[]> matrix = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(".\\src\\day11\\day11input.txt"));
            int length = 0;
            while(scanner.hasNextLine()){
                String[] str = scanner.nextLine().split("");
                length = str.length;
                int[] line = new int[length + 2];
                for (int i = 0; i < str.length; i++) {
                    line[0] = 0;
                    line[i+1] = Integer.parseInt(str[i]);
                    line[str.length + 1] = 0;
                }
                matrix.add(line);
            }
            int[] boundaryLine = new int[length + 2];
            Arrays.fill(boundaryLine, 0);
            matrix.add(0, boundaryLine);
            matrix.add(boundaryLine);

            int flashes = 0;
            int step = 0;
            while (flashes != 100){
                flashes = 0;
                step++;
                for (int i = 1; i < matrix.size()-1; i++) {
                    for (int j = 1; j < length + 1; j++) {
                        matrix.get(i)[j]++;
                    }
                }
                int count = 1;
                while (count > 0){
                    count --;
                    for (int i = 1; i < matrix.size()-1; i++) {
                        for (int j = 1; j < length + 1; j++) {
                            if (matrix.get(i)[j] > 9 && matrix.get(i)[j] < 100) {
                                matrix.get(i)[j] = 100;
                                increasesEnergyLevelAdjacentOctopuses(i,j);
                                count++;
                            }
                        }
                    }
                }

                for (int i = 1; i < matrix.size()-1; i++) {
                    for (int j = 1; j < length + 1; j++) {
                        if (matrix.get(i)[j] > 9) {
                            matrix.get(i)[j] = 0;
                            flashes++;
                        }
                    }
                }
            }

            System.out.println(step);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void increasesEnergyLevelAdjacentOctopuses(int x, int y) {
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                matrix.get(i)[j]++;
            }
        }
    }
}
