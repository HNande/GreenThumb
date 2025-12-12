package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import manager.GreenThumbManager;
import model.Address;
import model.Community;
import model.Member;
import model.MemberList;
import utils.ControllerHelper;

import java.io.IOException;

/**
 * Controller for displaying and managing the list of members.
 *
 * Supports adding, deleting, saving, converting points,
 * and resetting points for individual members.
 *
 * @author Artem Salatskyi
 *
 * @version 09.12.2025
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
  @FXML private TableColumn<Member, Number> lastRecordTimeColumn;

  @FXML private Button deleteButton;
  @FXML private Button addButton;
  @FXML private Button resetButton;
  @FXML private Button convertButton;

  private MemberList memberList;

  /**
   * Checks if an email already exists in the list, excluding the member being edited.
   *
   * @param memberToExclude The member currently being edited.
   * @param email The email address to check.
   * @return true if the email is found for a different member, false otherwise.
   */
  private boolean isEmailDuplicate(Member memberToExclude, String email) {
    for (Member m : memberList.getMemberList()) {
      if (!m.equals(memberToExclude) && m.getEmail().equals(email)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if a phone number already exists in the list, excluding the member being edited.
   *
   * @param memberToExclude The member currently being edited.
   * @param phone The phone number to check.
   * @return true if the phone number is found for a different member, false otherwise.
   */
  private boolean isPhoneDuplicate(Member memberToExclude, String phone) {
    for (Member m : memberList.getMemberList()) {
      if (!m.equals(memberToExclude) && m.getPhoneNumber().equals(phone)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if a member with the given first and last name already exists,
   * excluding the member being edited (if provided).
   *
   * @param memberToExclude The member currently being edited (or null if checking a new combination).
   * @param newFirstName The new first name.
   * @param newLastName The new last name.
   * @return true if the combination is found for a different member, false otherwise.
   */
  private boolean isNameCombinationDuplicate(Member memberToExclude, String newFirstName, String newLastName) {
    for (Member m : memberList.getMemberList()) {
      // Исключаем редактируемого участника, если он совпадает по ссылке
      if (m.equals(memberToExclude)) {
        continue;
      }
      if (m.getFirstName().equals(newFirstName) && m.getLastName().equals(newLastName)) {
        return true;
      }
    }
    return false;
  }


  /**
   * Initializes the table by binding columns to the Member properties
   * and loading all members into the table.
   * Также настраивает встроенное редактирование ячеек (inline editing).
   */
  @FXML
  public void initialize()
  {
    memberList = GreenThumbManager.getAllMembers();

    firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    email.setCellValueFactory(new PropertyValueFactory<>("email"));
    points.setCellValueFactory(new PropertyValueFactory<>("points"));
    address.setCellValueFactory(new PropertyValueFactory<>("address"));
    lastRecordTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lastRecordTime"));

    memberTable.getItems().setAll(memberList.getMemberList());

    memberTable.setEditable(true);

    firstName.setCellFactory(TextFieldTableCell.forTableColumn());
    firstName.setOnEditCommit(event -> {
      Member member = event.getRowValue();
      String newFirstName = event.getNewValue().trim();
      String currentLastName = member.getLastName(); // Используем текущую фамилию

      if (newFirstName.isEmpty()) {
        ControllerHelper.showErrorMessage("Input Error", "First name cannot be empty.");
        memberTable.refresh();
        return;
      }

      if (isNameCombinationDuplicate(member, newFirstName, currentLastName)) {
        ControllerHelper.showErrorMessage("Input Error",
            "A member with the name '" + newFirstName + " " + currentLastName + "' already exists.");
        memberTable.refresh();
        return;
      }

      member.setFirstName(newFirstName);
      GreenThumbManager.saveMembers(memberList);
    });

    lastName.setCellFactory(TextFieldTableCell.forTableColumn());
    lastName.setOnEditCommit(event -> {
      Member member = event.getRowValue();
      String currentFirstName = member.getFirstName(); // Используем текущее имя
      String newLastName = event.getNewValue().trim();

      if (newLastName.isEmpty()) {
        ControllerHelper.showErrorMessage("Input Error", "Last name cannot be empty.");
        memberTable.refresh();
        return;
      }

      if (isNameCombinationDuplicate(member, currentFirstName, newLastName)) {
        ControllerHelper.showErrorMessage("Input Error",
            "A member with the name '" + currentFirstName + " " + newLastName + "' already exists.");
        memberTable.refresh();
        return;
      }

      member.setLastName(newLastName);
      GreenThumbManager.saveMembers(memberList);
    });

    final String PHONE_REGEX = "^(?:\\+?)([0-9 \\-()]{7,20})$";
    phoneNumber.setCellFactory(TextFieldTableCell.forTableColumn());
    phoneNumber.setOnEditCommit(event -> {
      Member member = event.getRowValue();
      String newValue = event.getNewValue().trim();

      if (!newValue.matches(PHONE_REGEX)) {
        ControllerHelper.showErrorMessage("Input Error", "Invalid phone number. Please use a format with 7-20 digits.");
        memberTable.refresh();
        return;
      }

      if (isPhoneDuplicate(member, newValue)) {
        ControllerHelper.showErrorMessage("Input Error", "A different member already has this phone number.");
        memberTable.refresh();
        return;
      }

      member.setPhoneNumber(newValue);
      GreenThumbManager.saveMembers(memberList);
    });

    final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    email.setCellFactory(TextFieldTableCell.forTableColumn());
    email.setOnEditCommit(event -> {
      Member member = event.getRowValue();
      String newValue = event.getNewValue().trim();

      if (!newValue.matches(EMAIL_REGEX)) {
        ControllerHelper.showErrorMessage("Input Error", "Invalid email format. Please use a valid address (e.g., user@example.com).");
        memberTable.refresh();
        return;
      }

      if (isEmailDuplicate(member, newValue)) {
        ControllerHelper.showErrorMessage("Input Error", "A different member already has this email address.");
        memberTable.refresh();
        return;
      }

      member.setEmail(newValue);
      GreenThumbManager.saveMembers(memberList);
    });

    // 5. POINTS (Не отрицательное число)
    points.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    points.setOnEditCommit(event -> {
      Member member = event.getRowValue();
      Integer newValue = event.getNewValue();
      if (newValue != null && newValue >= 0) {
        member.setPoints(newValue);
        GreenThumbManager.saveMembers(memberList);
      } else {
        ControllerHelper.showErrorMessage("Input Error", "Points must be a non-negative integer.");
        memberTable.refresh();
      }
    });
  }

  /**
   * Deletes the selected member from the table and saves the updated list,
   * after asking the user for confirmation.
   */
  public void handleDelete()
  {
    Member selected = memberTable.getSelectionModel().getSelectedItem();
    if (selected == null)
    {
      Alert warn = new Alert(Alert.AlertType.WARNING);
      warn.setTitle("No Member Selected");
      warn.setHeaderText("No member was selected");
      warn.setContentText("Please choose a member you want to delete.");
      warn.showAndWait();
      return;
    }

    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    confirm.setTitle("Confirm Deletion");
    confirm.setHeaderText("Are you sure you want to delete this member?");
    confirm.setContentText(
        "Member: " + selected.getFirstName() + " " + selected.getLastName() + "\n" +
            "This action cannot be undone. Do you want to continue?"
    );

    if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK)
      return;

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

      ctrl.setList(this.memberList);

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

    memberTable.getColumns().get(0).setVisible(false);
    memberTable.getColumns().get(0).setVisible(true);

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

    memberTable.getColumns().get(0).setVisible(false);
    memberTable.getColumns().get(0).setVisible(true);
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

  /**
   * Reloads the MemberList from the manager and updates the TableView content.
   * This is called both on initialization and upon returning to this view
   * from other screens to ensure points are up-to-date.
   */
  public void refreshView()
  {
    memberList = GreenThumbManager.getAllMembers();
    memberTable.getItems().setAll(memberList.getMemberList());
    if (memberTable.getColumns().size() > 0)
    {
      memberTable.getColumns().get(memberTable.getColumns().size() - 1).setVisible(false);
      memberTable.getColumns().get(memberTable.getColumns().size() - 1).setVisible(true);
    }
    memberTable.refresh();
  }
}