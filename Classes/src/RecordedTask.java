public class RecordedTask
{
  private final String name;
  private final int taskType;
  private final String taskOwner = "Anonymous";
  private final int pointAmount;
  private final Date date;
  public RecordedTask(String name, int taskType,int pointAmount ,int day, int month, int year){
  this.name = name;
  this.taskType = taskType;
  this.pointAmount = pointAmount;
  this.date = new Date(day,month,year);
  }
  public String getName() {
    return name;
  }
  public int getTaskType() {
    return taskType;
  }
  public Date getDate() {
    return date;
  }
}
