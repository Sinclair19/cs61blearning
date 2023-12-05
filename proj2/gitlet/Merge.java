package gitlet;

import java.io.File;
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
    private static void preMerge(Branch current, Branch given) {
        Commit currentHEAD = current.returnCommit();
        Commit givenHEAD = given.returnCommit();

        Commit splitPoint = getSplitPoint(currentHEAD, givenHEAD);
        if (splitPoint.getID().equals(currentHEAD.getID())) {
            fastForward(given.getName());
        }
        if (splitPoint.getID().equals(givenHEAD.getID())) {
            Method.exit("Given branch is an ancestor of the current branch.");
        }
        String message = "Merged " + given.getName() + " into " + current.getName() + '.';
        Merge(currentHEAD, givenHEAD, splitPoint, message);
    }

    private static void Merge(Commit cHEAD, Commit gHEAD, Commit sp, String msg) {
        Map<String, String> cTracked = cHEAD.getTracked();
        Map<String, String> gTracked = gHEAD.getTracked();
        Map<String, String> sTracked = sp.getTracked();
        //get all files in CWD
        List<String> files = Method.getfileASList(Repository.CWD);


        for (String filename : files) {
            String cHash = cTracked.get(filename);
            String gHash = gTracked.get(filename);
            String sHash = sTracked.get(filename);

            File file = Utils.join(Repository.CWD, filename);

            // 4. files that were not present at the split point and are present only in the current branch
            if (!compare(cHash, null) && compare(gHash, null) && compare(sHash, null)) {
                continue;
            }
            // 3. files that have been modified in both the current and given branch in the same way
            if (compare(cHash, gHash)) {
                continue;
            }
            // 2. files that have been modified in the current branch but not in the given branch since the split point
            if (compare(gHash, sHash)) {
                continue;
            }
            // 5.  files that were not present at the split point and are present only in the given branch
            if (compare(cHash, null) && !compare(gHash, null) && compare(sHash, null)) {
                Checkout.checkoutFileID(gHEAD.getID(), filename); // checkout a file with commit ID
                Method.stageAdd(file);
                continue;
            }
            // 1. files that have been modified in the given branch since the split point, but not modified in the current branch since the split point
            if (compare(cHash, sHash) && !compare(gHash, sHash)) {
                writeFile(file, gHash);
                Method.stageAdd(file);
                continue;
            }
            // 6. files present at the split point, unmodified in the current branch, and absent in the given branch
            if (compare(cHash, null) && !compare(gHash, null) && !compare(sHash, null)) {
                Method.stageRemove(file);
                continue;
            }
            // 7.  files present at the split point, unmodified in the given branch, and absent in the current branch
            if (!compare(cHash, null) && !compare(gHash, null) && compare(gHash, sHash)) {
                continue;
            }
            if (sTracked.containsKey(filename) && !compare(cHash, gHash) ||
                    !sTracked.containsKey(filename) && !compare(cHash, gHash)) {
                conflict(file, cHash, gHash);
            }
        }
        List<Commit> parents = new ArrayList<>(Arrays.asList(cHEAD, gHEAD));
        Repository.commit(msg, parents);
    }

    private static void conflict(File file, String cHash, String gHash) {
        if (!compare(cHash, null) && !compare(gHash, null)) {
            String cur = Method.getBlob(cHash).getContent();
            String tar = Method.getBlob(gHash).getContent();
            String str = "<<<<<<< HEAD\n" + cur + "=======\n" + tar + ">>>>>>>\n";
            Utils.writeContents(file, str);
            System.out.println("Encountered a merge conflict.");
            Method.stageAdd(file);
        }
    }

    // return true if two string are equal and not null
    private static boolean compare(String s1, String s2) {
        if(s1 != null && s2 != null) {
            return s1.equals(s2);
        } else return s1 == null && s2 == null;
    }

    /**
     * write a blob to a file
     */
    private static void writeFile (File file, String BlobHash) {
        Blob blob = Method.getBlob(BlobHash);
        Utils.writeContents(file, blob.getContent());
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
