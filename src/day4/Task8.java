package day4;

/*
On the other hand, it might be wise to try a different strategy: let the giant squid win.

You aren't sure how many bingo boards a giant squid could play at once, so rather than waste time counting its arms, the safe thing to do is to figure out which board will win last and choose that one. That way, no matter which boards it picks, it will win for sure.

In the above example, the second board is the last to win, which happens after 13 is eventually called and its middle column is completely marked. If you were to keep playing until this point, the second board would have a sum of unmarked numbers equal to 148 for a final score of 148 * 13 = 1924.

Figure out which board will win last. Once it wins, what would its final score be?
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Task8 {
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
            int indexLastWinNumber = Collections.max(minSetList);
            int indexLastWinDesk = minSetList.indexOf(indexLastWinNumber);

            int sum = 0;
            int sumNumber = 0;

            int[][] winDesk = desks.get(indexLastWinDesk);
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    sum += winDesk[j][k];
                    for (int i = 0; i <= indexLastWinNumber; i++) {
                        if (winDesk[j][k] == numbers.get(i))
                            sumNumber += winDesk[j][k];
                    }
                }
            }
            System.out.println(sum - sumNumber);
            System.out.println(numbers.get(indexLastWinNumber));
            System.out.println((sum - sumNumber) * numbers.get(indexLastWinNumber));

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
                    }
                    temp++;
                }
            }
            minSet.add(min);
        }
        return minSet;
    }

}
