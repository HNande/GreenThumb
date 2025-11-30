import java.io.FileNotFoundException;
import java.io.IOException;
//Method for calculating how much time has passed
//Method for saving tasks that are younger than 7 days should be in a different list,
// or we can make it that the Recorded task list deletes all tasks older than 7 days(may be easier)
public class GreenThumbManager {
private final String tradeOfferFile = "tradeOfferFile";
private final String memberFile = "memberFile";
private final String communityFile = "communityFile";
private final String taskFile = "taskFile";
private final String recordedTaskFile = "recordedTaskFile";
public GreenThumbManager(){
}
public TradeOfferList getAllTradeOffers(){
  TradeOfferList tradeOffers;
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
