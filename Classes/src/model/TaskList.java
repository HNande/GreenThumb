package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The class contains a list of tasks and provides methods to manage them.
 *
 * @author Nandor Hock
 *
 * @version 02.12.2025
 */
public class TaskList implements Serializable
{
  private final ArrayList<Task> taskList = new ArrayList<>();

  /**
   * Creates an empty TaskList.
   */
  public TaskList()
  {
  }

  /**
   * Returns a copy of the list of all tasks.
   *
   * @return a new list containing all tasks
   */
  public ArrayList<Task> getTaskList()
  {
    return new ArrayList<>(taskList);
  }

  /**
   * Adds a task to the list.
   *
   * @param task added to the list
   */
  public void add(Task task)
  {
    taskList.add(task);
  }

  /**
   * Removes a task from the list.
   *
   * @param task removed from the list
   */
  public void remove(Task task)
  {
    taskList.remove(task);
  }

  public Task  getElementByIndex(int i){
   return taskList.get(i);
  }
}
