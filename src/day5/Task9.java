package day5;

/*
You come across a field of hydrothermal vents on the ocean floor! These vents constantly produce large, opaque clouds, so it would be best to avoid them if possible.

They tend to form in lines; the submarine helpfully produces a list of nearby lines of vents (your puzzle input) for you to review. For example:

0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2

Each line of vents is given as a line segment in the format x1,y1 -> x2,y2 where x1,y1 are the coordinates of one end the line segment and x2,y2 are the coordinates of the other end. These line segments include the points at both ends. In other words:

    An entry like 1,1 -> 1,3 covers points 1,1, 1,2, and 1,3.
    An entry like 9,7 -> 7,7 covers points 9,7, 8,7, and 7,7.

For now, only consider horizontal and vertical lines: lines where either x1 = x2 or y1 = y2.

So, the horizontal and vertical lines from the above list would produce the following diagram:

.......1..
..1....1..
..1....1..
.......1..
.112111211
..........
..........
..........
..........
222111....

In this diagram, the top left corner is 0,0 and the bottom right corner is 9,9. Each position is shown as the number of lines which cover that point or . if no line covers that point. The top-left pair of 1s, for example, comes from 2,2 -> 2,1; the very bottom row is formed by the overlapping lines 0,9 -> 5,9 and 0,9 -> 2,9.

To avoid the most dangerous areas, you need to determine the number of points where at least two lines overlap. In the above example, this is anywhere in the diagram with a 2 or larger - a total of 5 points.

Consider only horizontal and vertical lines. At how many points do at least two lines overlap?

--- Part Two ---

Unfortunately, considering only horizontal and vertical lines doesn't give you the full picture; you need to also consider diagonal lines.

Because of the limits of the hydrothermal vent mapping system, the lines in your list will only ever be horizontal, vertical, or a diagonal line at exactly 45 degrees. In other words:

    An entry like 1,1 -> 3,3 covers points 1,1, 2,2, and 3,3.
    An entry like 9,7 -> 7,9 covers points 9,7, 8,8, and 7,9.

Considering all lines from the above example would now produce the following diagram:

1.1....11.
.111...2..
..2.1.111.
...1.2.2..
.112313211
...1.2....
..1...1...
.1.....1..
1.......1.
222111....

You still need to determine the number of points where at least two lines overlap. In the above example, this is still anywhere in the diagram with a 2 or larger - now a total of 12 points.

Consider all of the lines. At how many points do at least two lines overlap?

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Task9 {
    public static void main(String[] args) {
        Scanner scanner = null;
        List<Line> lines = new ArrayList<>();
        try {
            scanner = new Scanner(new File(".\\src\\day5\\task9input.txt"));

            while(scanner.hasNextLine()){
                String[] str = scanner.nextLine().replace(" -> ", ",").split(",");
                lines.add(new Line(Integer.parseInt(str[0]),
                        Integer.parseInt(str[2]),
                        Integer.parseInt(str[1]),
                        Integer.parseInt(str[3])));
            }

            int maxX = getMax(lines, "x") + 1;
            int maxY = getMax(lines, "y") + 1;

            int[][] diagram = new int[maxX][maxY];
            fillDiagram(diagram, lines);
            int countOverlapPoints = getCountOverlapPoints(diagram);
            System.out.println(countOverlapPoints);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int getMax(List<Line> lines, String axis) {
        int max = 0;
        for (Line line : lines) {
            if (axis.equals("x")){
                if (line.getX1() > max)
                    max = line.getX1();
                if (line.getX2() > max)
                    max = line.getX2();
            }
            if (axis.equals("y")){
                if (line.getY1() > max)
                    max = line.getY1() ;
                if (line.getY2()  > max)
                    max = line.getY2() ;
            }
        }
        return max;
    }

    private static void fillDiagram(int[][] diagram, List<Line> lines) {
        int minX, maxX, minY, maxY;
        for (Line line : lines) {
            minX = Math.min(line.getX1(), line.getX2());
            maxX = Math.max(line.getX1(), line.getX2());
            minY = Math.min(line.getY1(), line.getY2());
            maxY = Math.max(line.getY1(), line.getY2());
            if (minX == maxX) {
                for (int i = minY; i <= maxY; i++) {
                    diagram[minX][i]++;
                }
            } else if (minY == maxY) {
                for (int i = minX; i <= maxX; i++) {
                    diagram[i][minY]++;
                }
            } else {
                for (int i = 0; i <= (maxX - minX); i++) {
                    if (line.getX1() < line.getX2() && line.getY1() < line.getY2())
                        diagram[line.getX1()+i][line.getY1()+i]++;
                    if (line.getX1() > line.getX2() && line.getY1() < line.getY2())
                        diagram[line.getX1()-i][line.getY1()+i]++;
                    if (line.getX1() < line.getX2() && line.getY1() > line.getY2())
                        diagram[line.getX1()+i][line.getY1()-i]++;
                    if (line.getX1() > line.getX2() && line.getY1() > line.getY2())
                        diagram[line.getX1()-i][line.getY1()-i]++;
                }
            }
        }
    }

    private static int getCountOverlapPoints(int[][] diagram) {
        int count = 0;
        for (int i = 0; i < diagram.length; i++) {
            for (int j = 0; j < diagram[0].length; j++) {
                if (diagram[i][j] >= 2)
                    count++;
            }
        }
        return count;
    }

    static class Line {
        private int x1, x2, y1,y2;

        public Line(int x1, int x2, int y1, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        public int getX1() {
            return x1;
        }

        public void setX1(int x1) {
            this.x1 = x1;
        }

        public int getX2() {
            return x2;
        }

        public void setX2(int x2) {
            this.x2 = x2;
        }

        public int getY1() {
            return y1;
        }

        public void setY1(int y1) {
            this.y1 = y1;
        }

        public int getY2() {
            return y2;
        }

        public void setY2(int y2) {
            this.y2 = y2;
        }
    }
}
