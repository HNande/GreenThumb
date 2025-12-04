package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.GreenThumbManager;
import model.Member;
import model.MemberList;
import model.Task;

public class TaskRecordingDialog {

  @FXML private DatePicker datePicker;
  @FXML private Button cancelButton;
  @FXML private ToggleButton boostButton;
  @FXML private Button recordButton;
  @FXML private TableView<Member> memberTable;
  @FXML private TableColumn<Member, String> memberFirstNameCol;
  @FXML private TableColumn<Member, String> memberLastNameCol;
  @FXML private TableColumn<Member, Integer> memberTimeSinceLastRecord;
  private MemberList memberList = GreenThumbManager.getAllMembers();
  private static Task task;

  public void initialize(){

    memberFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    memberLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    memberTimeSinceLastRecord.setCellValueFactory(new PropertyValueFactory<>("lastRecordTime"));
    memberTable.getItems().addAll(memberList.getMemberList());

  }

  public static void setTaskForRecording(Task task){
    TaskRecordingDialog.task = task;
  }

  public void handleRecord(ActionEvent actionEvent) {
  }

  public void toggleBoost(ActionEvent actionEvent) {
  }

  public void cancelTaskRecord(ActionEvent actionEvent) {
  }
}
