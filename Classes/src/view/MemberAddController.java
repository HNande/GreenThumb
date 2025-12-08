package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import manager.GreenThumbManager;
import model.Address;
import model.Member;
import model.MemberList;

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

  public void setStage(Stage stage)
  {
    this.stage = stage;
  }

  public void setList(MemberList list)
  {
    this.list = list;
  }

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

  @FXML
  private void handleCancel()
  {
    stage.close();
  }

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

    if (!email.contains("@") || email.length() < 5)
    {
      showError("Invalid email format.");
      return;
    }

    if (!phone.matches("[0-9 +()-]{7,20}"))
    {
      showError("Invalid phone number.");
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

  private void showError(String msg)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Invalid Input");
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
  }
}
