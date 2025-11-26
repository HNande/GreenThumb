import java.util.ArrayList;
public class TaskList {
  private final ArrayList<Task> taskList = new ArrayList<>();
  public TaskList() {
  }
  public ArrayList<Task> getTaskList() {
    return new ArrayList<>(taskList);
  }
  public void add(Task task) {
    taskList.add(task);
  }
  public void remove(Task task){
    taskList.remove(task)
  }
}
