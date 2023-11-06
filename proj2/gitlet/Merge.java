package gitlet;

import java.util.*;

public class Merge {

    /**
     * Get the Split point by compare it's create date
     * Return the latest split point
     */
    private static Commit getSplitPoint(Branch current, Branch given) {
        Commit currentHEAD = current.returnHEAD().getCommit();
        Commit givenHEAD = given.returnHEAD().getCommit();

        Set<String> CurrentParents = getAllParents(currentHEAD);

        Commit splitPoint = searchSplitPoint(givenHEAD, CurrentParents);
        if (splitPoint == null) {
            Method.exit("No Split Point was founded");
        }
        return splitPoint;
    }

    /**
     * Use dfs to get all parents
     */
    private static Set<String> getAllParents(Commit commit) {
        if (commit == null) {
            return null;
        }
        Set<String> res = new HashSet<>();
        res.add(commit.getID());
        for (String ID: commit.getParentList()) {
            Set<String> temp = getAllParents(Method.getCommit(ID));
            if (temp != null) {
                res.addAll(temp);
            }
        }
        return res;
    }

    /**
     * Use bfs to find out latest common ancestor(Aka split point)
     */
    private static Commit searchSplitPoint(Commit commit, Set<String> parents) {
        Queue<String> queue= new LinkedList<>();
        queue.add(commit.getID());
        while (!queue.isEmpty()) {
            String element = queue.poll();
            if (parents.contains(element)) {
                return Method.getCommit(element);
            }
            for (String ID: commit.getParentList()) {
                if (!queue.contains(ID)) {
                    queue.add(ID);
                }
            }
        }
        return null;
    }


}
