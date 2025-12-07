package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.TaskList;
import model.Task;
import manager.GreenThumbManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Button;
import utils.ControllerHelper;

import java.io.IOException;

import static utils.ControllerHelper.*;
/**
 * @author Nandor Hock
 * @version 04.12.2025
 */
public class TaskViewController {
  @FXML private Button add;
  @FXML private Button deleteButton;
  @FXML private TableView<Task> taskTable;
  @FXML private TableColumn<Task, String> taskNameCol;
  @FXML private TableColumn<Task, Integer> taskPointCol;
  @FXML private TableColumn<Task, Integer> taskTypeCol;
  @FXML private TableColumn<Task, Integer> taskTotalCol;
  private TaskList taskList = GreenThumbManager.getAllTasks();
  /**
   *
   */
  @FXML public void initialize() {
    //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
    taskNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    taskPointCol.setCellValueFactory(new PropertyValueFactory<>("pointAmount"));
    taskTypeCol.setCellValueFactory(new PropertyValueFactory<>("taskType"));
    taskTotalCol.setCellValueFactory(new PropertyValueFactory<>("totalCount"));
    taskTable.setEditable(true);

    taskTable.getItems().addAll(taskList.getTaskList());

    //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/TextFieldTableCell.html
    taskNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    taskNameCol.setOnEditCommit(event -> {
      String input = event.getNewValue();
      Task task = event.getRowValue();
      if (isNullOrEmpty(input)) {
        taskTable.refresh();
        return;
      }
      if (input.trim().length() < 4 || input.trim().length() > 32) {
        showErrorMessage("Name outside of bounds", "Edited value cannot be less than 4, or more than 32 characters.");
        taskTable.refresh();
        return;
      }
      if (taskNameAlreadyExists(taskList.getTaskList(), input)) {
        taskTable.refresh();
        return;
      }
      task.setName(input.trim());
      GreenThumbManager.saveTasks(taskList);
    });
    taskPointCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    taskPointCol.setOnEditCommit(event -> {
      Integer input = event.getNewValue();
      Task task = event.getRowValue();
      if (ControllerHelper.isValidInteger(input)) {
        taskTable.refresh();
        return;
      }
      task.setPointAmount(input);
      GreenThumbManager.saveTasks(taskList);
    });
    taskTypeCol.setCellFactory(column -> new TableCell<>() {
      public void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
        } else {
          switch (item) {
            case 1:
              setText("Community");
              break;
            case 2:
              setText("Individual");
              break;
            default:
          }
        }
      }
      public void startEdit() {
        super.startEdit();

        Task task = getTableRow().getItem();
        if (task != null) {
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

  public void handleTasks() {

  }
  public void handleRecordedTasks() {

  }

  public void handleTradeOffers() {
    try {
      System.out.println(getClass().getResource("TradeOfferView.fxml"));
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TradeOfferView.fxml"));
      AnchorPane root = loader.load();
      TradeOfferViewController controller = loader.getController();
      Stage stage = new Stage();
      stage.setTitle("Trade Offer View");
      stage.setScene(new Scene(root));
      stage.show();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void handleCommunity() {

  }

  public void handleMembers() {

  }

  public void handleRecord() {
    Task selectedTask = taskTable.getSelectionModel().getSelectedItem();

    if (selectedTask != null) {
      try {
        for (int i = 0; i < taskList.getTaskList().size(); i++) {
          if (taskList.getElementByIndex(i) == selectedTask) {
            TaskRecordingDialogController.setTaskIndex(i);
          }
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskRecordingDialog.fxml"));
        AnchorPane root = loader.load();
        TaskRecordingDialogController controller = loader.getController();
        System.out.println("Controller passed");
        Stage stage = new Stage();
        stage.setTitle("Task recording dialog");
        stage.setScene(new Scene(root));
        controller.setStage(stage);
        stage.showAndWait();
        taskList = GreenThumbManager.getAllTasks();
        taskTable.getItems().clear();
        taskTable.getItems().addAll(taskList.getTaskList());
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void handleDelete() {
    Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
    if (selectedTask != null) {
      if (showConfirmationMessage("Deletion confirmation", "Do you really want to delete: " + selectedTask.getName() + "?")) {
        taskTable.getItems().remove(selectedTask);
        taskList.remove(selectedTask);
      /*Probably the worst way to implement this updating sequence,
      as each time we are changing anything inside the lists,
      it clears, and repopulates the whole table.
       */
        GreenThumbManager.saveTasks(taskList);
        taskTable.getItems().clear();
        taskTable.getItems().addAll(taskList.getTaskList());

        showWarningMessage("Delete successful", "Object has been deleted successfully");
      }
    }
  }

  public void handleAdd() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskAddingDialog.fxml"));
      AnchorPane root = loader.load();
      TaskAddingDialogController controller = loader.getController();
      System.out.println("Controller passed");
      Stage stage = new Stage();
      stage.setTitle("Task recording dialog");
      stage.setScene(new Scene(root));
      controller.setStage(stage);
      stage.showAndWait();
      taskList = GreenThumbManager.getAllTasks();
      taskTable.getItems().clear();
      taskTable.getItems().addAll(taskList.getTaskList());
    }
    catch (IOException e) {
      e.printStackTrace();
    }

  }
}
