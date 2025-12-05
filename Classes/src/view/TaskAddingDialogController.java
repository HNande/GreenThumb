package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import manager.GreenThumbManager;
import model.Task;
import model.TaskList;
import utils.ControllerHelper;

public class TaskAddingDialogController {
  @FXML public Button confirmButton;
  @FXML public Button cancelButton;
  @FXML public TextField taskTypeField;
  @FXML public TextField taskNameField;
  @FXML public TextField taskPointField;
  private String name;
  private int pointAmount;
  private int taskType;
  private TaskList taskList = GreenThumbManager.getAllTasks();
  private Stage stage;
  private boolean validPointAmount = false;
  private boolean validTaskType = false;
  private boolean validTaskName = false;
  public void setStage(Stage stage) {
    this.stage = stage;
  }
  public void handleTaskType(ActionEvent actionEvent) {
    try {
      if (Integer.parseInt(taskTypeField.getText()) == 1 || Integer.parseInt(taskTypeField.getText()) == 2) {
        taskType = Integer.parseInt(taskTypeField.getText());
        validTaskType = true;
      } else {
        ControllerHelper.showErrorMessage("Task Type Format error", "Task Type must be a valid number, 1 or 2.");
      }
    }catch (NumberFormatException e){
      ControllerHelper.showErrorMessage("Task Type Format error", "Task Type must be a number");
      taskTypeField.clear();
    }
  }
  public void handleTaskPointAmount(ActionEvent actionEvent) {
    try {
      if (Integer.parseInt(taskPointField.getText()) < 0) {
        ControllerHelper.showErrorMessage("Point Format error","Point amount must be a positive.");
        taskPointField.clear();
        return;
      }
    } catch (NumberFormatException e) {
      ControllerHelper.showErrorMessage("Point Format Error","Point amount must be a valid number without decimal points..");
      taskPointField.clear();
      return;
    }
    pointAmount = Integer.parseInt(taskPointField.getText());
    System.out.println(pointAmount);
    validPointAmount = true;
  }
  public void handleTaskName(ActionEvent actionEvent) {
    if(taskNameField.getText().trim().length() > 32 || taskNameField.getText().trim().length() < 4 ){
      ControllerHelper.showErrorMessage("Name length Error","Name must be more than 4 characters, and less than 32 characters including spaces. ");
      taskNameField.clear();
      return;
    }
    if(ControllerHelper.isNullOrEmpty(taskNameField.getText())){
      ControllerHelper.showErrorMessage("Name Empty or Null Error","Name must not be empty. ");
      taskNameField.clear();
      return;
    }
    name = taskNameField.getText().trim();
    System.out.println(name);
    validTaskName = true;
  }
  public void handleCancel(ActionEvent actionEvent) {
    taskNameField.clear();
    taskTypeField.clear();
    taskPointField.clear();
    stage.close();
  }
  public void handleConfirm(ActionEvent actionEvent) {
    if(validPointAmount && validTaskName && validTaskType){
    taskList.add(new Task(name,pointAmount,taskType));
    GreenThumbManager.saveTasks(taskList);
    System.out.println("Task successfully saved: "+name);
    taskNameField.clear();
    taskTypeField.clear();
    taskPointField.clear();
    stage.close();
    }else{
      ControllerHelper.showErrorMessage("Missing or Invalid Input Error","Please fill in all fields with correct data before confirming.");
    }
  }
}
