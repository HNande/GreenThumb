package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * @version 02.12.2025
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

  /**
   *
   *
   * @author Artem Salatskyi
   *
   * @version 03.12.2025
   */
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

    memberTable.getItems().addAll(memberList.getMemberList());

  }

  @FXML
  public void handleDelete()
  {

  }

  @FXML
  public void handleAdd()
  {

  }

  @FXML
  public void handleSave()
  {

  }

  @FXML
  public void handleConvert()
  {

  }

  @FXML
  public void handleReset()
  {

  }

  @FXML
  private void handTask()
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TaskView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) taskButton.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  public void handRecorderTasks()
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecordedTaskView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) taskButton.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  public void handTradeOffers()
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TradeOfferView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) taskButton.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  public void handleCommunit()
  {
    try
    {
     FXMLLoader loader = new FXMLLoader(getClass().getResource("/viev/CommunityView"));
     Scene scene = new Scene(loader.load());
     Stage stage = (Stage) taskButton.getScene().getWindow();
     stage.setScene(scene);
     stage.show();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  private void handMembers()
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MemberView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) taskButton.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

}
