package gitlet;

import java.io.File;

import static gitlet.Utils.join;

public class Checkout {
    public void CheckOperands(String[] args) {
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

    public void checkoutFile(String filepath) {
        checkoutFileID("current", filepath);
    }

    public void checkoutFileID(String ID, String filepath) {
        File file = join(Repository.CWD, filepath);
        Commit commit;
        if (ID.equals("current")) {
            commit = Method.getCurrentCommit();
        } else {
            commit = Method.getCommit(ID);
        }
        if (commit == null) {
            Method.exit("No such commit");
        }
        checkoutFileID(commit, file);
    }

    public void checkoutFileID(Commit commit, File file) {
        File Blobfile = commit.getBlob(file);
        if (Blobfile == null) {
            Method.exit("File does not exist in that commit");
        }
        Blob stored = Blob.read(Blobfile);
        writeBlob(stored, file);

    }

    public void writeBlob(Blob stored, File write) {
        byte[] bytes = stored.getContent();
        Utils.writeContents(write, (Object) bytes);
    }

    public void checkoutBranch(String branchName){
        File checkBranch = join(Repository.BRANCHES_DIR, branchName);
        Method.ExistOrExit(checkBranch, "No such branch exists.");
        Branch branch = Branch.read(checkBranch);
        Branch current = Method.getCurrentBranch();
        if (branch.getName().equals(current.getName())) {
            Method.exit("No need to checkout the current branch.");
        }
        Method.checkUntracked(); //check if there are untracked files

        Method.clean(Repository.CWD);
        // TODO: branch switch and restore files
    }
}
