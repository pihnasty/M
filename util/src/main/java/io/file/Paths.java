package io.file;

import java.nio.file.Path;

public class Paths {

    public static String getPathToDirectory (String stringFullPath) {
        Path fullPath = java.nio.file.Paths.get(stringFullPath);
        return  fullPath.getParent().toString().replace("\\","//");
    }

    public  static  String getShortFileName (String stringFullPath) {
        Path fullPath = java.nio.file.Paths.get(stringFullPath);
        return fullPath.getFileName().toString();
    }

}
