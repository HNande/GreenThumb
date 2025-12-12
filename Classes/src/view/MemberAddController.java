package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import manager.GreenThumbManager;
import model.Address;
import model.Member;
import model.MemberList;
import utils.ControllerHelper;

import java.util.ArrayList;

/**
 * Controller for adding or editing a member.
 * Handles input validation and saving changes to the member list.
 *
 * @author Artem Salatskyi
 *
 * @version 09.12.2025 (Updated)
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

    final String PHONE_REGEX_DK = "^\\+45\\d{8}$";
    if (!phone.matches(PHONE_REGEX_DK))
    {
      showError("Invalid phone number. It must be in the format +45XXXXXXXX without spaces (e.g., +4512345678).");
      return;
    }

    if (street.isEmpty())
    {
      showError("Street field cannot be empty.");
      return;
    }

    if (!street.matches(".*[a-zA-Z].*"))
    {
      showError("Invalid street name. It must contain letters (e.g., Main Street).");
      return;
    }


    int houseNumber;
    try
    {
      houseNumber = Integer.parseInt(houseStr);
      if (houseNumber <= 0)
      {
        showError("House number must be a positive integer.");
        return;
      }
    }
    catch (NumberFormatException e)
    {
      showError("House number must be a valid integer.");
      return;
    }

    Address address = new Address(houseNumber, street);

    if (editingMember == null)
    {

      if (ControllerHelper.memberNameAlreadyExists(
          (ArrayList<Member>) list.getMemberList(), first, last))
      {
        showError("A member with this first name and last name already exists.");
        return;
      }

      if (ControllerHelper.memberEmailAlreadyExists(list, email))
      {
        showError("A member with this email address already exists.");
        return;
      }

      if (ControllerHelper.memberPhoneAlreadyExists(list, phone))
      {
        showError("A member with this phone number already exists.");
        return;
      }

      Member newMember = new Member(first, last, phone, email, houseNumber, street);
      list.add(newMember);
    }
    else
    {

      if (!email.equals(editingMember.getEmail())) {
        for (int i = 0; i < list.getMemberList().size(); i++) {
          Member m = list.getMemberList().get(i);
          if (!m.equals(editingMember) && m.getEmail().equals(email)) {
            showError("A different member already has this email address.");
            return;
          }
        }
      }

      if (!phone.equals(editingMember.getPhoneNumber())) {
        for (int i = 0; i < list.getMemberList().size(); i++) {
          Member m = list.getMemberList().get(i);
          if (!m.equals(editingMember) && m.getPhoneNumber().equals(phone)) {
            showError("A different member already has this phone number.");
            return;
          }
        }
      }

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