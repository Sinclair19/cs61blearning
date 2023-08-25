package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static gitlet.Utils.*;

public class Branch implements Serializable {

    private String name;

    private File HEAD;

    private File BRANCHES_DIR = Repository.BRANCHES_DIR;

    private File DIR;

    private Map<String, String> tracked;

    public Branch(String name) {
        this.checkExist();
        this.name = name;
        this.HEAD = null;
        this.DIR = join(BRANCHES_DIR, name);
        this.tracked = new TreeMap<>();
    }

    public Branch(String name, HEAD HEAD) {
        this.checkExist();
        this.name = name;
        this.HEAD = HEAD.getDIR();
        this.DIR = join(BRANCHES_DIR, name);
        this.tracked = new TreeMap<>();
    }

    public void updateHEAD(Commit commit) {
        HEAD newHEAD = new HEAD(this, commit);
        newHEAD.write();
        this.HEAD = newHEAD.getDIR();
    }

    public String getName() {
        return this.name;
    }

    public HEAD returnHEAD() {
        return Method.readHEAD(HEAD);
    }

    public File getDIR() {
        return this.DIR;
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
