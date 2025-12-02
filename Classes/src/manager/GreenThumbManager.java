package manager;
import model.*;
import utils.MyFileHandler;

/**
 * The manager class for the GreenThumb application.
 * It handles saving and loading of trade offers, members, tasks, recorded tasks, and community data.
 * All data is stored in binary files.
 *
 * @author Nandor Hock
 *
 * @version 02.12.2025
 */
public class GreenThumbManager
{
  private final String tradeOfferFile = "tradeOfferFile";
  private final String memberFile = "memberFile";
  private final String communityFile = "communityFile";
  private final String taskFile = "taskFile";
  private final String recordedTaskFile = "recordedTaskFile";

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
  public TradeOfferList getAllTradeOffers()
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
  public void saveTradeOffers(TradeOfferList tradeOfferList)
  {
    MyFileHandler.writeToBinaryFile(tradeOfferFile, tradeOfferList);
  }

  /**
   * Returns all members stored in the file.
   *
   * @return list of all members
   */
  public MemberList getAllMembers()
  {
    return (MemberList) MyFileHandler.readFromBinaryFile(memberFile);
  }

  /**
   * Saves the given list of members to the file.
   *
   * @param memberList the members to save
   */
  public void saveMembers(MemberList memberList)
  {
      MyFileHandler.writeToBinaryFile(memberFile, memberList);
  }

  /**
   * Returns all tasks stored in the file.
   *
   * @return list of all tasks
   */
  public TaskList getAllTasks()
  {
    return (TaskList) MyFileHandler.readFromBinaryFile(taskFile);
  }

  /**
   * Saves the given list of tasks to the file.
   *
   * @param taskList the tasks to save
   */
  public void saveTasks(TaskList taskList)
  {
      MyFileHandler.writeToBinaryFile(taskFile, taskList);
  }

  /**
   * Returns all recorded tasks stored in the file.
   *
   * @return list of all recorded tasks
   */
  public RecordedTaskList getAllRecordedTasks()
  {
    return (RecordedTaskList) MyFileHandler.readFromBinaryFile(recordedTaskFile);
  }

  /**
   * Saves the given list of recorded tasks to the file.
   *
   * @param recordedTaskList the recorded tasks to save
   */
  public void saveRecordedTasks(RecordedTaskList recordedTaskList)
  {
      MyFileHandler.writeToBinaryFile(recordedTaskFile, recordedTaskList);
  }

  /**
   * Returns the community stored in the file.
   *
   * @return community data
   */
  public Community getCommunity()
  {
    Community community;
    return  (Community) MyFileHandler.readFromBinaryFile(communityFile);
  }

  /**
   * Saves the given community to the file.
   *
   * @param community the community to save
   */
  public void saveCommunity(Community community) {
      MyFileHandler.writeToBinaryFile(communityFile, community);

  }
}
