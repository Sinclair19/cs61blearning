package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gitlet.Utils.join;

public class Checkout {
    public static void CheckOperands(String[] args) {
        if(args.length == 3) {
            if (args[1].equals("--")) {
                checkoutFile(args[2]); // checkout -- [file name]
            }
        } else if (args.length == 4) {
            if (args[2].equals("--")) {
                checkoutFileID(args[1], args[3]); //checkout [commit id] -- [file name]
            }
        } else if (args.length == 2) {
            checkoutBranch(args[1]); //checkout [branch name]
        } else {
            Method.exit("Incorrect operands");
        }
    }

    public static void checkoutFile(String filepath) {
        checkoutFileID("current", filepath);
    }

    public static void checkoutFileID(String ID, String filepath) {
        File file = join(Repository.CWD, filepath);
        Commit commit;
        if (ID.equals("current")) {
            commit = Method.getCurrentCommit();
        } else {
            commit = Method.getCommit(ID);
        }
        if (commit != null) {
            checkoutFileID(commit, file);
        }
        Method.exit("No such commit");
    }

    public static void checkoutFileID(Commit commit, File file) {
        File Blobfile = commit.getBlob(file);
        if (Blobfile == null) {
            Method.exit("File does not exist in that commit");
        }
        Blob stored = Blob.read(Blobfile);
        writeBlob(stored, file);

    }

    public static void writeBlob(Blob stored, File write) {
        byte[] bytes = stored.getContent();
        Utils.writeContents(write, (Object) bytes);
    }

    public static void checkoutBranch(String branchName){
        File checkBranch = join(Repository.BRANCHES_DIR, branchName);
        Method.ExistOrExit(checkBranch, "No such branch exists.");
        Branch newBranch = Branch.read(checkBranch);
        Branch oldBranch = Method.getCurrentBranch();
        if (newBranch.getName().equals(oldBranch.getName())) {
            Method.exit("No need to checkout the current branch.");
        }
        Method.checkUntracked(); //check if there are untracked files

        Map<String, String> newBranchTracked = newBranch.getTracked();
        List<String> trackedList = new ArrayList<>(newBranchTracked.keySet());
        Method.clean(Repository.CWD, trackedList);

        for (Map.Entry<String, String> entry: newBranchTracked.entrySet()) {
            File file = join(Repository.CWD, entry.getKey());
            Blob write = Method.getBlob(entry.getValue());
            writeBlob(write, file);
        }

        Stage stage = new Stage();
        stage.write(); //clear stage area

        HEAD newHEAD = newBranch.returnHEAD();
        Method.writeCurrentHEAD(newHEAD);
    }
}
