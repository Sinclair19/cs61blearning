package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

public class HEAD implements Serializable {

    private File HEAD_DIR = Repository.HEADS_DIR;

    private File DIR;

    private File commit;


    private final File branch;

    public HEAD (Branch branch, Commit commit) {
        this.DIR = join(HEAD_DIR, branch.getName());
        this.branch = branch.getDIR();
        this.commit = commit.getDIR();
    }

    public void updateHEAD(Commit commit) {
        this.commit = commit.getDIR();
    }

    public Branch getBranch () {
        return readObject(this.branch, Branch.class);
    }

    public Serializable getCommit () {
        return readObject(this.commit, Commit.class);
    }

    public File getDIR () {
        return DIR;
    }

    public void write() {
        writeObject(DIR, this);
    }

    public HEAD read(File file) {
        return readObject(file, HEAD.class);
    }

}
