public class RecordedTask
{
  private final String name;
  private final int taskType;
  private final Date date;
  public RecordedTask(String name, int taskType, int day, int month, int year){
  this.name = name;
  this.taskType = taskType;
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
