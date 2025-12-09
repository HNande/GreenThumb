package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import manager.GreenThumbManager;
import model.TaskList;
import model.Task;
import utils.ControllerHelper;

import java.io.IOException;

import static utils.ControllerHelper.*;

/**
 * Controller for managing the Task view.
 * Handles displaying tasks, adding, deleting, editing, and recording tasks.
 * Supports inline table editing and input validation.
 *
 * @author Nandor Hock
 *
 * @version 09.12.2025
 */
public class TaskViewController
{

  @FXML public Button record;
  @FXML public TextField taskNameField;
  @FXML public TextField taskPointField;
  @FXML public TextField taskTypeField;
  @FXML private Button add;
  @FXML private Button deleteButton;
  @FXML private TableView<Task> taskTable;
  @FXML private TableColumn<Task, String> taskNameCol;
  @FXML private TableColumn<Task, Integer> taskPointCol;
  @FXML private TableColumn<Task, Integer> taskTypeCol;
  @FXML private TableColumn<Task, Integer> taskTotalCol;

  private TaskList taskList;

  /**
   * Initializes the table and sets up editable columns.
   */
  @FXML
  public void initialize()
  {
    taskList = GreenThumbManager.getAllTasks();

    taskNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    taskPointCol.setCellValueFactory(new PropertyValueFactory<>("pointAmount"));
    taskTypeCol.setCellValueFactory(new PropertyValueFactory<>("taskType"));
    taskTotalCol.setCellValueFactory(new PropertyValueFactory<>("totalCount"));

    taskTable.setEditable(true);
    taskTable.getItems().addAll(taskList.getTaskList());

    taskNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    taskNameCol.setOnEditCommit(event -> {
      String input = event.getNewValue();
      Task task = event.getRowValue();
      if (isNullOrEmpty(input))
      {
        showErrorMessage("Empty value error", "Task Name cannot be empty.");
        taskTable.refresh();
        return;
      }
      if (input.trim().length() < 4 || input.trim().length() > 32)
      {
        showErrorMessage("Name outside of bounds", "Task Name  cannot be less than 4, or more than 32 characters.");
        taskTable.refresh();
        return;
      }
      if (taskNameAlreadyExists(taskList.getTaskList(), input)) {
        ControllerHelper.showErrorMessage("Name already exists", "Task name must unique.");
        taskTable.refresh();
        return;
      }
      task.setName(input.trim());
      GreenThumbManager.saveTasks(taskList);
    });

    taskPointCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    taskPointCol.setOnEditCommit(event -> {
      Integer newValue = event.getNewValue();
      Task task = event.getRowValue();
      if (newValue == null)
      {
        ControllerHelper.showErrorMessage("Points Empty", "Points must not be empty");
        taskTable.refresh();
        return;
      }
      try {
        if (newValue < 0) {
          ControllerHelper.showErrorMessage("Points Invalid", "Points must be a whole number.");
          taskTable.refresh();
          return ;
        }
      } catch (NumberFormatException e) {
        ControllerHelper.showErrorMessage("Points Invalid", "Number must be a whole number.");
        taskTable.refresh();
        return ;
      }
      task.setPointAmount(newValue);
      GreenThumbManager.saveTasks(taskList);
      taskTable.refresh();
    });

    taskTypeCol.setCellFactory(column -> new TableCell<>()
    {
      public void updateItem(Integer item, boolean empty)
      {
        super.updateItem(item, empty);
        if (empty || item == null)
        {
          setText(null);
        }
        else
        {
          switch (item)
          {
            case 1: setText("Community"); break;
            case 2: setText("Individual"); break;
            default: setText(null);
          }
        }
      }

      public void startEdit()
      {
        super.startEdit();
        Task task = getTableRow().getItem();
        if (task != null)
        {
          int currentValue = task.getTaskType();
          int newValue = (currentValue == 1) ? 2 : 1;
          task.setTaskType(newValue);
          GreenThumbManager.saveTasks(taskList);
          getTableView().refresh();
        }
        cancelEdit();
      }
    });
  }

