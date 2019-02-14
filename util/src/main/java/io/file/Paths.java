package io.file;

import java.io.IOException;
import java.nio.file.Files;
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

    public static void createPathIfNotExist(String path) {
        Path p1 = java.nio.file.Paths.get(path);
        if (Files.notExists(p1)) {
            try {
                Files.createDirectories(p1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
