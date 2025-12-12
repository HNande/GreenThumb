package model;

import java.io.Serializable;

/**
 * The class represents a list of members with the ability to add, remove, and retrieve them.
 * It stores all members in an internal ArrayList.
 *
 * @author Nandor Hock
 *
 * @version 02.12.2025
 */
public class RecordedTask implements Serializable
{

  private final String name;
  private final int taskType;
  private final String taskOwner = "Anonymous";
  private final int pointAmount;
  private final Date timeOfRecord;

  /**
   * Creates a new RecordedTask with specified name, type, points, and date.
   *
   * @param name the name of the task
   * @param taskType the type of the task
   * @param pointAmount the amount of points for the task
   * @param day day of the record
   * @param month month of the record
   * @param year year of the record
   */
  public RecordedTask(String name, int taskType,int pointAmount ,int day, int month, int year)
  {
  this.name = name;
  this.taskType = taskType;
  this.pointAmount = pointAmount;
  this.timeOfRecord = new Date(day,month,year);
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
   * Returns the type of the task.
   *
   * @return task type
   */
  public int getTaskType()
  {
    return taskType;
  }

  /**
   * Returns the owner of the task.
   *
   * @return task owner
   */
  public String getTaskOwner()
  {
    return taskOwner;
  }

  /**
   * Returns the amount of points for the task.
   *
   * @return points amount
   */
  public int getPointAmount()
  {
    return pointAmount;
  }

  /**
   * Returns the date when the task was recorded.
   *
   * @return date of record
   */
  public Date getTimeOfRecord() {
    return timeOfRecord;
  }

}
