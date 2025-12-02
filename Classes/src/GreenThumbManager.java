public class GreenThumbManager {
  private final String tradeOfferFile = "tradeOfferFile";
  private final String memberFile = "memberFile";
  private final String communityFile = "communityFile";
  private final String taskFile = "taskFile";
  private final String recordedTaskFile = "recordedTaskFile";
  public GreenThumbManager() {
  }
  public TradeOfferList getAllTradeOffers() {
    TradeOfferList tradeOffers;
    tradeOffers = (TradeOfferList) MyFileHandler.readFromBinaryFile(tradeOfferFile);
    return tradeOffers;
  }
  public void saveTradeOffers(TradeOfferList tradeOfferList) {
    MyFileHandler.writeToBinaryFile(tradeOfferFile, tradeOfferList);
  }
  public MemberList getAllMembers() {
    return (MemberList) MyFileHandler.readFromBinaryFile(memberFile);
  }
  public void saveMembers(MemberList memberList) {
      MyFileHandler.writeToBinaryFile(memberFile, memberList);
  }
  public TaskList getAllTasks() {
    return (TaskList) MyFileHandler.readFromBinaryFile(taskFile);
  }
  public void saveTasks(TaskList taskList) {
      MyFileHandler.writeToBinaryFile(taskFile, taskList);
  }
  public RecordedTaskList getAllRecordedTasks() {
    return (RecordedTaskList) MyFileHandler.readFromBinaryFile(recordedTaskFile);
  }
  public void saveRecordedTasks(RecordedTaskList recordedTaskList) {
      MyFileHandler.writeToBinaryFile(recordedTaskFile, recordedTaskList);
  }
  public Community getCommunity() {
    Community community;
    return  (Community) MyFileHandler.readFromBinaryFile(communityFile);
  }
  public void saveCommunity(Community community) {
      MyFileHandler.writeToBinaryFile(communityFile, community);

  }
}
