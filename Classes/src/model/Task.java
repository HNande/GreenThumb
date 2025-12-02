import java.io.Serializable;

public class Task implements Serializable {
  private String name;
  private int pointAmount;
  private int taskType; //1 == COMMUNITY, 2 == INDIVIDUAL
  private int totalCount;

  // Here when inputting in the gui, we won't be providing a 1 or 2,
  // but 2 checkboxes titled them and only one can be checked at one time..
  public Task(String name, int pointAmount, int taskType) {
    this.name = name;
    this.pointAmount = pointAmount;
    this.taskType = taskType;
    this.totalCount = 0;
  }

  public RecordedTask recordTask(Member recordMember, int day, int month, int year) {
    if (taskType == 1) {//IF Invidiual
      if (recordMember.isBoosted()) {
        float temp = Math.round(pointAmount * 1.3);//30% increase in points earned
        recordMember.addPoints(recordMember.getPoints());
      } else {
        recordMember.addPoints(pointAmount);
      }
    } else { //ELSE Community
      Community.getInstance().addCommunityPoints(pointAmount);
    }
    totalCount++;
    return new RecordedTask(name, taskType,pointAmount ,day, month, year);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setPointAmount(int pointAmount) {
    this.pointAmount = pointAmount;
  }

  public int getPointAmount() {
    return pointAmount;
  }

  public void setTaskType(int taskType) {
    this.taskType = taskType;
  }

  public int getTaskType() {
    return taskType;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public boolean equals(Task task) {
    return name.equals(task.getName()) && pointAmount == task.getPointAmount()
        && taskType == task.getTaskType() && totalCount == task.totalCount;
  }

  public String toString() {
    String temp;
    if (taskType == 1) {
      temp = "This task's name is: %S, my point amount is: %d, my task type is: Community My total count is: %d";
    } else {
      temp = "This task's name is: %S, my point amount is: %d, my task Type is: Individual, My total count is %d";
    }
    return String.format(temp, name, pointAmount, totalCount);
  }

}
