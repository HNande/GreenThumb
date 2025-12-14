package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The class contains a list of all recorded tasks and provides methods to manage them.
 *
 * @author Nandor Hock
 *
 * @version 02.12.2025
 */
public class RecordedTaskList implements Serializable
{
  private final ArrayList<RecordedTask> recordedTaskList = new ArrayList<>();

  /**
   * Creates an empty RecordedTaskList.
   */
  public RecordedTaskList()
  {
  }

  /**
   * Returns a copy of the list of all recorded tasks.
   *
   * @return a new list containing all recorded tasks
   */
  public ArrayList<RecordedTask> getRecordedTaskList()
  {
    return new ArrayList<>(recordedTaskList);
  }

  /**
   * Adds a recorded task to the list.
   *
   * @param recordedTask added to the list
   */
  public void add(RecordedTask recordedTask)
  {
    recordedTaskList.add(recordedTask);
  }

  /**
   * Removes a recorded task from the list.
   *
   * @param recordedTask removed from the list
   */
  public void remove(RecordedTask recordedTask)
  {
    recordedTaskList.remove(recordedTask);
  }
  public void removeOutDated(){
    LocalDate today = LocalDate.now();
    for(int i = 0; i < recordedTaskList.size();i++){
      if(recordedTaskList.get(i).getTimeOfRecord().timePeriod(today) >= 7){
        recordedTaskList.remove(i);
      }
    }


  }

}