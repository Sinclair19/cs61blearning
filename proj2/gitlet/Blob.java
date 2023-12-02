package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

public class Blob implements Serializable {
    private final String filepath;
    private String content;

    private final String ID;

    private File OBJECT_DIR = Repository.OBJECTS_DIR;

    private final File DIR;

    public Blob(File file) {
        this.filepath = file.getAbsolutePath();
        this.content = Utils.readContentsAsString(file);
        this.ID = Utils.sha1(filepath, content);
        this.DIR = join(OBJECT_DIR, ID);
    }

    public String getID () {
        return this.ID;
    }

    public File getDIR() {
        return this.DIR;
    }

    public String getFilepath(){
        return this.filepath;
    }

    public String getContent() { return this.content; }

    public void write() {
        Utils.writeObject(DIR, this);
    }

    public static Blob read(File file) {
        return Utils.readObject(file, Blob.class);
    }
}
