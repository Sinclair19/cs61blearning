package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

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
        File commitDir = Method.returnIDDir(first.getID());
        writeContents(commitDir, first);

    }

    public static void add(String name) {
        File adding = join(CWD, name);
        if (! Method.checkExist(GITLET_DIR)) {
            Method.exit("No Gitlet exists in the current directory.");
        }
        if(!Method.checkExist(adding)) {
            Method.exit("This file does not exist.");
        }

    }

}
