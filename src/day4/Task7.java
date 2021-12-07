package day4;

/*
You're already almost 1.5km (almost a mile) below the surface of the ocean, already so deep that you can't see any sunlight. What you can see, however, is a giant squid that has attached itself to the outside of your submarine.

Maybe it wants to play bingo?

Bingo is played on a set of boards each consisting of a 5x5 grid of numbers. Numbers are chosen at random, and the chosen number is marked on all boards on which it appears. (Numbers may not appear on all boards.) If all numbers in any row or any column of a board are marked, that board wins. (Diagonals don't count.)

The submarine has a bingo subsystem to help passengers (currently, you and the giant squid) pass the time. It automatically generates a random order in which to draw numbers and a random set of boards (your puzzle input). For example:

7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7

After the first five numbers are drawn (7, 4, 9, 5, and 11), there are no winners, but the boards are marked as follows (shown here adjacent to each other to save space):

22 13 17 11  0         3 15  0  2 22        14 21 17 24  4
 8  2 23  4 24         9 18 13 17  5        10 16 15  9 19
21  9 14 16  7        19  8  7 25 23        18  8 23 26 20
 6 10  3 18  5        20 11 10 24  4        22 11 13  6  5
 1 12 20 15 19        14 21 16 12  6         2  0 12  3  7

After the next six numbers are drawn (17, 23, 2, 0, 14, and 21), there are still no winners:

22 13 17 11  0         3 15  0  2 22        14 21 17 24  4
 8  2 23  4 24         9 18 13 17  5        10 16 15  9 19
21  9 14 16  7        19  8  7 25 23        18  8 23 26 20
 6 10  3 18  5        20 11 10 24  4        22 11 13  6  5
 1 12 20 15 19        14 21 16 12  6         2  0 12  3  7

Finally, 24 is drawn:

22 13 17 11  0         3 15  0  2 22        14 21 17 24  4
 8  2 23  4 24         9 18 13 17  5        10 16 15  9 19
21  9 14 16  7        19  8  7 25 23        18  8 23 26 20
 6 10  3 18  5        20 11 10 24  4        22 11 13  6  5
 1 12 20 15 19        14 21 16 12  6         2  0 12  3  7

At this point, the third board wins because it has at least one complete row or column of marked numbers (in this case, the entire top row is marked: 14 21 17 24 4).

The score of the winning board can now be calculated. Start by finding the sum of all unmarked numbers on that board; in this case, the sum is 188. Then, multiply that sum by the number that was just called when the board won, 24, to get the final score, 188 * 24 = 4512.

To guarantee victory against the giant squid, figure out which board will win first. What will your final score be if you choose that board?
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Task7 {
    public static void main(String[] args) {
        Scanner scanner = null;
        List<Integer> numbers = new ArrayList<>();
        try {
            scanner = new Scanner(new File(".\\src\\day4\\task7input_numbers.txt"));
            String[] str = scanner.nextLine().split(",");
            for (String s : str) {
                numbers.add(Integer.parseInt(s));
            }
            scanner = new Scanner(new File(".\\src\\day4\\task7input_desk.txt"));
            List<int[][]> desks = new ArrayList<>();
            while(scanner.hasNextLine()){
                scanner.nextLine();
                int[][] desk = new int[5][5];
                for (int i = 0; i < 5; i++) {
                    str = scanner.nextLine().split(" ");
                    int j = 0;
                    for (String s : str) {
                        if (!s.equals("")){
                            desk[i][j] = Integer.parseInt(s);
                            j++;
                        }
                    }
                }
                desks.add(desk);
            }
            List<Integer> minSetList = getMinSetList(numbers, desks);
            int indexWinNumber = Collections.min(minSetList);
            int indexWinDesk = minSetList.indexOf(indexWinNumber);
            int sum = 0;
            int sumNumber = 0;

            int[][] winDesk = desks.get(indexWinDesk);
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    sum += winDesk[j][k];
                    for (int i = 0; i <= indexWinNumber; i++) {
                        if (winDesk[j][k] == numbers.get(i))
                            sumNumber += winDesk[j][k];
                    }
                }
            }
            System.out.println(sum - sumNumber);
            System.out.println(numbers.get(indexWinNumber));
            System.out.println((sum - sumNumber) * numbers.get(indexWinNumber));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> getMinSetList(List<Integer> numbers, List<int[][]> desks) {
        List<Integer> minSet = new ArrayList<>();
        int temp;
        int coincidence;

        for (int[][] desk : desks) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < 5; i++) {
                coincidence = 0;
                temp = 0;
                for (int number : numbers) {
                    for (int j = 0; j < 5; j++) {
                        if (desk[i][j] == number)
                            coincidence++;
                    }
                    if (coincidence == 5 && min > temp) {
                        min = temp;
                        break;
                    }
                    temp++;
                }

            }

            for (int i = 0; i < 5; i++) {
                coincidence = 0;
                temp = 0;
                for (int number : numbers) {
                    for (int j = 0; j < 5; j++) {
                        if (desk[j][i] == number)
                            coincidence++;
                    }
                    if (coincidence == 5 && min > temp) {
                        min = temp;
                        break;
                    }
                    temp++;
                }
            }
            minSet.add(min);
        }

        return minSet;
    }

}
