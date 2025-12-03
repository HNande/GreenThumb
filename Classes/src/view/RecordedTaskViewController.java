package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.GreenThumbManager;
import model.Task;
import model.TaskList;

/**
 *
 *
 * @author Artem Salatskyi
 *
 * @version 03.12.2025
 */
public class RecordedTaskViewController
{

  @FXML private TableView<Task> taskTable;
  @FXML private TableColumn<Task, String> taskName;
  @FXML private TableColumn<Task, Integer> pointAmount;
  @FXML private TableColumn<Task, Integer> taskType;
  @FXML private TableColumn<Task, Integer> totalCount;

  @FXML private Button task;
  @FXML private Button recorderTasks;
  @FXML private Button tradeOffers;
  @FXML private Button communit;
  @FXML private Button members;

  @FXML
  public void initialize()
  {

    taskName.setCellValueFactory(new PropertyValueFactory<>("name"));
    pointAmount.setCellValueFactory(new PropertyValueFactory<>("pointAmount"));
    taskType.setCellValueFactory(new PropertyValueFactory<>("taskType"));
    totalCount.setCellValueFactory(new PropertyValueFactory<>("totalCount"));
    GreenThumbManager manager = new GreenThumbManager();
    TaskList taskList = manager.getAllTasks();

    taskTable.getItems().addAll(taskList.getTaskList());

  }

  @FXML
  public void handTask()
  {

  }

  @FXML
  public void handRecorderTasks()
  {

  }

  @FXML
  public void handTradeOffers()
  {

  }

  @FXML
  public void handleCommunit()
  {

  }

  @FXML
  public void handMembers()
  {

  }

}
