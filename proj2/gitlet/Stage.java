package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.join;

public class Stage implements Serializable {

    private Map<String, String> adding;

    private Set<String> removeing;

    private Map<String, String> tracked;

    public Stage() {
        this.adding = new HashMap<>();
        this.removeing = new HashSet<>();
        this.tracked = new TreeMap<>();
    }

    public void add(String filename) {
        File file = join(Repository.CWD, filename);
        String fileSha1 = Utils.sha1(file);
        if (isNEWorModified(filename, fileSha1)) {
            this.adding.put(filename,fileSha1);
        }
    }

    public void remove(String filename) {
        this.removeing.add(filename);
    }

    public boolean isNEWorModified(String filename, String fileSha1) {
        Branch current = Method.getCurrentBranch();
        Map<String, String> Branch_tracked = current.getTracked();
        return !Branch_tracked.containsValue(fileSha1) && !this.tracked.containsValue(fileSha1);
    }
}
