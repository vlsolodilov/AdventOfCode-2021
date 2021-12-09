package day9;

/*
These caves seem to be lava tubes. Parts are even still volcanically active; small hydrothermal vents release smoke into the caves that slowly settles like rain.

If you can model how the smoke flows through the caves, you might be able to avoid it and be that much safer. The submarine generates a heightmap of the floor of the nearby caves for you (your puzzle input).

Smoke flows to the lowest point of the area it's in. For example, consider the following heightmap:

2199943210
3987894921
9856789892
8767896789
9899965678

Each number corresponds to the height of a particular location, where 9 is the highest and 0 is the lowest a location can be.

Your first goal is to find the low points - the locations that are lower than any of its adjacent locations. Most locations have four adjacent locations (up, down, left, and right); locations on the edge or corner of the map have three or two adjacent locations, respectively. (Diagonal locations do not count as adjacent.)

In the above example, there are four low points, all highlighted: two are in the first row (a 1 and a 0), one is in the third row (a 5), and one is in the bottom row (also a 5). All other locations on the heightmap have some lower adjacent location, and so are not low points.

The risk level of a low point is 1 plus its height. In the above example, the risk levels of the low points are 2, 1, 6, and 6. The sum of the risk levels of all low points in the heightmap is therefore 15.

Find all of the low points on your heightmap. What is the sum of the risk levels of all low points on your heightmap?
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Task15 {
    public static void main(String[] args) {
        Scanner scanner = null;
        List<int[]> heightmap = new ArrayList<>();
        try {
            scanner = new Scanner(new File(".\\src\\day9\\day9input.txt"));
            int length = 0;
            while(scanner.hasNextLine()){
                String[] str = scanner.nextLine().split("");
                length = str.length;
                int[] line = new int[length + 2];
                for (int i = 0; i < str.length; i++) {
                    line[0] = 9;
                    line[i+1] = Integer.parseInt(str[i]);
                    line[str.length + 1] = 9;
                }
                heightmap.add(line);
            }
            int[] boundaryLine = new int[length + 2];
            Arrays.fill(boundaryLine, 9);
            heightmap.add(0, boundaryLine);
            heightmap.add(boundaryLine);

            int sumRiskLevels = 0;
            for (int i = 1; i < heightmap.size()-1; i++) {
                for (int j = 1; j < length + 1; j++) {
                    if (heightmap.get(i)[j] < heightmap.get(i-1)[j] &&
                            heightmap.get(i)[j] < heightmap.get(i+1)[j] &&
                            heightmap.get(i)[j] < heightmap.get(i)[j-1] &&
                            heightmap.get(i)[j] < heightmap.get(i)[j+1])
                        sumRiskLevels += 1 + heightmap.get(i)[j];
                }
            }

            System.out.println(sumRiskLevels);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
