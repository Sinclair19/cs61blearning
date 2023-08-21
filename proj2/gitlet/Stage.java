package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;

public class Stage implements Serializable {

    private Map<String, String> adding;

    private Set<String> removeing;

    private Map<String, String> tracked;

    private File DIR = Repository.STAGED_DIR;

    public Stage() {
        this.adding = new HashMap<>();
        this.removeing = new TreeSet<>();
        this.tracked = new TreeMap<>();
    }

    public void add(String filename) {
        File file = join(Repository.CWD, filename);
        Method.ExistOrExit(file, "File does not exist.");
        Blob fileBlob = new Blob(file);
        String fileSha1 = fileBlob.getID();
        if (isNEWorModified(filename, fileSha1)) {
            this.adding.put(filename,fileSha1);
            this.tracked.put(filename, fileSha1);
            fileBlob.write();
        }
    }

    public void addRemove(String filename) {
        this.removeing.add(filename);
        this.adding.remove(filename);
        this.tracked.remove(filename);
    }

    public boolean isNEWorModified(String filename, String fileSha1) {
        Branch current = Method.getCurrentBranch();
        Map<String, String> Branch_tracked = current.getTracked();
        if (!Branch_tracked.containsKey(fileSha1)) {
            return !this.tracked.containsKey(fileSha1);
        }
        return false;
    }

    public void write() {
        writeObject(DIR, this);
    }

    public void read(File file) {
       readObject(file, Stage.class);
    }
}
