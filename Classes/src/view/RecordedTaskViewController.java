package view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.GreenThumbManager;
import model.Date;
import model.RecordedTask;
import model.RecordedTaskList;

/**
 * Controller for displaying the list of recorded tasks.
 * Initializes the table and loads all recorded tasks from the manager.
 *
 * @author Nandor Hock
 *
 * @version 03.12.2025
 */
public class RecordedTaskViewController
{
  @FXML private TableView<RecordedTask> recordedTaskTable;
  @FXML private TableColumn<RecordedTask, String> taskNameCol;
  @FXML private TableColumn<RecordedTask, Integer> pointAmountCol;
  @FXML private TableColumn<RecordedTask, Integer> taskTypeCol;
  @FXML private TableColumn<RecordedTask, String> taskOwnerCol;
  @FXML private TableColumn<RecordedTask, Date> timeOfRecordCol;

  private RecordedTaskList recordedTaskList;

  /**
   * Initializes the table columns and fills the table with all recorded tasks.
   */
  @FXML
  public void initialize()
  {
    recordedTaskList = GreenThumbManager.getAllRecordedTasks();

    taskNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    pointAmountCol.setCellValueFactory(new PropertyValueFactory<>("pointAmount"));
    taskTypeCol.setCellValueFactory(new PropertyValueFactory<>("taskType"));
    taskOwnerCol.setCellValueFactory(new PropertyValueFactory<>("taskOwner"));
    timeOfRecordCol.setCellValueFactory(new PropertyValueFactory<>("timeOfRecord"));

    recordedTaskTable.getItems().addAll(recordedTaskList.getRecordedTaskList());
  }
}
