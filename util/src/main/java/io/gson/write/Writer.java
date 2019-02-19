package io.gson.write;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Writer {


    public static void saveToGsonFile(String path, String fileName, Object object) {
        Path p1 = Paths.get(path);
        if (Files.notExists(p1)) {
            try {
                Files.createDirectories(p1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String fullName = path + "\\" + fileName;
        saveToGsonFile(fullName, object);
    }

    public static void saveToGsonFile(String fullName, Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String stringJson = gson.toJson(object);
        try (FileOutputStream fileOutputStream = new FileOutputStream(fullName, false);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
            bufferedWriter.append(stringJson);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
