package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gitlet.Utils.*;

public class Status {

    public static void printStatus() {
        Stage stage = Method.getStaging();
        Branch branch = Method.getCurrentBranch();
        printList("=== Branches ===", getBranches());
        printList("=== Staged Files ===", sortList(stage.getAddingAslist()));
        printList("=== Removed Files ===", sortList(stage.getRemovingAslist()));
        printList("=== Modifications Not Staged For Commit ===", getNotStages(stage, branch));
        printList("=== Untracked Files ===", sortList(getUntracked(stage, branch)));
    }

    private static List<String> sortList(List<String> list) {
        list.sort(null);
        return list;
    }

    private static void printList(String message, List<String> list) {
        System.out.println(message);
        if (list != null) {
            for (String s : list) {
                System.out.println(s);
            }
            System.out.println();
        }
    }

    private static List<String> getBranches() {
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


    private static List<String> getNotStages(Stage stage, Branch branch) {
        List<String> modified = new ArrayList<>();
        List<String> deleted = new ArrayList<>();
        List<String> returnList = new ArrayList<>();
        List<String> all = Method.getfileASList(Repository.CWD);
        //List<String> allblobs = Method.getfileASList(Repository.OBJECTS_DIR);
        for(String s: all) {
            File file = join(Repository.CWD, s);
            String ID = Method.returnFileID(file);
            boolean isTracked = stage.isTracked(file) || branch.isTracked(file);
            boolean isModified = stage.isNEWorModified(file, ID);
            boolean exist = file.exists();
            if (isTracked && isModified) {
                modified.add(s + "(modified)");
            }
            if (isTracked && !exist) {
                deleted.add(s + "(deleted)");
            }
        }
        returnList.addAll(sortList(modified));
        returnList.addAll(sortList(deleted));
        return returnList;
    }

    private static List<String> getUntracked(Stage stage, Branch branch) {
        List<String> all = Method.getfileASList(Repository.CWD);
        Map<String, String> branchTracked = branch.getTracked();
        Map<String, String> stageTracked = stage.getTracked();
        List<String> returnList = new ArrayList<>();
        for(String s : all) {
            if (!branchTracked.containsKey(s) || !stageTracked.containsKey(s)) {
                returnList.add(s);
            }
        }
        return returnList;
    }

    // get current Untracked;
    public static List<String> getUntracked() {
        Stage stage = Method.getStaging();
        Branch branch = Method.getCurrentBranch();
        return getUntracked(stage, branch);
    }
}
