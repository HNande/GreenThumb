package view;
import javafx.scene.control.ButtonType;
import model.TaskList;
import model.Task;
import manager.GreenThumbManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import java.util.Optional;

import static utils.ControllerHelper.*;

/**
 *@author Nandor Hock
 *
 * @version 03.12.2025
 */
public class TaskViewController
{
  public Button recordedTasks;
  public Button record;
  public Button tradeOffers;
  public Button tasks;
  public Button community;
  public Button members;
  public Button add;
  @FXML private TableView<Task> taskTable;
  @FXML private TableColumn<Task, String> taskNameCol;
  @FXML private TableColumn<Task, Integer> taskPointCol;
  @FXML private TableColumn<Task, Integer> taskTypecol;
  @FXML private TableColumn<Task, Integer> taskTotalcol;
  @FXML private Button deleteButton;
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

  }
  public void handleDelete(){
    Task selectedTask = taskTable.getSelectionModel().getSelectedItem(); // selectedTask is now the Object itself how cool is that
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

  }
  public void handleSave(){

  }
}
