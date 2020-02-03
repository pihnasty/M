package io.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public static boolean deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] children =directory.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }
        return directory.delete();
    }

    public static boolean  deleteDirectory(String pathS) {
        Path path = java.nio.file.Paths.get(pathS);
        if (!Files.notExists(path)) {
            try {
                Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static String lastSortedFileNameFromDirectory(String path, String containExpression) {
        String firstFileName ="";

        Path p1 = java.nio.file.Paths.get(path);
        if (Files.notExists(p1)) {
            return "";
        }

        try {
            List<String> fileNames = Files.walk(p1).filter(Files::isRegularFile).map(p-> p.toFile().getName())
                 .filter(fileName->fileName.contains(containExpression)).sorted().collect(Collectors.toList());
            if(!fileNames.isEmpty()) {
                firstFileName = fileNames.get(fileNames.size()-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
      return firstFileName;
    }

    public static List<String> sortedFileNameFromDirectory(String path, String containExpression) {
        List<String> fileNames = new ArrayList<>();

        Path p1 = java.nio.file.Paths.get(path);
        if (Files.notExists(p1)) {
            return null;
        }

        try {
            fileNames = Files.walk(p1).filter(Files::isRegularFile).map(p-> p.toFile().getName())
                .filter(fileName->fileName.contains(containExpression)).sorted().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileNames;
    }

}
