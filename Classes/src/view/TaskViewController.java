package view;
import model.TaskList;
import model.Task;
import manager.GreenThumbManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 */
public class TaskViewController
{
  @FXML private TableView<Task> taskTable;
  @FXML private TableColumn<Task, String> taskNameCol;
  @FXML private TableColumn<Task, Integer> taskPointCol;
  @FXML private TableColumn<Task, Integer> taskTypecol;
  @FXML private TableColumn<Task, Integer> taskTotalcol;

  /**
   *
   */
  @FXML
  public void initialize()
  {

    taskNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    taskPointCol.setCellValueFactory(new PropertyValueFactory<>("pointAmount"));
    taskTypecol.setCellValueFactory(new PropertyValueFactory<>("taskType"));
    taskTotalcol.setCellValueFactory(new PropertyValueFactory<>("totalCount"));
    GreenThumbManager manager = new GreenThumbManager();
    TaskList taskList = manager.getAllTasks();

    taskTable.getItems().addAll(taskList.getTaskList());
  }
}
