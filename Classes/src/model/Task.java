package model;

import java.io.Serializable;

/**
 * The class represents a task with a name, point amount, type, and total count.
 * It provides methods to record the task and manage its data.
 *
 * @author Nandor Hock
 *
 * @version 02.12.2025
 */
public class Task implements Serializable
{

  private String name;
  private int pointAmount;
  private int taskType; // 1 == COMMUNITY, 2 == INDIVIDUAL
  private int totalCount;

  /**
   * Creates a new Task with specified name, point amount, and type.
   *
   * @param name the name of the task
   * @param pointAmount the amount of points for the task
   * @param taskType the type of the task (1 == COMMUNITY, 2 == INDIVIDUAL)
   */
  public Task(String name, int pointAmount, int taskType)
  {
    this.name = name;
    this.pointAmount = pointAmount;
    this.taskType = taskType;
    this.totalCount = 0;
  }

  /**
   * Records the task for a member or community and returns a RecordedTask.
   *
   * @param recordMember the member performing the task
   * @param day day of the task
   * @param month month of the task
   * @param year year of the task
   * @return a new RecordedTask object
   */
  public RecordedTask recordTask(Member recordMember, int day, int month, int year,boolean boost)
  {
    if (taskType == 1) { // COMMUNITY
      if (boost) {
        float temp = (float) (pointAmount * 1.3); // 30% increase in points
        recordMember.addPoints(Math.round(temp));
      } else {
        recordMember.addPoints(pointAmount);
      }
    } else { // INDIVIDUAL
      Community.getInstance().addCommunityPoints(pointAmount);

    }
    System.out.println("Current Total count "+totalCount);
    return new RecordedTask(name, taskType, pointAmount, day, month, year);
  }

  /**
   * Sets the name of the task.
   *
   * @param name new task name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Returns the name of the task.
   *
   * @return task name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets the point amount of the task.
   *
   * @param pointAmount new point amount
   */
  public void setPointAmount(int pointAmount)
  {
    this.pointAmount = pointAmount;
  }

  /**
   * Returns the point amount of the task.
   *
   * @return point amount
   */
  public int getPointAmount()
  {
    return pointAmount;
  }

  /**
   * Sets the type of the task.
   *
   * @param taskType new task type (1 == COMMUNITY, 2 == INDIVIDUAL)
   */
  public void setTaskType(int taskType)
  {
    this.taskType = taskType;
  }

  /**
   * Returns the type of the task.
   *
   * @return task type
   */
  public int getTaskType()
  {
    return taskType;
  }

  /**
   * Sets the total count of how many times the task was recorded.
   *
   * @param totalCount new total count
   */
  public void setTotalCount(int totalCount)
  {
    this.totalCount = totalCount;
  }

  /**
   * Returns the total count of how many times the task was recorded.
   *
   * @return total count
   */
  public int getTotalCount()
  {
    return totalCount;
  }
  /**
   * Increments the total count by 1.
   *
   */
  public void addToTotalCount(){
    totalCount++;
  }

  /**
   * Compares this task with another task for equality.
   *
   * @param task another task to compare
   * @return true if all fields are equal, false otherwise
   */
  public boolean equals(Task task)
  {
    return name.equals(task.getName()) &&
        pointAmount == task.getPointAmount() &&
        taskType == task.getTaskType() &&
        totalCount == task.totalCount;
  }

  /**
   * Returns a string representation of the task.
   *
   * @return string with task details
   */
  public String toString()
  {
    String temp;
    if (taskType == 1) {
      temp = "This task's name is: %S, my point amount is: %d, my task type is: Community My total count is: %d";
    } else {
      temp = "This task's name is: %S, my point amount is: %d, my task Type is: Individual, My total count is %d";
    }
    return String.format(temp, name, pointAmount, totalCount);
  }

}
