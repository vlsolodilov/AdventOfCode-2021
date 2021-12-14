package day14;

/*
The resulting polymer isn't nearly strong enough to reinforce the submarine. You'll need to run more steps of the pair insertion process; a total of 40 steps should do it.

In the above example, the most common element is B (occurring 2192039569602 times) and the least common element is H (occurring 3849876073 times); subtracting these produces 2188189693529.

Apply 40 steps of pair insertion to the polymer template and find the most and least common elements in the result. What do you get if you take the quantity of the most common element and subtract the quantity of the least common element?
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Task26 {

    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            String template = "OKSBBKHFBPVNOBKHBPCO";
            String template_test = "NNCB";
            Map<String,String> pairsMap = new HashMap<>();
            scanner = new Scanner(new File(".\\src\\day14\\day14input.txt"));
            while(scanner.hasNextLine()){
                String[] str = scanner.nextLine().split(" -> ");
                pairsMap.put(str[0], str[1]);
            }

            Map<String, Long> pairCount = new HashMap<>();
            String[] str = template.split("");
            for (int i = 0; i < str.length - 1; i++) {
                if (!pairCount.containsKey(str[i] + str[i + 1]))
                    pairCount.put(str[i] + str[i + 1], 1L);
                else
                    pairCount.merge(str[i] + str[i + 1], 1L, Long::sum);
            }

            Map<String, Long> letterCount = new HashMap<>();
            for (int i = 0; i < str.length; i++) {
                if (!letterCount.containsKey(str[i]))
                    letterCount.put(str[i], 1L);
                else
                    letterCount.merge(str[i], 1L, Long::sum);
            }

            Map<String, Long> newPairCount = new HashMap<>(pairCount);
            String letter;
            for (int i = 0; i < 40; i++) {
                for (String pair : pairCount.keySet()) {
                    long count = pairCount.get(pair);
                    str = pair.split("");
                    letter = pairsMap.get(pair);
                    if (!newPairCount.containsKey(str[0] + letter))
                        newPairCount.put(str[0] + letter, count);
                    else
                        newPairCount.merge(str[0] + letter, count, Long::sum);
                    if (!newPairCount.containsKey(letter + str[1]))
                        newPairCount.put(letter + str[1], count);
                    else
                        newPairCount.merge(letter + str[1], count, Long::sum);
                    if (!letterCount.containsKey(letter))
                        letterCount.put(letter, count);
                    else
                        letterCount.merge(letter, count, Long::sum);
                    newPairCount.merge(pair, -count, Long::sum);
                    if (newPairCount.get(pair) == 0)
                        newPairCount.remove(pair);
                }
                pairCount = new HashMap<>(newPairCount);
            }

            System.out.println(Collections.max(letterCount.values()) - Collections.min(letterCount.values()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
