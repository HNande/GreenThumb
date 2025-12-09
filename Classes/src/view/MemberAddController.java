package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import manager.GreenThumbManager;
import model.Address;
import model.Member;
import model.MemberList;

/**
 * Controller for adding or editing a member.
 * Handles input validation and saving changes to the member list.
 *
 * @author Artem Salatskyi
 *
 * @version 08.12.2025
 */
public class MemberAddController
{

  @FXML private TextField firstNameField;
  @FXML private TextField lastNameField;
  @FXML private TextField phoneField;
  @FXML private TextField emailField;
  @FXML private TextField houseNumberField;
  @FXML private TextField streetField;

  private Stage stage;
  private MemberList list;
  private Member editingMember = null;

  /**
   * Sets the stage used for closing the window.
   *
   * @param stage the window stage
   */
  public void setStage(Stage stage)
  {
    this.stage = stage;
  }

  /**
   * Sets the member list where the new or edited member will be stored.
   *
   * @param list the member list
   */
  public void setList(MemberList list)
  {
    this.list = list;
  }

  /**
   * Loads an existing member into the text fields for editing.
   *
   * @param m the member to edit
   */
  public void editMember(Member m)
  {
    this.editingMember = m;

    firstNameField.setText(m.getFirstName());
    lastNameField.setText(m.getLastName());
    phoneField.setText(m.getPhoneNumber());
    emailField.setText(m.getEmail());
    streetField.setText(m.getAddressStreet());
    houseNumberField.setText(String.valueOf(m.getAddressHouse()));
  }

  /**
   * Closes the window without saving changes.
   */
  @FXML
  private void handleCancel()
  {
    stage.close();
  }

  /**
   * Validates the input and either creates a new member
   * or updates an existing one.
   *
   * Shows an error message if the input is invalid.
   */
  @FXML
  private void handleConfirm()
  {
    String first = firstNameField.getText().trim();
    String last = lastNameField.getText().trim();
    String phone = phoneField.getText().trim();
    String email = emailField.getText().trim();
    String street = streetField.getText().trim();
    String houseStr = houseNumberField.getText().trim();

    if (first.isEmpty() || last.isEmpty())
    {
      showError("Name fields cannot be empty.");
      return;
    }

    if (!email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"))
    {
      showError("Invalid email format. Please use a valid address (e.g., user@example.com).");
      return;
    }

    if (!phone.matches("^(?:\\+?)([0-9 \\-()]{7,20})$"))
    {
      showError("Invalid phone number. Please use a format with 7-20 digits (e.g., +1234567890).");
      return;
    }


    int houseNumber;
    try
    {
      houseNumber = Integer.parseInt(houseStr);
    }
    catch (NumberFormatException e)
    {
      showError("House number must be a valid integer.");
      return;
    }

    Address address = new Address(houseNumber, street);

    if (editingMember == null)
    {
      Member newMember = new Member(first, last, phone, email, houseNumber, street);
      list.add(newMember);
    }
    else
    {
      editingMember.setFirstName(first);
      editingMember.setLastName(last);
      editingMember.setPhoneNumber(phone);
      editingMember.setEmail(email);
      editingMember.setAddress(address);
    }

    GreenThumbManager.saveMembers(list);
    stage.close();
  }

  /**
   * Displays an error alert with the given message.
   *
   * @param msg the error message
   */
  private void showError(String msg)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Invalid Input");
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
  }
}
