package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;

    private Date time;

    private List<String> parent;

    // filename, ID
    private Map<String, String> adding;

    private Set<String> removing;

    private Map<String, String> tracked;

    private String ID;

    private final File COMMITS_DIR = Repository.COMMITS_DIR;

    private final File DIR;

    /* TODO: fill in the rest of this class. */




    public Commit(String message) {
        checkMessage(message);
        this.message = message;
        createMap();
        this.parent = null;
        setInitTime();
        updateID();
        this.DIR = join(COMMITS_DIR, this.ID);
    }

    public Commit(String message, Commit p, Stage Staging) {
        checkMessage(message);
        this.message = message;
        createMap();
        this.parent.add(p.ID);
        setTime();
        configMap(Staging);
        updateID();
        this.DIR = join(COMMITS_DIR, this.ID);

    }

    private void createMap () {
        this.adding = new TreeMap<>();
        this.removing = new TreeSet<>();
        this.tracked = new TreeMap<>();
    }

    private void configMap (Stage staging) {
        this.adding = staging.getAdding();
        this.removing = staging.getRemoving();
    }

    private void checkMessage(String message) {
        if (message == null) {
            Method.exit("Please enter a commit message.");
        }
    }

    private void updateID() {
        this.ID = Utils.sha1(this);
    }

    private void setInitTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(1970, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        this.time = calendar.getTime();
    }

    private void setTime() {
        Calendar c = Calendar.getInstance();
        this.time = c.getTime();
    }

    public void updateTracked(Map<String, String> map) {
        this.tracked = map;
    }

    public Map<String, String> getTracked() {
        return this.tracked;
    }

    public List<String> getParentList() {
        return this.parent;
    }

    public String getFirstParent () {
        return this.parent.get(0);
    }

    public String getMessage() {
        return this.message;
    }

    public Date getTime() {
        return this.time;
    }

    public String getStrTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss z', 'EEEE, d MMMM yyyy");
        return formatter.format(this.time);
    }

    public String getID() {
        return this.ID;
    }

    public String getLogMessage () {
        return "commit " + getID() + "\n" +
                "Date:" + getStrTime() + "\n" +
                getMessage() + "\n";
    }

    public File getBlob(File file) { //get blob file with its file
        String filepath = file.getAbsolutePath();
        if (this.tracked.containsKey(filepath)) {
            return join(Repository.OBJECTS_DIR, this.tracked.get(filepath));
        } else {
            return null;
        }
    }

    public File getDIR() {
        return this.DIR;
    }

    public Map<String, String> getAdding () {
        return this.adding;
    }

    public Set<String> getRemoving () {
        return this.removing;
    }

    public void write() {
        writeObject(DIR, this);
    }

    public Commit read(File file) {
        return readObject(file, Commit.class);
    }
}
