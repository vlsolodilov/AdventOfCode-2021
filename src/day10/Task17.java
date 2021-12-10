package day10;

/*
You ask the submarine to determine the best route out of the deep-sea cave, but it only replies:

Syntax error in navigation subsystem on line: all of them

All of them?! The damage is worse than you thought. You bring up a copy of the navigation subsystem (your puzzle input).

The navigation subsystem syntax is made of several lines containing chunks. There are one or more chunks on each line, and chunks contain zero or more other chunks. Adjacent chunks are not separated by any delimiter; if one chunk stops, the next chunk (if any) can immediately start. Every chunk must open and close with one of four legal pairs of matching characters:

    If a chunk opens with (, it must close with ).
    If a chunk opens with [, it must close with ].
    If a chunk opens with {, it must close with }.
    If a chunk opens with <, it must close with >.

So, () is a legal chunk that contains no other chunks, as is []. More complex but valid chunks include ([]), {()()()}, <([{}])>, [<>({}){}[([])<>]], and even (((((((((()))))))))).

Some lines are incomplete, but others are corrupted. Find and discard the corrupted lines first.

A corrupted line is one where a chunk closes with the wrong character - that is, where the characters it opens and closes with do not form one of the four legal pairs listed above.

Examples of corrupted chunks include (], {()()()>, (((()))}, and <([]){()}[{}]). Such a chunk can appear anywhere within a line, and its presence causes the whole line to be considered corrupted.

For example, consider the following navigation subsystem:

[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]

Some of the lines aren't corrupted, just incomplete; you can ignore these lines for now. The remaining five lines are corrupted:

    {([(<{}[<>[]}>{[]{[(<()> - Expected ], but found } instead.
    [[<[([]))<([[{}[[()]]] - Expected ], but found ) instead.
    [{[{({}]{}}([{[{{{}}([] - Expected ), but found ] instead.
    [<(<(<(<{}))><([]([]() - Expected >, but found ) instead.
    <{([([[(<>()){}]>(<<{{ - Expected ], but found > instead.

Stop at the first incorrect closing character on each corrupted line.

Did you know that syntax checkers actually have contests to see who can get the high score for syntax errors in a file? It's true! To calculate the syntax error score for a line, take the first illegal character on the line and look it up in the following table:

    ): 3 points.
    ]: 57 points.
    }: 1197 points.
    >: 25137 points.

In the above example, an illegal ) was found twice (2*3 = 6 points), an illegal ] was found once (57 points), an illegal } was found once (1197 points), and an illegal > was found once (25137 points). So, the total syntax error score for this file is 6+57+1197+25137 = 26397 points!

Find the first illegal character in each corrupted line of the navigation subsystem. What is the total syntax error score for those errors?
 */

import sun.security.util.ArrayUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Task17 {
    private static String[] line;
    public static void main(String[] args) {
        Scanner scanner = null;
        List<String[]> lines = new ArrayList<>();
        List<String> characters = new ArrayList<>();
        try {
            scanner = new Scanner(new File(".\\src\\day10\\day10input.txt"));
            while(scanner.hasNextLine()){
                String[] str = scanner.nextLine().split("");
                line = str;
                lines.add(removeChunk());
            }
            int score = 0;
            for (int i = 0; i < lines.size(); i++) {
                for (int j = 0; j < lines.get(i).length; j++) {
                    if (lines.get(i)[j].equals(")")) {
                        characters.add(")");
                        score += 3;
                        break;
                    }
                    if (lines.get(i)[j].equals("]")) {
                        characters.add("]");
                        score += 57;
                        break;
                    }
                    if (lines.get(i)[j].equals("}")) {
                        characters.add("}");
                        score += 1197;
                        break;
                    }
                    if (lines.get(i)[j].equals(">")) {
                        characters.add(">");
                        score += 25137;
                        break;
                    }
                }
            }


            System.out.println(score);
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
