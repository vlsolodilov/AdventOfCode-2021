package day6;

/*
Suppose the lanternfish live forever and have unlimited food and space. Would they take over the entire ocean?

After 256 days in the example above, there would be a total of 26984457539 lanternfish!

How many lanternfish would there be after 256 days?
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

public class Task11 {

    public static void main(String[] args) {
        Scanner scanner = null;
        List<Integer> lanternfishs = new ArrayList<>();
        try {
            scanner = new Scanner(new File(".\\src\\day6\\task10input.txt"));
            String[] str = scanner.nextLine().split(",");
            for (String s : str) {
                lanternfishs.add(Integer.parseInt(s));
            }

            BigInteger count = BigInteger.valueOf(0);
            //int count =0;
            List<BigInteger> countByDays = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                countByDays.add(BigInteger.valueOf(0));
            }
            for (int i = 0; i < lanternfishs.size(); i++) {
                countByDays.set(lanternfishs.get(i), countByDays.get(lanternfishs.get(i)).add(BigInteger.valueOf(1)));
            }

            System.out.println();

            for (int i = 0; i < 256; i++) {
                BigInteger temp = countByDays.get(0);
                for (int j = 0; j < 8; j++) {
                    countByDays.set(j, countByDays.get(j+1));
                }
                countByDays.set(8, temp);
                countByDays.set(6, countByDays.get(6).add(temp));

            }
            System.out.println();
            for (int i = 0; i < 9; i++) {
                count = count.add(countByDays.get(i));
            }
            System.out.println(count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
