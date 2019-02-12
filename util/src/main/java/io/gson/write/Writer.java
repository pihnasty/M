package io.gson.write;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Writer {


    public static void saveToGsonFile(String path, String fileName, Object object) {
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
