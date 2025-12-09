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
  private String name;
  private boolean validTaskName;
  private int pointAmount;
  private boolean validPointAmount;
  private int taskType;
  private boolean validTaskType;

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
        showErrorMessage("Empty value error", "Description cannot be empty.");
        taskTable.refresh();
        return;
      }
      if (input.trim().length() < 4 || input.trim().length() > 32)
      {
        showErrorMessage("Name outside of bounds", "Edited value cannot be less than 4, or more than 32 characters.");
        taskTable.refresh();
        return;
      }
      if (taskNameAlreadyExists(taskList.getTaskList(), input))
      {
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
          ControllerHelper.showErrorMessage("Invalid Input", "Number must be a  non-negative number.");
          taskTable.refresh();
          return ;
        }
      } catch (NumberFormatException e) {
        ControllerHelper.showErrorMessage("Invalid Input", "Number must be a whole number.");
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
    if (validPointAmount && validTaskName && validTaskType)
    {
      taskList.add(new Task(name, pointAmount, taskType));
      GreenThumbManager.saveTasks(taskList);
      taskNameField.clear();
      taskTypeField.clear();
      taskPointField.clear();
      validPointAmount = false;
      validTaskName = false;
      validTaskType = false;
    }
    else
    {
      ControllerHelper.showErrorMessage("Missing or Invalid Input Error", "Please fill in all fields with correct data before confirming.");
    }
    taskTable.getItems().clear();
    taskTable.getItems().addAll(taskList.getTaskList());
  }

  /**
   * Validates and sets the task name from input field.
   */
  public void handleTaskName(ActionEvent actionEvent)
  {
    if (taskNameField.getText().trim().length() > 32 || taskNameField.getText().trim().length() < 4)
    {
      ControllerHelper.showErrorMessage("Name length Error", "Name must be more than 4 characters, and less than 32 characters including spaces.");
      taskNameField.clear();
      return;
    }
    if (ControllerHelper.isNullOrEmpty(taskNameField.getText()))
    {
      ControllerHelper.showErrorMessage("Name Empty or Null Error", "Name must not be empty.");
      taskNameField.clear();
      return;
    }
    name = taskNameField.getText().trim();
    validTaskName = true;
  }

  /**
   * Validates and sets the task point amount from input field.
   */
  public void handleTaskPointAmount(ActionEvent actionEvent)
  {
    try
    {
      if (Integer.parseInt(taskPointField.getText()) < 0)
      {
        ControllerHelper.showErrorMessage("Point Format error", "Point amount must be a positive.");
        taskPointField.clear();
        return;
      }
    }
    catch (NumberFormatException e)
    {
      ControllerHelper.showErrorMessage("Point Format Error", "Point amount must be a valid number without decimal points.");
      taskPointField.clear();
      return;
    }
    pointAmount = Integer.parseInt(taskPointField.getText());
    validPointAmount = true;
  }

  /**
   * Validates and sets the task type from input field.
   */
  public void handleTaskType(ActionEvent actionEvent)
  {
    try
    {
      int value = Integer.parseInt(taskTypeField.getText());
      if (value == 1 || value == 2)
      {
        taskType = value;
        validTaskType = true;
      }
      else
      {
        ControllerHelper.showErrorMessage("Task Type Format error", "Task Type must be a valid number, 1 or 2.");
      }
    }
    catch (NumberFormatException e)
    {
      ControllerHelper.showErrorMessage("Task Type Format error", "Task Type must be a number");
      taskTypeField.clear();
    }
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
