package gitlet;

public class Branch {

    private String name;

    public String HEAD;

    public Branch(String name, String HEAD) {
        checkExist();
        this.name = name;
        this.HEAD = HEAD;
    }

    private Boolean checkExist() {
        return false;
    }
}
