package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

/**
 * Utility class providing methods to read and write objects
 * in binary and JSON formats. This class contains static methods for handling file operations,
 * including serialization and deserialization of objects.
 *
 * @author Nandor Hock
 *
 * @version 02.12.2025
 */
public class MyFileHandler
{
  /**
   * Reads an object from a binary file located in the "data" folder.
   *
   * @param fileName the name of the file to read from
   * @return the deserialized object, or null if an error occurred
   */
  public static Object readFromBinaryFile(String fileName)
  {
    try
    {
      ObjectInputStream read = new ObjectInputStream(new FileInputStream("data/" + fileName));
      Object obj = read.readObject();
      read.close();
      return obj;
    }
    catch(FileNotFoundException e)
    {
      System.out.println("File not found, or cannot be opened");
      System.exit(1);
    }
    catch(IOException e)
    {
      System.out.println("IO Error reading from file");
      System.exit(1);
    }
    catch(ClassNotFoundException e)
    {
      System.out.println("Class not found");
      System.exit(1);
    }
    return null;
  }

  /**
   * Writes the given object to a binary file in the "data" folder.
   *
   * @param fileName the name of the file to write to
   * @param object   the object to be serialized and saved
   */
  public static void writeToBinaryFile(String fileName, Object object)
  {
    try
    {
      ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream("data/" + fileName));
      write.writeObject(object);
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found, or cannot be opened");
      System.exit(1);
    }
    catch(IOException e)
    {
      System.out.println("IO Error writing to file");
      System.exit(1);
    }
    System.out.println("Writing successful");
  }

  /**
   * Saves the given object as a formatted JSON file
   * in the "webpage/json" directory.
   *
   * @param fileName the name of the JSON file (without extension)
   * @param object   the object to convert and write to JSON
   */
  public static void writeToJsonFile(String fileName, Object object)
  {
    try
    {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String json = gson.toJson(object);
      FileWriter writer = new FileWriter("webpage/json/" + fileName + ".json");
      writer.write(json);
      writer.close();
    }
    catch (IOException e)
    {
      System.out.println("Error saving tasks: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
