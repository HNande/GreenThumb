package view;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
 *@author Nandor Hock
 *
 * @version 04.12.2025
 */
public class TaskViewController
{
  @FXML private Button recordedTasks;
  @FXML private Button record;
  @FXML private Button tradeOffers;
  @FXML private Button tasks;
  @FXML private Button community;
  @FXML private Button members;
  @FXML private Button add;
  @FXML private Button deleteButton;
  @FXML private TableView<Task> taskTable;
  @FXML private TableColumn<Task, String> taskNameCol;
  @FXML private TableColumn<Task, Integer> taskPointCol;
  @FXML private TableColumn<Task, Integer> taskTypecol;
  @FXML private TableColumn<Task, Integer> taskTotalcol;

  private TaskList taskList = GreenThumbManager.getAllTasks();
  /**
   *
   */
  @FXML
  public void initialize()
  {
    //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
    taskNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    taskPointCol.setCellValueFactory(new PropertyValueFactory<>("pointAmount"));
    taskTypecol.setCellValueFactory(new PropertyValueFactory<>("taskType"));
    taskTotalcol.setCellValueFactory(new PropertyValueFactory<>("totalCount"));
    taskTable.setEditable(true);

    taskTable.getItems().addAll(taskList.getTaskList());

    //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/TextFieldTableCell.html
    taskNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    taskNameCol.setOnEditCommit(event -> {
      boolean pass = true;
      String input = event.getNewValue();
      Task task = event.getRowValue();//Thats the Object in which you are making the edit
      if (isNullOrEmpty(input)) {
        showErrorMessage("Empty value error message ","Edited value cannot be empty.");
        pass = false;
      } else if (input.trim().length() < 4 || input.trim().length() > 32 ){
        showErrorMessage("Name outside of bounds ","Edited value cannot be less than 4, or more than 32 characters.");
        pass = false;
      } else if (taskNameAlreadyExists(taskList.getTaskList(),input)) {
        showErrorMessage("Name already exists ","Edited value cannot already exist in list.");
        pass = false;
      }if(pass)
        task.setName(input.trim());
      taskTable.refresh();
    });
  }

  public void handleTasks(){

  }
  public void handleRecordedTasks(){

  }
  public void handleTradeOffers(){

  }
  public void handleCommunity(){

  }
  public void handleMembers(){

  }
  public void handleRecord(){
    Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
    TaskRecordingDialogController.setTaskForRecording(selectedTask);
    if(selectedTask != null){
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskRecordingDialog.fxml"));
      AnchorPane root = loader.load();
      TaskRecordingDialogController controller = loader.getController();
      System.out.println("Controller passed");
      Stage stage = new Stage();
      stage.setTitle("Task recording dialog");
      stage.setScene(new Scene(root));
      controller.setStage(stage);
      stage.showAndWait();
    }catch (IOException e) {
      e.printStackTrace();
    }
    }
  }
  public void handleDelete(){
    Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
    if(selectedTask != null){
      if (showConfirmationMessage("Deletion confirmation", "Do you really want to delete: "+selectedTask.getName()+"?")){
      taskTable.getItems().remove(selectedTask);
      taskList.remove(selectedTask);
      taskTable.refresh();
      showWarningMessage("Delete successful","Object has been deleted successfully");
      }
    }
  }
  public void handleAdd(){
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
    }catch (IOException e) {
      e.printStackTrace();
    }

  }
}
