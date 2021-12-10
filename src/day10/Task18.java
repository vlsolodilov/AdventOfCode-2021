package day10;

/*
Now, discard the corrupted lines. The remaining lines are incomplete.

Incomplete lines don't have any incorrect characters - instead, they're missing some closing characters at the end of the line. To repair the navigation subsystem, you just need to figure out the sequence of closing characters that complete all open chunks in the line.

You can only use closing characters (), ], }, or >), and you must add them in the correct order so that only legal pairs are formed and all chunks end up closed.

In the example above, there are five incomplete lines:

    [({(<(())[]>[[{[]{<()<>> - Complete by adding }}]])})].
    [(()[<>])]({[<{<<[]>>( - Complete by adding )}>]}).
    (((({<>}<{<{<>}{[]{[]{} - Complete by adding }}>}>)))).
    {<[[]]>}<{[{[{[]{()[[[] - Complete by adding ]]}}]}]}>.
    <{([{{}}[<[[[<>{}]]]>[]] - Complete by adding ])}>.

Did you know that autocomplete tools also have contests? It's true! The score is determined by considering the completion string character-by-character. Start with a total score of 0. Then, for each character, multiply the total score by 5 and then increase the total score by the point value given for the character in the following table:

    ): 1 point.
    ]: 2 points.
    }: 3 points.
    >: 4 points.

So, the last completion string above - ])}> - would be scored as follows:

    Start with a total score of 0.
    Multiply the total score by 5 to get 0, then add the value of ] (2) to get a new total score of 2.
    Multiply the total score by 5 to get 10, then add the value of ) (1) to get a new total score of 11.
    Multiply the total score by 5 to get 55, then add the value of } (3) to get a new total score of 58.
    Multiply the total score by 5 to get 290, then add the value of > (4) to get a new total score of 294.

The five lines' completion strings have total scores as follows:

    }}]])})] - 288957 total points.
    )}>]}) - 5566 total points.
    }}>}>)))) - 1480781 total points.
    ]]}}]}]}> - 995444 total points.
    ])}> - 294 total points.

Autocomplete tools are an odd bunch: the winner is found by sorting all of the scores and then taking the middle score. (There will always be an odd number of scores to consider.) In this example, the middle score is 288957 because there are the same number of scores smaller and larger than it.

Find the completion string for each incomplete line, score the completion strings, and sort the scores. What is the middle score?
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Task18 {
    private static String[] line;
    public static void main(String[] args) {
        Scanner scanner = null;
        List<String[]> lines = new ArrayList<>();
        List<Long> scores = new ArrayList<>();
        try {
            scanner = new Scanner(new File(".\\src\\day10\\day10input.txt"));
            while(scanner.hasNextLine()){
                String[] str = scanner.nextLine().split("");
                line = str;
                lines.add(removeChunk());
            }
            List<String[]> incompleteLines = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                boolean corrupted = false;
                for (int j = 0; j < lines.get(i).length; j++) {
                    if (lines.get(i)[j].equals(")") ||
                            lines.get(i)[j].equals("]") ||
                            lines.get(i)[j].equals("}") ||
                            lines.get(i)[j].equals(">"))
                        corrupted = true;
                }
                if (!corrupted)
                    incompleteLines.add(lines.get(i));
            }

            for (int i = 0; i < incompleteLines.size(); i++) {
                long score = 0;
                for (int j = incompleteLines.get(i).length - 1; j >= 0; j--) {
                    score *= 5;
                    if (incompleteLines.get(i)[j].equals("(")) {
                        score += 1;
                    }
                    if (incompleteLines.get(i)[j].equals("[")) {
                        score += 2;
                    }
                    if (incompleteLines.get(i)[j].equals("{")) {
                        score += 3;
                    }
                    if (incompleteLines.get(i)[j].equals("<")) {
                        score += 4;
                    }
                }
                scores.add(score);
            }

            Collections.sort(scores);

            System.out.println(scores.get(scores.size()/2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String[] removeChunk() {
        for (int i = 1; i < line.length; i++) {
            if (line[i].equals(")") && line[i-1].equals("(")) {
                    List<String> tempList = new ArrayList<>(Arrays.asList(line));
                    tempList.remove(i);
                    tempList.remove(i-1);
                    line = tempList.toArray(new String[0]);
                removeChunk();
            } else
            if (line[i].equals("}") && line[i-1].equals("{")) {
                List<String> tempList = new ArrayList<>(Arrays.asList(line));
                tempList.remove(i);
                tempList.remove(i-1);
                line = tempList.toArray(new String[0]);
                removeChunk();
            } else
            if (line[i].equals("]") && line[i-1].equals("[")) {
                List<String> tempList = new ArrayList<>(Arrays.asList(line));
                tempList.remove(i);
                tempList.remove(i-1);
                line = tempList.toArray(new String[0]);
                removeChunk();
            } else
            if (line[i].equals(">") && line[i-1].equals("<")) {
                List<String> tempList = new ArrayList<>(Arrays.asList(line));
                tempList.remove(i);
                tempList.remove(i-1);
                line = tempList.toArray(new String[0]);
                removeChunk();
            }

        }
        return line;
    }
}
