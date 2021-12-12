package day12;

/*
After reviewing the available paths, you realize you might have time to visit a single small cave twice. Specifically, big caves can be visited any number of times, a single small cave can be visited at most twice, and the remaining small caves can be visited at most once. However, the caves named start and end can only be visited exactly once each: once you leave the start cave, you may not return to it, and once you reach the end cave, the path must end immediately.

Now, the 36 possible paths through the first example above are:

start,A,b,A,b,A,c,A,end
start,A,b,A,b,A,end
start,A,b,A,b,end
start,A,b,A,c,A,b,A,end
start,A,b,A,c,A,b,end
start,A,b,A,c,A,c,A,end
start,A,b,A,c,A,end
start,A,b,A,end
start,A,b,d,b,A,c,A,end
start,A,b,d,b,A,end
start,A,b,d,b,end
start,A,b,end
start,A,c,A,b,A,b,A,end
start,A,c,A,b,A,b,end
start,A,c,A,b,A,c,A,end
start,A,c,A,b,A,end
start,A,c,A,b,d,b,A,end
start,A,c,A,b,d,b,end
start,A,c,A,b,end
start,A,c,A,c,A,b,A,end
start,A,c,A,c,A,b,end
start,A,c,A,c,A,end
start,A,c,A,end
start,A,end
start,b,A,b,A,c,A,end
start,b,A,b,A,end
start,b,A,b,end
start,b,A,c,A,b,A,end
start,b,A,c,A,b,end
start,b,A,c,A,c,A,end
start,b,A,c,A,end
start,b,A,end
start,b,d,b,A,c,A,end
start,b,d,b,A,end
start,b,d,b,end
start,b,end

The slightly larger example above now has 103 paths through it, and the even larger example now has 3509 paths through it.

Given these new rules, how many paths through this cave system are there?
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Task22 {

    public static void main(String[] args) {
        Scanner scanner = null;
        List<String[]> pairs = new ArrayList<>();
        try {
            scanner = new Scanner(new File(".\\src\\day12\\day12input.txt"));
            while(scanner.hasNextLine()){
                String[] str = scanner.nextLine().split("-");
                pairs.add(str);
            }

            Set<String> allPaths = new HashSet<>();
            List<String[]> tempPairs = new ArrayList<>(pairs);
            for (int i = 0; i < pairs.size(); i++) {
                StringBuilder stringBuilder = new StringBuilder();
                if (pairs.get(i)[0].equals("start")) {
                    stringBuilder.append(pairs.get(i)[0]).append(",").append(pairs.get(i)[1]);
                    allPaths.add(stringBuilder.toString());
                    tempPairs.remove(pairs.get(i));
                }
                if (pairs.get(i)[1].equals("start")) {
                    stringBuilder.append(pairs.get(i)[1]).append(",").append(pairs.get(i)[0]);
                    allPaths.add(stringBuilder.toString());
                    tempPairs.remove(pairs.get(i));
                }
            }

            pairs = new ArrayList<>(tempPairs);
            Set<String> tempAllPaths = new HashSet<>(allPaths);
            boolean equalsSet = false;
            int penalty = 0;
            while (!equalsSet){
                for (String path : allPaths) {
                    String[] caves = path.split(",");
                    String lastCave = caves[caves.length-1];
                    penalty = 0;
                    for (int i = 0; i < caves.length - 1; i++) {
                        for (int j = i + 1; j < caves.length; j++) {
                            if (caves[i].equals(caves[i].toLowerCase()) && caves[i].equals(caves[j]))
                                penalty++;
                        }
                    }

                    if (!lastCave.equals("end")) {
                        for (int j = 0; j < pairs.size(); j++) {
                            StringBuilder stringBuilder = new StringBuilder(path);
                            if (pairs.get(j)[0].equals(lastCave) && penalty < 2) {
                                stringBuilder.append(",").append(pairs.get(j)[1]);
                                tempAllPaths.add(stringBuilder.toString());
                            }
                            if (pairs.get(j)[1].equals(lastCave) && penalty < 2) {
                                stringBuilder.append(",").append(pairs.get(j)[0]);
                                tempAllPaths.add(stringBuilder.toString());
                            }
                        }
                    }
                }

                if (allPaths.size() != tempAllPaths.size())
                    allPaths = new HashSet<>(tempAllPaths);
                else
                    equalsSet = true;
            }
            int pathCount = 0;

            for (String path : allPaths) {
                String[] caves = path.split(",");
                String lastCave = caves[caves.length-1];
                if (lastCave.equals("end")) {
                   pathCount++;
                }
            }

            System.out.println(pathCount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
