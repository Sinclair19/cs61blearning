package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gitlet.Utils.*;

public class Method {

    public static void exit(String word) {
        if(word != null) {
            System.out.println(word);
        }
        System.exit(0);
    }

    public static boolean checkExist(File f) {
        return f.exists();
    }

    public static void ExistOrExit(File f, String message) {
        if (!checkExist(f)) {
            exit(message);
        }
    }

    public static File returnDir(File pDir, String id) {
        return join(pDir, id);
    }

    public static File returnIDDir(String ID) {
        return join(Repository.COMMITS_DIR, ID);
    }

    public static String returnFileID(File file) {
        return Utils.sha1(file.getAbsolutePath(), Utils.readContentsAsString(file)); // TODO: fix path
    }

    public static HEAD readHEAD(File dir) {
        return readObject(dir, HEAD.class);
    }

    public static Map<String, String> getCurrentTracked() {
        Branch current = getCurrentBranch();
        return current.getTracked();
    }

    public static HEAD getCurrentHEAD() {
        return readHEAD(Repository.CURRENT_HEAD);
    }

    public static Branch getCurrentBranch() {
        HEAD CurrentHEAD = getCurrentHEAD();
        return CurrentHEAD.getBranch();
    }

    public static void writeCurrentHEAD(HEAD head) {
        writeObject(Repository.CURRENT_HEAD, head);
    }

    public static Commit getCurrentCommit() {
        return getCurrentHEAD().getCommit();
    }

    public static Commit getCommit(String ID) {
        ID = checkCommitID(ID); //convert shortid to long id;
        if (ID != null) {
            File dir = join(Repository.COMMITS_DIR, ID);
            return readObject(dir, Commit.class);
        }
        return null;
    }

    public static Branch getBranch(String name) {
        File dir = join(Repository.BRANCHES_DIR, name);
        if (!dir.exists()) {
            return null;
        }
        return Branch.read(dir);
    }

    private static String checkCommitID(String ID) {
        int length = ID.length();
        if (length != 40) {
            List<String> IDlist = plainFilenamesIn(Repository.COMMITS_DIR);
            if (IDlist != null) {
                for (String mID : IDlist) {
                    if (mID.substring(0, length - 1).equals(ID)) {
                        return mID;
                    }
                }
            }
            return null;
        }
        return ID;
    }




    /**
     * get a blob with its ID
     */
    public static Blob getBlob(String ID) {
        return Blob.read(join(Repository.OBJECTS_DIR, ID));
    }

    public static Stage getStaging() {
        return Stage.read(Repository.STAGED_DIR);
    }

    public static List<String> getCommitList() {
        return Utils.plainFilenamesIn(Repository.COMMITS_DIR);
    }

    public static List<String> getfileASList(File file) {
        List<String> all = Utils.plainFilenamesIn(file);
        if (all == null) {
            return new ArrayList<String>();
        }
        if (file.equals(Repository.CWD)) {
            all.remove(".gitlet");
        }
        return all;
    }
    /*
     * This method has a problem that it just check if there are Untracked files
     * between current staging area and current branch,
     * it won't do check if these files will really modified by function who call this
     * If a working file is untracked in the current branch and **would be overwritten by the checkout**
     * As checking this is quite complex, I will see if it's really needed
     */
    public static void checkUntracked() {
        if (! Status.getUntracked().isEmpty()) {
            Method.exit("There is an untracked file in the way; " +
                    "delete it, or add and commit it first.");
        }
    }

    public static void checkUncommitted() {
        if (getStaging().isEmpty()) {
            Method.exit("You have uncommitted changes.");
        }
    }

    public static void clean(File file, List<String> tracked) {
        List<String> files = plainFilenamesIn(file);
        if (files != null) {
            files.removeAll(tracked); //delete all files that are not tracked in tracked list
            for (String s : files) {
                join(file, s).delete();
            }
        }
    }

    public static void updateHEAD(Commit commit) {
        Branch cur = getCurrentBranch();
        cur.updateHEAD(commit);
        cur.updateTracked(commit);
        cur.write();
    }

    public static void stageAdd(File file) {
        Stage stage = getStaging();
        stage.add(file);
        stage.write();
    }

    public static void stageRemove(File file) {
        Stage stage = getStaging();
        stage.remove(file);
        stage.write();
    }

    public static void clearStage() {
        Stage stage = new Stage();
        stage.write();
    }
}
