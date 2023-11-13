package gitlet;

import java.util.*;

public class Merge {

    public static void mergeCheck(String branchName) {
        Method.checkUncommitted(); // check if there are uncommitted files
        Method.checkUntracked(); // check if there are untracked files

        Branch given = Method.getBranch(branchName);
        if (given == null) {
            Method.exit("A branch with that name does not exist.");
        }

        Branch current = Method.getCurrentBranch();
        if (current.getName().equals(given.getName())) {
            Method.exit("Cannot merge a branch with itself.");
        }

        preMerge(current, given);
    }
    public static void preMerge(Branch current, Branch given) {
        Commit currentHEAD = current.returnCommit();
        Commit givenHEAD = given.returnCommit();

        Commit splitPoint = getSplitPoint(currentHEAD, givenHEAD);
        if (splitPoint.getID().equals(currentHEAD.getID())) {
            fastForward(given.getName());
        }
        if (splitPoint.getID().equals(givenHEAD.getID())) {
            Method.exit("Given branch is an ancestor of the current branch.");
        }

    }

    private static void fastForward (String branchName) {
        Checkout.checkoutBranch(branchName);
        Method.exit("Current branch fast-forwarded.");
    }

    /**
     * Get the Split point by compare it's create date
     * Return the latest split point
     */
    private static Commit getSplitPoint(Commit currentHEAD, Commit givenHEAD) {

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
