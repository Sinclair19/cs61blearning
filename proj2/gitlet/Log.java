package gitlet;

import java.util.List;

public class Log {

    public static void printLog() {
        StringBuilder str = new StringBuilder();
        Commit commit = Method.getCurrentCommit();
        while (commit != null) {
            str.append("===\n");
            str.append(commit.getLogMessage());
            commit = Method.getCommit(commit.getFirstParent());
        }
        System.out.println(str);
    }

    public static void printGlobalLog() {
        List<String> commitList = Utils.plainFilenamesIn(Repository.COMMITS_DIR);
        if (commitList == null) {
            System.out.println();
            return;
        }
        for(String str : commitList) {
            printCommit(str);
        }
    }

    private static void printCommit(String id) {
        Commit commit = Method.getCommit(id);
        System.out.print("\n===\n");
        System.out.print(commit.getLogMessage());
    }

}
