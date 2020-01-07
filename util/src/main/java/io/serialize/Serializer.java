package io.serialize;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Serializer {

    public static void serialize(Object serializeObject, String path, String fileName) {
        Path p1 = Paths.get(path);
        if (Files.notExists(p1)) {
            try {
                Files.createDirectories(p1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path+"//"+fileName))) {
            objectOutputStream.writeObject(serializeObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deserialize(String path, String fileName) {
        Object deserializeObject = new Object();
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path+"//"+fileName))) {
            deserializeObject = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deserializeObject;
    }
}
