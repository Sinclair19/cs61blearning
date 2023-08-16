package gitlet;

import java.io.File;

public class Method {

    public static void exit(String word) {
        if(word != null) {
            System.out.println(word);
        }
        System.exit(0);
    }

    public static boolean checkExist(File f) {
        return f.exists();
    }
}
