package utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.TradeOfferList;

import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

public class MyFileHandler {
  public static Object readFromBinaryFile(String fileName){
    try{
      ObjectInputStream read = new ObjectInputStream(new FileInputStream("data/"+fileName));
      Object obj = read.readObject();
      read.close();
      return obj;
    }catch(FileNotFoundException e){
      System.out.println("File not found, or cannot be opened");
    }catch(IOException e){
      System.out.println("IO Error reading from file");
    }catch(ClassNotFoundException e){
      System.out.println("Class not found");
    }
    return null;

  }
  public static void writeToBinaryFile(String fileName,Object object){
    try{
      ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream("data/"+fileName));
      write.writeObject(object);
    }catch (FileNotFoundException e){
      System.out.println("File not found, or cannot be opened");
    }catch(IOException e){
      System.out.println("IO Error writing to file");
    }
    System.out.println("Writing successful");
  }
public static void writeToJsonFile(String fileName, Object object){
  try {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(object);
    FileWriter writer = new FileWriter("webpage/json/"+fileName+".json");
    writer.write(json);
    writer.close();
  } catch (IOException e) {
    System.out.println("Error saving tasks: " + e.getMessage());
    e.printStackTrace();
  }

}
}
