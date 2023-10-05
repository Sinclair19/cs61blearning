package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    public static final File REFS_DIR = join(GITLET_DIR, "refs");

    public static final File COMMITS_DIR = join(REFS_DIR, "commits");

    public static final File HEADS_DIR = join(REFS_DIR, "heads");

    public static final File CURRENT_HEAD = join(GITLET_DIR, "HEAD");

    public static final File BRANCHES_DIR = join(REFS_DIR, "branches");

    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");

    public static final File STAGED_DIR = join(GITLET_DIR, "staged");


    /* TODO: fill in the rest of this class. */

    private static void createDir() {
        GITLET_DIR.mkdir();
        REFS_DIR.mkdir();
        COMMITS_DIR.mkdir();
        BRANCHES_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        STAGED_DIR.mkdir();
    }

    private static void createFile() {
        try {
            CURRENT_HEAD.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void initRepo() {
        if (Method.checkExist(GITLET_DIR)) {
            Method.exit("A Gitlet version-control system already exists in the current directory.");
        }
        createDir();
        createFile();
        Commit first = new Commit("initial commit");
        Branch master = new Branch("master");
        master.updateHEAD(first);
        Method.writeCurrentHEAD(master.returnHEAD());
        File commitDir = Method.returnIDDir(first.getID());
        writeContents(commitDir, first);

    }

    public static void add(String name) {
        File adding = join(CWD, name);
        Method.ExistOrExit(GITLET_DIR, "No Gitlet exists in the current directory.");
        Method.ExistOrExit(adding, "File does not exist.");
        Stage now = Stage.read(STAGED_DIR);
        now.add(adding);
        now.write();
    }

    public static void remove(String name) {
        File removing = join(CWD, name);
        Method.ExistOrExit(GITLET_DIR, "No Gitlet exists in the current directory.");

        Stage now = Stage.read(STAGED_DIR);
        if (!now.isStagedorTracked(removing)) {
            Method.exit("No reason to remove the file.");
        }
        now.remove(removing);
        now.write();
    }

    public static void commit(String message) {
        if (message.trim().isEmpty()) {
            Method.exit("Please enter a commit message.");
        }
        Stage staging = Stage.read(STAGED_DIR);
        if (staging.getAdding().isEmpty() && staging.getRemoving().isEmpty()) {
            Method.exit("No changes added to the commit.");
        }

        Branch current_branch = Method.getCurrentBranch();
        Commit parent_commit = current_branch.returnHEAD().getCommit();
        Stage now = Stage.read(STAGED_DIR);
        if (now.isEmpty()) {
            Method.exit("No changes added to the commit.");
        }
        Commit new_commit = new Commit(message, parent_commit, now);

        // update HEAD and tracking list
        current_branch.updateHEAD(new_commit);
        current_branch.updateTracked(new_commit);

        Stage new_stage = new Stage();

        new_commit.write();
        new_stage.write();
        current_branch.write();
    }

    public static void branch(String name) {
        File newBranchFile = join(BRANCHES_DIR, name);
        if (Method.checkExist(newBranchFile)) {
            Method.exit("A branch with that name already exists.");
        }

        Branch newBranch = new Branch(name);
        newBranch.updateHEAD(Method.getCurrentCommit());
        newBranch.write();

    }
}
