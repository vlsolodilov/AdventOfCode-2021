package day7;

/*
The crabs don't seem interested in your proposed solution. Perhaps you misunderstand crab engineering?

As it turns out, crab submarine engines don't burn fuel at a constant rate. Instead, each change of 1 step in horizontal position costs 1 more unit of fuel than the last: the first step costs 1, the second step costs 2, the third step costs 3, and so on.

As each crab moves, moving further becomes more expensive. This changes the best horizontal position to align them all on; in the example above, this becomes 5:

    Move from 16 to 5: 66 fuel
    Move from 1 to 5: 10 fuel
    Move from 2 to 5: 6 fuel
    Move from 0 to 5: 15 fuel
    Move from 4 to 5: 1 fuel
    Move from 2 to 5: 6 fuel
    Move from 7 to 5: 3 fuel
    Move from 1 to 5: 10 fuel
    Move from 2 to 5: 6 fuel
    Move from 14 to 5: 45 fuel

This costs a total of 168 fuel. This is the new cheapest possible outcome; the old alignment position (2) now costs 206 fuel instead.

Determine the horizontal position that the crabs can align to using the least fuel possible so they can make you an escape route! How much fuel must they spend to align to that position?
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Task13 {
    public static void main(String[] args) {
        Scanner scanner = null;
        List<Integer> positions = new ArrayList<>();

        try {
            scanner = new Scanner(new File(".\\src\\day7\\day7input.txt"));
            String[] str = scanner.nextLine().split(",");
            for (String s : str) {
                positions.add(Integer.parseInt(s));
            }

            int minPos = Collections.min(positions);
            int maxPos = Collections.max(positions);

            List<Integer> fuels = new ArrayList<>();
            for (int i = minPos; i <= maxPos; i++) {
                int fuel = 0;
                for (int j = 0; j < positions.size(); j++) {
                    fuel += getFuelByDistance(positions.get(j), i);
                }
                fuels.add(fuel);
            }

            System.out.println(Collections.min(fuels));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int getFuelByDistance(int startPos, int finishPos) {
        int cost = 0;
        int fuel = 0;
        for (int i = 0; i < Math.abs(finishPos - startPos); i++) {
            cost++;
            fuel += cost;
        }
        return fuel;
    }

}
