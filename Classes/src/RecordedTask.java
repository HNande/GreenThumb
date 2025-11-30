import java.io.Serializable;

public class RecordedTask implements Serializable
{
  private final String name;
  private final int pointAmount;
  private final int taskType;
  private final String taskOwner = "Anonymous";
  private final Date timeOfRecord;
  public RecordedTask(String name, int taskType,int pointAmount ,int day, int month, int year){
  this.name = name;
  this.taskType = taskType;
  this.pointAmount = pointAmount;
  this.timeOfRecord = new Date(day,month,year);
  }
  public String getName() {
    return name;
  }
  public int getTaskType() {
    return taskType;
  }
  public String getTaskOwner() {
    return taskOwner;
  }
  public int getPointAmount() {
    return pointAmount;
  }
  public Date getTimeOfRecord() {
    return timeOfRecord;
  }

}
