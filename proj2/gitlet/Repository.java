package gitlet;

import java.io.File;
import java.io.IOException;

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

    public static final File HEAD = join(GITLET_DIR, "HEAD");

    public static final File BRANCHES_DIR = join(REFS_DIR, "branches");

    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");


    /* TODO: fill in the rest of this class. */

    private static void createDir() {
        GITLET_DIR.mkdir();
        REFS_DIR.mkdir();
        BRANCHES_DIR.mkdir();
        OBJECTS_DIR.mkdir();
    }

    private static void createFile() {
        try {
            HEAD.createNewFile();
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
        Commit first = new Commit("initial commit", null);
        Branch master = new Branch("master", "");


    }

}
