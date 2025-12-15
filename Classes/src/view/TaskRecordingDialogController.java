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

/**
 * Controller for the task recording dialog.
 *
 * Handles selecting a member, picking a date, applying boost,
 * and recording tasks into the system.
 *
 * @author Nandor Hock
 * @version 08.12.2025
 */
public class TaskRecordingDialogController
{

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
  private TaskList taskList = GreenThumbManager.getAllTasks();
  private boolean boost;
  private int day;
  private int month;
  private int year;

  /**
   * Initializes the dialog, sets up the table and date picker,
   * and configures the boost toggle button style.
   */
  public void initialize()
  {
    memberFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    memberFirstNameCol.setReorderable(false);
    memberLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    memberLastNameCol.setReorderable(false);
    memberPointAmountCol.setCellValueFactory(new PropertyValueFactory<>("points"));
    memberPointAmountCol.setReorderable(false);
    memberTimeSinceLastRecord.setCellValueFactory(new PropertyValueFactory<>("lastRecordTime"));
    memberTimeSinceLastRecord.setReorderable(false);
    memberTable.getItems().addAll(memberList.getMemberList());
    datePicker.setValue(LocalDate.now());
    LocalDate localDate = datePicker.getValue();
    day = localDate.getDayOfMonth();
    month = localDate.getMonthValue();
    year = localDate.getYear();
    System.out.println("Day: " + day + " Month: " + month + " Year: " + year);

    boostButton.selectedProperty().addListener((obs, previousSelection, currentSelection) -> {
      if (currentSelection) {
        boostButton.setStyle("-fx-base: #4CAF50;");  // Boost is green when selected
      } else {
        boostButton.setStyle("-fx-base: #cccccc;");  // Gray when not selected
      }
    });
  }

  /**
   * Sets the stage of this dialog for later closing.
   *
   * @param stage the window stage
   */
  public void setStage(Stage stage){
    this.stage = stage;
  }

  /**
   * Sets the index of the selected task from the TaskView.
   *
   * @param i index in the TaskList
   */
  public static void setTaskIndex(int i){
    TaskRecordingDialogController.index = i;
  }

  /**
   * Records the selected task for the selected member.
   * Validates member selection and date before saving.
   *
   * @param actionEvent the event triggered by pressing the record button
   */
  public void handleRecord(ActionEvent actionEvent) {
    Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
    if(selectedMember == null){
      ControllerHelper.showErrorMessage("Member Selection error", "Please select a Member before recording");
      return;
    }

    // Record the task and add it to the recorded task list
    recordedTaskList.add(taskList.getElementByIndex(index).recordTask(selectedMember, day, month, year, boost));
    GreenThumbManager.saveCommunity(Community.getInstance());
    GreenThumbManager.saveRecordedTasks(recordedTaskList);
    GreenThumbManager.saveMembers(memberList);
    GreenThumbManager.saveTasks(taskList);

    day = 0;
    month = 0;
    year = 0;
    boost = false;

    stage.close();
  }

  /**
   * Toggles the boost flag when the boost button is pressed.
   *
   * @param actionEvent the event triggered by pressing the boost button
   */
  public void toggleBoost(ActionEvent actionEvent) {
    boost = boostButton.isSelected();
  }

  /**
   * Handles the date picker selection and stores the chosen date.
   *
   * @param actionEvent the event triggered by selecting a date
   */
  public void handleDatePicker(ActionEvent actionEvent){
    LocalDate localDate = datePicker.getValue();
    day = localDate.getDayOfMonth();
    month = localDate.getMonthValue();
    year = localDate.getYear();
    System.out.println("Day: " + day + " Month: " + month + " Year: " + year);
  }

  /**
   * Cancels task recording and closes the dialog.
   *
   * @param actionEvent the event triggered by pressing the cancel button
   */
  public void cancelTaskRecord(ActionEvent actionEvent) {
    stage.close();
  }
}
