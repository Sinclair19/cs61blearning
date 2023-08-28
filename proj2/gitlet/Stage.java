package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;

public class Stage implements Serializable {

    private Map<String, String> adding;

    private Set<String> removing;

    private Map<String, String> tracked;

    private File DIR = Repository.STAGED_DIR;

    public Stage() {
        this.adding = new HashMap<>();
        this.removing = new TreeSet<>();
        this.tracked = new TreeMap<>();
    }

    public void add(File file) {
        //File file = join(Repository.CWD, filename);
        String filepath = file.getAbsolutePath();
        Method.ExistOrExit(file, "File does not exist.");
        Blob fileBlob = new Blob(file);
        String fileSha1 = fileBlob.getID();
        if (isNEWorModified(file, fileSha1)) {
            this.adding.put(filepath, fileSha1);
            this.tracked.put(filepath, fileSha1);
            fileBlob.write();
        }
    }

    public void remove(File file) {
        String filepath = file.getAbsolutePath();
        this.tracked.remove(filepath);
        this.removing.add(filepath);
        // remove key, doesn't matter what value it is
        this.adding.remove(filepath);
        file.delete();
    }

    public boolean isNEWorModified(File file, String fileSha1) {
        Map<String, String> Branch_tracked = Method.getCurrentTracked();
        if (!Branch_tracked.containsKey(fileSha1)) {
            return !this.tracked.containsKey(fileSha1);
        }
        return false;
    }

    public boolean isEmpty() {
        return this.adding.isEmpty() && this.removing.isEmpty();
    }

    public boolean isStagedorTracked(File file) {
        String filepath = file.getAbsolutePath();
        if (this.tracked.containsKey(filepath)) {
            return true;
        }
        if (Method.getCurrentTracked().containsKey(filepath)) {
            return true;
        }
        return false;
    }

    public Map<String, String> getAdding () {
        return this.adding;
    }

    public Set<String> getRemoving () {
        return this.removing;
    }

    public Map<String, String> getTracked() {
        return this.tracked;
    }

    public void write() {
        writeObject(DIR, this);
    }

    public static Stage read(File file) {
       return readObject(file, Stage.class);
    }
}
