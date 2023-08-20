package gitlet;

import java.io.File;

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

    public static File returnDir(File pDir, String id) {
        return join(pDir, id);
    }

    public static File returnIDDir(String ID) {
        return join(Repository.COMMITS_DIR, ID);
    }

    public static HEAD readHEAD(File dir) {
        return readObject(dir, HEAD.class);
    }

    public static HEAD getCurrentHEAD() {
        return readHEAD(Repository.CURRENT_HEAD);
    }

    public static Branch getCurrentBranch() {
        HEAD CurrentHEAD = getCurrentHEAD();
        return CurrentHEAD.getBranch();
    }
}
