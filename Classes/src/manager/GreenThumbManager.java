package manager;
import model.*;
import utils.MyFileHandler;

/**
 * The manager class for the GreenThumb application.
 * It handles saving and loading of trade offers, members, tasks, recorded tasks, and community data.
 * All data is stored in binary files.
 *
 * @author Sofia Golban
 *
 * @version 02.12.2025
 */
public class GreenThumbManager
{
  private static final String tradeOfferFile = "tradeOfferFile";
  private static final String tradeOfferJsonFile = "tradeOfferjsonFile";
  private static final String memberFile = "memberFile";
  private static final String communityFile = "communityFile";
  private static final String communityJsonFile = "communityJsonFile";
  private static final String taskFile = "taskFile";
  private static final String recordedTaskFile = "recordedTaskFile";
  private static final String recordedTaskJsonFile = "recordedTaskJsonFile";
  private static final String dateFile = "datefile";

  /**
   * Creates a new GreenThumbManager.
   */
  public GreenThumbManager()
  {
  }

  /**
   * Returns all trade offers stored in the file.
   *
   * @return list of all trade offers
   */
  public static TradeOfferList getAllTradeOffers()
  {
    TradeOfferList tradeOffers;
    tradeOffers = (TradeOfferList) MyFileHandler.readFromBinaryFile(tradeOfferFile);
    return tradeOffers;
  }

  /**
   * Saves the given list of trade offers to the file.
   *
   * @param tradeOfferList the trade offers to save
   */
  public static void saveTradeOffers(TradeOfferList tradeOfferList)
  {
    MyFileHandler.writeToBinaryFile(tradeOfferFile, tradeOfferList);
  }

  /**
   * Returns all members stored in the file.
   *
   * @return list of all members
   */
  public static MemberList getAllMembers()
  {
    return (MemberList) MyFileHandler.readFromBinaryFile(memberFile);
  }

  /**
   * Saves the given list of members to the file.
   *
   * @param memberList the members to save
   */
  public static void saveMembers(MemberList memberList)
  {
      MyFileHandler.writeToBinaryFile(memberFile, memberList);
  }

  /**
   * Returns all tasks stored in the file.
   *
   * @return list of all tasks
   */
  public static TaskList getAllTasks()
  {
    return (TaskList) MyFileHandler.readFromBinaryFile(taskFile);
  }

  /**
   * Saves the given list of tasks to the file.
   *
   * @param taskList the tasks to save
   */
  public static void saveTasks(TaskList taskList)
  {
      MyFileHandler.writeToBinaryFile(taskFile, taskList);
  }

  /**
   * Returns all recorded tasks stored in the file.
   *
   * @return list of all recorded tasks
   */
  public static RecordedTaskList getAllRecordedTasks()
  {
    return (RecordedTaskList) MyFileHandler.readFromBinaryFile(recordedTaskFile);
  }

  /**
   * Saves the given list of recorded tasks to the file.
   *
   * @param recordedTaskList the recorded tasks to save
   */
  public static void saveRecordedTasks(RecordedTaskList recordedTaskList)
  {
      MyFileHandler.writeToBinaryFile(recordedTaskFile, recordedTaskList);
  }

  /**
   * Returns the community stored in the file.
   *
   * @return community data
   */
  public static Community getCommunity()
  {
    return  (Community) MyFileHandler.readFromBinaryFile(communityFile);
  }

  public static Date getDate()
  {
    return  (Date) MyFileHandler.readFromBinaryFile(dateFile);
  }
  /**
   * Saves the given community to the file.
   *
   * @param community the community to save
   */
  public static void saveCommunity(Community community) {
      MyFileHandler.writeToBinaryFile(communityFile, community);
  }
  public static void saveDate(Date date) {
    MyFileHandler.writeToBinaryFile(dateFile, date);
  }
  public static void saveFileToJson(RecordedTaskList recordedTaskList) {
    MyFileHandler.writeToJsonFile(recordedTaskJsonFile,recordedTaskList);
  }
  public static void saveFileToJson(Community community) {
    MyFileHandler.writeToJsonFile(communityJsonFile,community);
  }
  public static void saveFileToJson(TradeOfferList tradeOfferList) {
    MyFileHandler.writeToJsonFile(tradeOfferJsonFile,tradeOfferList);
  }

}
