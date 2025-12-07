package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import manager.GreenThumbManager;
import model.*;
import utils.ControllerHelper;

import java.time.LocalDate;

public class TaskRecordingDialogController {

  @FXML private DatePicker datePicker;
  @FXML private Button cancelButton;
  @FXML private ToggleButton boostButton;
  @FXML private Button recordButton;
  @FXML private TableView<Member> memberTable;
  @FXML private TableColumn<Member, String> memberFirstNameCol;
  @FXML private TableColumn<Member, String> memberLastNameCol;
  @FXML private TableColumn<Member, Integer> memberPointAmountCol;
  @FXML private TableColumn<Member, Integer> memberTimeSinceLastRecord;

  private Stage stage;
  private final MemberList memberList = GreenThumbManager.getAllMembers();
  private final RecordedTaskList recordedTaskList = GreenThumbManager.getAllRecordedTasks();
  private static int index;
  private TaskList taskList= GreenThumbManager.getAllTasks();
  private boolean boost;
  private int day;
  private int month;
  private int year;

  public void initialize(){
    memberFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    memberLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    memberPointAmountCol.setCellValueFactory(new PropertyValueFactory<>("points"));
    memberTimeSinceLastRecord.setCellValueFactory(new PropertyValueFactory<>("lastRecordTime"));
    memberTable.getItems().addAll(memberList.getMemberList());
    datePicker.setValue(LocalDate.now());
    System.out.println("Table set");

    boostButton.selectedProperty().addListener((obs, previousSelection, currentSelection) -> {
      if (currentSelection) {
        boostButton.setStyle("-fx-base: #4CAF50;");  // Boost is green when selected
      } else {
        boostButton.setStyle("-fx-base: #cccccc;");  // Gray when not selected
      }
    });

  }
  public void setStage(Stage stage){
    this.stage = stage;
  }
  /**
   * Sets the Arraylist index of the selected task from the TaskView.
   *
   * @param i is the index inside the ArrayList
   */
  public static void setTaskIndex(int i){
    TaskRecordingDialogController.index = i;
  }

  public void handleRecord(ActionEvent actionEvent) {
    Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
    if(selectedMember ==null){
      ControllerHelper.showErrorMessage("Member Selection error", "Please select a Member before recording");
      return;
    }
    if(day != 0) {
        ControllerHelper.showErrorMessage("Date Pick Error", "Please pick a date before recording");
        return;
    }
    //This records the task inside the list based on the index value, and adds the returning Recorded Task object to the RecordedTask List
    recordedTaskList.add(taskList.getElementByIndex(index).recordTask(selectedMember, day, month, year, boost));
    taskList.getElementByIndex(index).addToTotalCount();
    GreenThumbManager.saveRecordedTasks(recordedTaskList);
    GreenThumbManager.saveMembers(memberList);
    GreenThumbManager.saveTasks(taskList);
    day = 0;
    month = 0;
    year = 0;
    boost = false;
    stage.close();
  }
  public void toggleBoost(ActionEvent actionEvent) {
    boost = boostButton.isSelected();
  }
  public void handleDatePicker(ActionEvent actionEvent){
    LocalDate localDate = datePicker.getValue();
    int day = localDate.getDayOfMonth();
    int month = localDate.getMonthValue();
    int year = localDate.getYear();
    System.out.println("Day: " +day+" Month: "+month+" Year: "+year);
  }
  public void cancelTaskRecord(ActionEvent actionEvent) {
    stage.close();
  }
}
