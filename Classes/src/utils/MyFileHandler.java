package utils;

import java.io.*;

public class MyFileHandler {
  public static Object readFromBinaryFile(String fileName){
    try{
      ObjectInputStream read = new ObjectInputStream(new FileInputStream(fileName));
      Object obj = read.readObject();
      read.close();
      return obj;
    }catch(FileNotFoundException e){
      System.out.println("File not found, or cannot be opened");
      System.exit(1);
    }catch(IOException e){
      System.out.println("IO Error reading from file");
      System.exit(1);
    }catch(ClassNotFoundException e){
      System.out.println("Class not found");
      System.exit(1);
    }
    return null;

  }
  public static void writeToBinaryFile(String filename,Object object){
    try{
      ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(filename));
      write.writeObject(object);
    }catch (FileNotFoundException e){
      System.out.println("File not found, or cannot be opened");
      System.exit(1);
    }catch(IOException e){
      System.out.println("IO Error writing to file");
      System.exit(1);
    }
    System.out.println("Writing successful");
  }




}
