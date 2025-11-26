import java.util.ArrayList;
public class RecordedTaskList {
  private final ArrayList<RecordedTask> recordedTaskList = new ArrayList<>();
  public RecordedTaskList() {
  }
  public ArrayList<RecordedTask> getRecordedTaskList() {
    return new ArrayList<>(recordedTaskList);
  }
  public void add(RecordedTask recordedTask) {
    recordedTaskList.add(recordedTask);
  }
  public void remove(RecordedTask recordedTask){
    recordedTaskList.remove(recordedTask);
  }
}
