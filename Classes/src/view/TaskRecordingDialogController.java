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
  private static Task task;
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

  public static void setTaskForRecording(Task task){
    TaskRecordingDialogController.task = task;
  }

  public void handleRecord(ActionEvent actionEvent) {
    Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
    if(selectedMember ==null){
      ControllerHelper.showErrorMessage("Member Selection error", "Please select a Memeber before recording");
      return;
    }
    if(day != 0) {
        ControllerHelper.showErrorMessage("Date Pick Error", "Please pick a date before recording");
        return;
    }
      recordedTaskList.add(task.recordTask(selectedMember, day, month, year, boost));
      GreenThumbManager.saveRecordedTasks(recordedTaskList);
      GreenThumbManager.saveMembers(memberList);
      task = null;
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
    task = null;
    stage.close();
  }
}
