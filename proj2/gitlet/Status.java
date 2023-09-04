package gitlet;

import java.util.Comparator;
import java.util.List;

public class Status {

    public static void printStatus() {
        Stage current = Method.getStaging();
        printList("=== Branches ===", getBranches());
        printList("=== Staged Files ===", sortList(current.getAddingAslist()));
        printList("=== Removed Files ===", sortList(current.getRemovingAslist()));
        printList("=== Modifications Not Staged For Commit ===", sortList(getNotStages()));
        printList("=== Untracked Files ===", sortList(getUntracked()));
    }

    public static List<String> sortList(List<String> list) {
        list.sort(null);
        return list;
    }

    public static void printList(String message, List<String> list) {
        System.out.println(message);
        if (list != null) {
            for (String s : list) {
                System.out.println(s);
            }
            System.out.println();
        }
    }

    public static List<String> getBranches() {
        List<String> branchlist = Utils.plainFilenamesIn(Repository.BRANCHES_DIR);
        if (branchlist == null) {
            Method.exit("No branch existed");
        }
        String currentBranch = Method.getCurrentBranch().getName();
        branchlist.remove(currentBranch);
        branchlist.sort(null);  // because the asterisk not count
        branchlist.add(0,"*" + currentBranch);
        return branchlist;
    }

    public static List<String> getNotStages() {
        return null;
    }

    public static List<String> getUntracked() {
        return null;
    }

}
