package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import manager.GreenThumbManager;
import model.Address;
import model.Member;
import model.MemberList;

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
    GreenThumbManager manager = new GreenThumbManager();
    MemberList memberList = manager.getAllMembers();

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
  public void handTask()
  {

  }

  @FXML
  public void handRecorderTasks()
  {

  }

  @FXML
  public void handTradeOffers()
  {

  }

  @FXML
  public void handleCommunit()
  {

  }

  @FXML
  public void handMembers()
  {

  }

}
