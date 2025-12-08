package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import manager.GreenThumbManager;
import model.Address;
import model.Community;
import model.Member;
import model.MemberList;

import java.io.IOException;

/**
 * Controller for displaying and managing the list of members.
 *
 * Supports adding, deleting, saving, converting points,
 * and resetting points for individual members.
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
  @FXML private Button resetButton;
  @FXML private Button convertButton;

  private MemberList memberList;

  /**
   * Initializes the table by binding columns to the Member properties
   * and loading all members into the table.
   */
  @FXML
  public void initialize()
  {
    memberList = GreenThumbManager.getAllMembers();
    memberTable.getItems().clear();
    firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    email.setCellValueFactory(new PropertyValueFactory<>("email"));
    points.setCellValueFactory(new PropertyValueFactory<>("points"));
    address.setCellValueFactory(new PropertyValueFactory<>("address"));
    memberTable.getItems().setAll(memberList.getMemberList());
  }

  /**
   * Deletes the selected member from the table and saves the updated list.
   */
  public void handleDelete()
  {
    Member selected = memberTable.getSelectionModel().getSelectedItem();
    if (selected == null) return;
    memberTable.getItems().remove(selected);
    memberList.getMemberList().remove(selected);
    GreenThumbManager.saveMembers(memberList);
    memberTable.refresh();
  }

  /**
   * Opens a dialog for adding a new member.
   * After the dialog closes, the table is refreshed with updated data.
   */
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

      memberTable.getItems().setAll(memberList.getMemberList());

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Saves the current state of the table back into the member list.
   */
  public void handleSave()
  {
    MemberList list = new MemberList();
    for (Member m : memberTable.getItems())
    {
      list.add(m);
    }
    GreenThumbManager.saveMembers(list);
  }

  /**
   * Converts the selected member's personal points
   * into community points after confirmation.
   */
  public void handleConvert()
  {
    Member selected = memberTable.getSelectionModel().getSelectedItem();

    if (selected == null)
    {
      Alert warn = new Alert(Alert.AlertType.WARNING);
      warn.setTitle("No Member Selected");
      warn.setHeaderText("No member was selected");
      warn.setContentText("Please choose a member whose points you want to convert.");
      warn.showAndWait();
      return;
    }

    if (selected.getPoints() == 0)
    {
      Alert warn = new Alert(Alert.AlertType.WARNING);
      warn.setTitle("No Points");
      warn.setHeaderText("This member has 0 points");
      warn.setContentText("There is nothing to convert.");
      warn.showAndWait();
      return;
    }

    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    confirm.setTitle("Emergency Conversion");
    confirm.setHeaderText("Convert personal points to community points?");
    confirm.setContentText(
        "Member: " + selected.getFirstName() + " " + selected.getLastName() + "\n" +
            "Personal points: " + selected.getPoints() + "\n" +
            "These points will be added to Community Points and reset to 0.\n\n" +
            "Are you absolutely sure?"
    );

    if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK)
      return;

    int personal = selected.getPoints();
    Community.getInstance().addCommunityPoints(personal);
    selected.setPoints(0);
    GreenThumbManager.saveMembers(memberList);
    GreenThumbManager.saveCommunity(Community.getInstance());

    memberTable.refresh();

    Alert info = new Alert(Alert.AlertType.INFORMATION);
    info.setTitle("Conversion Complete");
    info.setHeaderText("Conversion Successful");
    info.setContentText(personal + " points were transferred into Community Points.");
    info.showAndWait();
  }

  /**
   * Resets the selected member's points to 0 after confirmation.
   */
  public void handleReset()
  {
    Member selected = memberTable.getSelectionModel().getSelectedItem();

    if (selected == null)
    {
      Alert warn = new Alert(Alert.AlertType.WARNING);
      warn.setTitle("No Member Selected");
      warn.setHeaderText("No member was selected");
      warn.setContentText("Please choose a member whose points you want to reset.");
      warn.showAndWait();
      return;
    }

    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    confirm.setTitle("Emergency Action");
    confirm.setHeaderText("Reset points for: " +
        selected.getFirstName() + " " + selected.getLastName());
    confirm.setContentText("Are you ABSOLUTELY sure you want to reset this member's points to 0?");

    if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK)
      return;

    selected.setPoints(0);
    GreenThumbManager.saveMembers(memberList);

    memberTable.refresh();
  }

  /**
   * Opens a new view by replacing the current scene with the one
   * loaded from the given FXML file.
   *
   * @param fxmlPath path to the FXML file
   * @param source button that triggered the navigation
   */
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
}
