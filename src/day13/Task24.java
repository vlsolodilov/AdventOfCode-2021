package day13;

/*
Finish folding the transparent paper according to the instructions. The manual says the code is always eight capital letters.

What code do you use to activate the infrared thermal imaging camera system?
 */

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task24 {

    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            List<Pair<Integer,Integer>> dots = new ArrayList<>();
            scanner = new Scanner(new File(".\\src\\day13\\day13input_dots.txt"));
            while(scanner.hasNextLine()){
                String[] str = scanner.nextLine().split(",");
                dots.add(new Pair<>(Integer.parseInt(str[0]), Integer.parseInt(str[1])));
            }
            List<Pair<String,Integer>> folds = new ArrayList<>();
            scanner = new Scanner(new File(".\\src\\day13\\day13input_fold.txt"));
            while(scanner.hasNextLine()){
                String[] str = scanner.nextLine().split("=");
                folds.add(new Pair<>(str[0].replaceAll("fold along ",""), Integer.parseInt(str[1])));
            }

            int maxX = 0;
            int maxY = 0;
            for (int i = 0; i < dots.size(); i++) {
                if (dots.get(i).getKey() > maxX)
                    maxX = dots.get(i).getKey();
                if (dots.get(i).getValue() > maxY)
                    maxY = dots.get(i).getValue();
            }

            int[][] paper = new int[maxY+2][maxX+1];
            for (int i = 0; i < dots.size(); i++) {
                paper[dots.get(i).getValue()][dots.get(i).getKey()] = 1;
            }

            int[][] newPaper = new int[0][0];
            for (int k = 0; k < folds.size(); k++) {

                if (folds.get(k).getKey().equals("x")) {
                    newPaper = new int[paper.length][folds.get(k).getValue()];
                    for (int i = 0; i < newPaper.length; i++) {
                        for (int j = 0; j < newPaper[0].length; j++) {
                            newPaper[i][j] = paper[i][paper[0].length - 1 - j] + paper[i][j];
                        }
                    }
                }

                if (folds.get(k).getKey().equals("y")) {
                    newPaper = new int[folds.get(k).getValue()][paper[0].length];
                    for (int i = 0; i < newPaper.length; i++) {
                        for (int j = 0; j < newPaper[0].length; j++) {
                            newPaper[i][j] = paper[paper.length - 1 - i][j] + paper[i][j];
                        }
                    }
                }
                paper = newPaper;
            }

            for (int i = 0; i < newPaper.length; i++) {
                for (int j = 0; j < newPaper[0].length; j++) {
                    if (newPaper[i][j] > 0)
                        System.out.print("#");
                    else
                        System.out.print(" ");
                }
                System.out.println();
            }

            System.out.println();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
