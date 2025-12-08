package view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import manager.GreenThumbManager;
import model.Address;
import model.Member;
import model.MemberList;

import java.io.IOException;

/**
 *
 *
 * @author Artem Salatskyi
 *
 * @version 03.12.2025
 */
public class MemberViewController
{

  @FXML private TableView<Member> memberTable;
  @FXML private TableColumn<Member, String> firstName;
  @FXML private TableColumn<Member, String> lastName;
  @FXML private TableColumn<Member, String> phoneNumber;
  @FXML private TableColumn<Member, String> email;
  @FXML private TableColumn<Member, Integer> points;
  @FXML private TableColumn<Member, Address> address;

  @FXML private Button deleteButton;
  @FXML private Button addButton;
  @FXML private Button saveButton;
  @FXML private Button convertButton;
  @FXML private Button resetButton;

  @FXML private Button task;
  @FXML private Button recorderTasks;
  @FXML private Button tradeOffers;
  @FXML private Button communit;
  @FXML private Button members;

  @FXML private Button taskButton;

  @FXML
  public void initialize()
  {
    firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    email.setCellValueFactory(new PropertyValueFactory<>("email"));
    points.setCellValueFactory(new PropertyValueFactory<>("points"));
    address.setCellValueFactory(new PropertyValueFactory<>("address"));

    MemberList memberList = GreenThumbManager.getAllMembers();
    memberTable.getItems().setAll(memberList.getMemberList());
  }

  @FXML
  public void handleDelete()
  {
    Member selected = memberTable.getSelectionModel().getSelectedItem();
    if (selected == null) return;
    MemberList list = GreenThumbManager.getAllMembers();
    list.remove(selected);
    GreenThumbManager.saveMembers(list);
    memberTable.getItems().remove(selected);
  }

  @FXML
  public void handleAdd()
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MemberAddView.fxml"));
      Scene scene = new Scene(loader.load());

      MemberAddController ctrl = loader.getController();
      Stage dialog = new Stage();
      dialog.setTitle("Add New Member");
      dialog.initOwner(addButton.getScene().getWindow());
      ctrl.setStage(dialog);
      ctrl.setList(GreenThumbManager.getAllMembers());

      dialog.setScene(scene);
      dialog.showAndWait();

      MemberList list = GreenThumbManager.getAllMembers();
      memberTable.getItems().setAll(list.getMemberList());

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  public void handleSave()
  {
    MemberList list = new MemberList();
    for (Member m : memberTable.getItems())
    {
      list.add(m);
    }
    GreenThumbManager.saveMembers(list);
  }

  @FXML
  public void handleConvert()
  {
    MemberList list = GreenThumbManager.getAllMembers();
    for (Member m : list.getMemberList())
    {
      m.convertPoints();
    }
    GreenThumbManager.saveMembers(list);
    memberTable.getItems().setAll(list.getMemberList());
  }

  @FXML
  public void handleReset()
  {
    MemberList list = GreenThumbManager.getAllMembers();
    memberTable.getItems().setAll(list.getMemberList());
  }

  private void openView(String fxmlPath, Button source)
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) source.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  private void handTask()
  {
    Button src = (task != null) ? task : taskButton;
    openView("/view/TaskView.fxml", src);
  }

  @FXML
  public void handRecorderTasks()
  {
    Button src = (recorderTasks != null) ? recorderTasks : taskButton;
    openView("/view/RecordedTaskView.fxml", src);
  }

  @FXML
  public void handTradeOffers()
  {
    Button src = (tradeOffers != null) ? tradeOffers : taskButton;
    openView("/view/TradeOfferView.fxml", src);
  }

  @FXML
  public void handleCommunit()
  {
    Button src = (communit != null) ? communit : taskButton;
    openView("/view/CommunityView.fxml", src);
  }

  @FXML
  private void handMembers()
  {
    Button src = (members != null) ? members : taskButton;
    openView("/view/MemberView.fxml", src);
  }

}
