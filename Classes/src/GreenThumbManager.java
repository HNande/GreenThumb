import java.io.FileNotFoundException;
import java.io.IOException;

public class GreenThumbManager {
private String tradeOfferFile = "tradeOfferFile";
private String memberFile = "memberFile";
private String communityFile = "communityFile";
private String taskFile = "taskFile";
private String recordedTaskFile = "recordedTaskFile";
public GreenThumbManager(){
}
public TradeOfferList getAllTradeOffers(){
  TradeOfferList tradeOffers = new TradeOfferList();
  try{
    tradeOffers = (TradeOfferList)MyFileHandler.readFromBinaryFile(tradeOfferFile);
  }catch(FileNotFoundException e) {
    System.out.println("File not Found");
  }catch(IOException e) {
    System.out.println("IO Error reading file");
  }catch(ClassNotFoundException e) {
    System.out.println("Class not Found");
  }
  return tradeOffers;
}
public void saveTradeOffers(TradeOfferList tradeOfferList){
  try{
    MyFileHandler.writeToBinaryFile(tradeOfferFile,tradeOfferList);
  }catch(FileNotFoundException e){
    System.out.println("File not Found");
  }catch(IOException e){
    System.out.println("IO Error writing to file");
  }
}
}
