package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static gitlet.Utils.*;

public class Branch implements Serializable {

    private String name;

    private File head;

    private File BRANCHES_DIR = Repository.BRANCHES_DIR;

    private File THIS_HEAD_DIR;

    private File DIR;

    private Map<String, String> tracked;

    public Branch(String name) {
        this.checkExist();
        this.name = name;
        this.head = null;
        this.DIR = join(BRANCHES_DIR, name);
        this.THIS_HEAD_DIR = join(Repository.HEADS_DIR, this.name);
        this.tracked = new TreeMap<>();
    }

    public Branch(String name, HEAD HEAD) {
        this.checkExist();
        this.name = name;
        this.head = HEAD.getDIR();
        this.DIR = join(BRANCHES_DIR, name);
        this.tracked = new TreeMap<>();
    }

    public void updateHEAD(Commit commit) {
        HEAD newHEAD = HEAD.read(this.THIS_HEAD_DIR);
        newHEAD.updateHEAD(commit);
        this.head = newHEAD.getDIR();
        newHEAD.write();
    }

    public String getName() {
        return this.name;
    }

    public HEAD returnHEAD() {
        return HEAD.read(this.head);
    }

    public File getDIR() {
        return this.DIR;
    }

    public void updateTracked(Commit commit) {
        this.tracked.putAll(commit.getAdding());
        for (String key : commit.getRemoving()) {
            this.tracked.remove(key);
        }
    }

    public Map<String, String> getTracked() {
        return this.tracked;
    }

    public Boolean checkExist() {
        return DIR.exists();
    }

    public void write() {
        writeObject(DIR, this);
    }

    public Branch read(File file) {
        return readObject(file, Branch.class);
    }

}
