package gitlet;

// TODO: any imports you need here

import java.text.SimpleDateFormat;
import java.util.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit {
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
    private HashMap<String, String> blobs;

    private String ID;

    /* TODO: fill in the rest of this class. */

    public Commit(String message, Commit p) {
        if (message == null) {
            Method.exit("Please enter a commit message.");
        }
        this.message = message;
        this.parent.add(p.ID);
        if (this.parent == null) {
            setInitTime();
        } else {
            setTime();
        }
        updateID();

    }

    private void updateID() {
        this.ID = Utils.sha1(this.message, time.toString(), parent.toArray(), blobs.toString());
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

    public List<String> getParentID() {
        return this.parent;
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

    public Map<String, String> getBlobs () {
        return this.blobs;
    }
}
