package day9;

/*
Next, you need to find the largest basins so you know what areas are most important to avoid.

A basin is all locations that eventually flow downward to a single low point. Therefore, every low point has a basin, although some basins are very small. Locations of height 9 do not count as being in any basin, and all other locations will always be part of exactly one basin.

The size of a basin is the number of locations within the basin, including the low point. The example above has four basins.

The top-left basin, size 3:

2199943210
3987894921
9856789892
8767896789
9899965678

The top-right basin, size 9:

2199943210
3987894921
9856789892
8767896789
9899965678

The middle basin, size 14:

2199943210
3987894921
9856789892
8767896789
9899965678

The bottom-right basin, size 9:

2199943210
3987894921
9856789892
8767896789
9899965678

Find the three largest basins and multiply their sizes together. In the above example, this is 9 * 14 * 9 = 1134.

What do you get if you multiply together the sizes of the three largest basins?
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Task16 {
    static int count = 0;
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

            List<int[]> lowPoints = new ArrayList<>();
            for (int i = 1; i < heightmap.size()-1; i++) {
                for (int j = 1; j < length + 1; j++) {
                    if (heightmap.get(i)[j] < heightmap.get(i-1)[j] &&
                            heightmap.get(i)[j] < heightmap.get(i+1)[j] &&
                            heightmap.get(i)[j] < heightmap.get(i)[j-1] &&
                            heightmap.get(i)[j] < heightmap.get(i)[j+1])
                        lowPoints.add(new int[]{i,j});
                }
            }

            List<Integer> basinSizes = new ArrayList<>();
            for (int[] lowPoint : lowPoints) {
                count = 0;
                basinSizes.add(getBasinSize(lowPoint[0], lowPoint[1], heightmap));
            }

            Collections.sort(basinSizes, Collections.reverseOrder());

            System.out.println(basinSizes.get(0)*basinSizes.get(1)*basinSizes.get(2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int getBasinSize(int i, int j, List<int[]> heightmap) {
        List<int[]> matrix = heightmap;
        if (matrix.get(i)[j] != 9) {
            matrix.get(i)[j] = 9;
            count++;
            getBasinSize(i+1, j, matrix);
            getBasinSize(i-1, j, matrix);
            getBasinSize(i, j+1, matrix);
            getBasinSize(i, j-1, matrix);
        }
        return count;
    }
}
