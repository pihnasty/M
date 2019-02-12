package io.gson.read;

//http://www.javenue.info/post/gson-json-api

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
public class Reader  {

    public static   Object readFromGsonFile(String path, String fileName, Object objectFromGson) {
        String fullName = path + "\\" + fileName;
        return  readFromGsonFile(fullName,objectFromGson);
    }

    public static   Object readFromGsonFile(String fullName, Object objectFromGson) {
        try (FileInputStream fileInputStream = new FileInputStream(fullName);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            Gson gson = new GsonBuilder().create();
        //    objectFromGson = gson.fromJson(reader, type);
         //   gson.fromJson(reader,String.class);
            objectFromGson = gson.fromJson(reader,objectFromGson.getClass());
           return objectFromGson;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectFromGson;
    }
}
