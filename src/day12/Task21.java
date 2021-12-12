package day12;

/*
With your submarine's subterranean subsystems subsisting suboptimally, the only way you're getting out of this cave anytime soon is by finding a path yourself. Not just a path - the only way to know if you've found the best path is to find all of them.

Fortunately, the sensors are still mostly working, and so you build a rough map of the remaining caves (your puzzle input). For example:

start-A
start-b
A-c
A-b
b-d
A-end
b-end

This is a list of how all of the caves are connected. You start in the cave named start, and your destination is the cave named end. An entry like b-d means that cave b is connected to cave d - that is, you can move between them.

So, the above cave system looks roughly like this:

    start
    /   \
c--A-----b--d
    \   /
     end

Your goal is to find the number of distinct paths that start at start, end at end, and don't visit small caves more than once. There are two types of caves: big caves (written in uppercase, like A) and small caves (written in lowercase, like b). It would be a waste of time to visit any small cave more than once, but big caves are large enough that it might be worth visiting them multiple times. So, all paths you find should visit small caves at most once, and can visit big caves any number of times.

Given these rules, there are 10 paths through this example cave system:

start,A,b,A,c,A,end
start,A,b,A,end
start,A,b,end
start,A,c,A,b,A,end
start,A,c,A,b,end
start,A,c,A,end
start,A,end
start,b,A,c,A,end
start,b,A,end
start,b,end

(Each line in the above list corresponds to a single path; the caves visited by that path are listed in the order they are visited and separated by commas.)

Note that in this cave system, cave d is never visited by any path: to do so, cave b would need to be visited twice (once on the way to cave d and a second time when returning from cave d), and since cave b is small, this is not allowed.

Here is a slightly larger example:

dc-end
HN-start
start-kj
dc-start
dc-HN
LN-dc
HN-end
kj-sa
kj-HN
kj-dc

The 19 paths through it are as follows:

start,HN,dc,HN,end
start,HN,dc,HN,kj,HN,end
start,HN,dc,end
start,HN,dc,kj,HN,end
start,HN,end
start,HN,kj,HN,dc,HN,end
start,HN,kj,HN,dc,end
start,HN,kj,HN,end
start,HN,kj,dc,HN,end
start,HN,kj,dc,end
start,dc,HN,end
start,dc,HN,kj,HN,end
start,dc,end
start,dc,kj,HN,end
start,kj,HN,dc,HN,end
start,kj,HN,dc,end
start,kj,HN,end
start,kj,dc,HN,end
start,kj,dc,end

Finally, this even larger example has 226 paths through it:

fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW

How many paths through this cave system are there that visit small caves at most once?
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Task21 {

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
            while (!equalsSet){
                for (String path : allPaths) {
                    String[] caves = path.split(",");
                    String lastCave = caves[caves.length-1];
                    if (!lastCave.equals("end")) {
                        for (int j = 0; j < pairs.size(); j++) {
                            StringBuilder stringBuilder = new StringBuilder(path);
                            if (pairs.get(j)[0].equals(lastCave) && !(path.contains("," + pairs.get(j)[1].toLowerCase() + ","))) {
                                stringBuilder.append(",").append(pairs.get(j)[1]);
                                tempAllPaths.add(stringBuilder.toString());
                            }
                            if (pairs.get(j)[1].equals(lastCave) && !(path.contains("," + pairs.get(j)[0].toLowerCase() + ","))) {
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
