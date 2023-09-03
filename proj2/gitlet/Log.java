package gitlet;

import java.util.ArrayList;
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
        List<String> commitList = Method.getCommitList();
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

    public static void findMessage(String message) {
        List<String> commitList = Method.getCommitList();
        StringBuilder str = new StringBuilder();
        if (commitList != null) {
            for (String id : commitList) {
                Commit commit = Method.getCommit(id);
                if (commit.getMessage().equals(message)) {
                    str.append(id).append("\n");
                }
            }
        }
        if (!(str.length() == 0)) {
            Method.exit(str.toString());
        }
        Method.exit("Found no commit with that message.");
    }

}