  /**
   * Opens the task recording dialog for the selected task.
   */
  public void handleRecord()
  {
    int index = taskTable.getSelectionModel().getSelectedIndex();
    if (index >= 0)
    {
      try
      {
        TaskRecordingDialogController.setTaskIndex(index);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskRecordingDialog.fxml"));
        AnchorPane root = loader.load();
        TaskRecordingDialogController controller = loader.getController();
        Stage stage = new Stage();
        stage.setTitle("Task recording dialog");
        stage.setScene(new Scene(root));
        controller.setStage(stage);
        stage.showAndWait();

        taskList = GreenThumbManager.getAllTasks();
        taskTable.getItems().clear();
        taskTable.getItems().addAll(taskList.getTaskList());
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      showWarningMessage("No Member selected","Please select a member before recording.");
    }
  }

  /**
   * Deletes the selected task from the table and TaskList.
   */
  public void handleDelete()
  {
    Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
    if (selectedTask != null)
    {
      if (showConfirmationMessage("Deletion confirmation", "Do you really want to delete: " + selectedTask.getName() + "?")) {
        taskTable.getItems().remove(selectedTask);
        taskList.remove(selectedTask);
        GreenThumbManager.saveTasks(taskList);
        taskTable.getItems().clear();
        taskTable.getItems().addAll(taskList.getTaskList());
        showWarningMessage("Delete successful", "Task has been deleted successfully");
      }
    }
  }

  /**
   * Adds a new task with input validation.
   */
  public void handleAdd()
  {
    String name = taskNameField.getText().trim();
    int taskType;
    int pointAmount;

    if (ControllerHelper.isNullOrEmpty(name)) {
      ControllerHelper.showErrorMessage("Name Empty or Null Error", "Name must not be empty.");
      taskNameField.clear();
      return;
    }
    if (name.length() > 32 || name.length() < 4) {
      ControllerHelper.showErrorMessage("Name length Error", "Name must be more than 4 characters, and less than 32 characters including spaces.");
      taskNameField.clear();
      return;
    }
    if (ControllerHelper.taskNameAlreadyExists(taskList.getTaskList(), name)){
      ControllerHelper.showErrorMessage("Name already exists", "Task name must not already exists.");
      taskNameField.clear();
      return;
    }
    try {
      if (Integer.parseInt(taskPointField.getText()) < 0) {
        ControllerHelper.showErrorMessage("Point Value error", "Point amount must be a positive.");
        taskPointField.clear();
        return;
      }
    }
    catch (NumberFormatException e) {
      ControllerHelper.showErrorMessage("Point Value Error", "Point amount must be a not be empty, or with decimal points.");
      taskPointField.clear();
      return;
    }
    pointAmount = Integer.parseInt(taskPointField.getText());
    try {
      int value = Integer.parseInt(taskTypeField.getText());
      if (value == 1 || value == 2) {
        taskType = value;
      } else {
        ControllerHelper.showErrorMessage("Task Type Format error", "Task Type must be a valid number, 1 or 2.");
        taskTypeField.clear();
        return;
      }
    }
    catch (NumberFormatException e) {
      ControllerHelper.showErrorMessage("Task Type Format error", "Task Type must be a number");
      taskTypeField.clear();
      return;
    }

    taskList.add(new Task(name, pointAmount, taskType));
    GreenThumbManager.saveTasks(taskList);
    taskNameField.clear();
    taskTypeField.clear();
    taskPointField.clear();
    taskTable.getItems().clear();
    taskTable.getItems().addAll(taskList.getTaskList());
  }

  public void refreshView()
  {
    taskList = GreenThumbManager.getAllTasks();
    taskTable.getItems().setAll(taskList.getTaskList());
    if (!taskTable.getColumns().isEmpty())
    {
      taskTable.getColumns().getFirst().setVisible(false);
      taskTable.getColumns().getFirst().setVisible(true);
    }
    taskTable.refresh();
  }
}
