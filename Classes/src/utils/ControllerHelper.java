package utils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Member;
import model.MemberList;
import model.Task;
import model.TradeOffer;
import view.TaskRecordingDialogController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
/**
 * @author Nandor Hock
 * @version 05/12/2025
* The class contains utility methods such as input validation,
 * several types of alert messages, and opening new stages.
 */
public class ControllerHelper {
  /**
   * Returns whether parameter is null, or empty without whitespace.
   *
   * @return boolean
   *
   * @param input to be checked
   */
  public static boolean isNullOrEmpty(String input) {
    return input == null || input.trim().isEmpty();
  }
  /**
   * Returns whether the ArrayList<Task> contains string.
   *
   * @return boolean
   *
   * @param list the Arraylist to be checked
   * @param string the String that should be checked in the Arraylist
   */
  public static boolean taskNameAlreadyExists(ArrayList<Task> list, String string){
    String str = string.trim();
    for(int i = 0; i != list.size();i++){
      if (list.get(i).getName().equals(str)){
        return true;
      }
    }
    return false;
  }

  /**
   * Returns whether in the ArrayList<Member> both strings already exists.
   *
   * @return boolean
   *
   * @param list the Arraylist to be checked
   * @param firstName the first String that should be checked in the Arraylist
   * @param lastName the second String that should be checked in the Arraylist
   */
  public static boolean memberNameAlreadyExists(ArrayList<Member> list, String firstName, String lastName){
    String trimmedFirstName = firstName.trim();
    String trimmedLastName = lastName.trim();
    for(int i = 0; i != list.size();i++){
      if (list.get(i).getFirstName().equals(trimmedFirstName) && list.get(i).getLastName().equals(trimmedLastName))
        return true;
    }
    return false;
  }
  /**
   * Returns whether in the list the phone number already exists.
   *
   * @return boolean
   *
   * @param list the MemberList to be checked
   * @param phoneNumber the phone number String that should be checked in the list
   */
  public static boolean memberPhoneAlreadyExists(MemberList list, String phoneNumber)
  {
    String phone = phoneNumber.trim();
    for(int i = 0; i < list.getMemberList().size(); i++)
    {
      if (list.getMemberList().get(i).getPhoneNumber().equals(phone))
        return true;
    }
    return false;
  }

  /**
   * Returns whether the email address already exists in the list.
   *
   * @return boolean
   *
   * @param list the MemberList to be checked
   * @param email the email String that should be checked in the list
   */
  public static boolean memberEmailAlreadyExists(MemberList list, String email)
  {
    String mail = email.trim();

    for(int i = 0; i < list.getMemberList().size(); i++)
    {
      if (list.getMemberList().get(i).getEmail().equalsIgnoreCase(mail)) return true;
    }
    return false;
  }
  /**
   * Shows an alert message of type ERROR.
   *
   * @param title the title of the message
   * @param message the message content
   *
   */
  public static void showErrorMessage(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
  /**
   * Shows an alert message of type CONFIRMATION.
   * @return boolean
   * @param title the title of the message
   * @param message the message content
   *
   */
  public static boolean showConfirmationMessage(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
  }
  /**
   * Shows an alert message of type WARNING.
   *
   * @param title the title of the message
   * @param message the message content
   *
   */
  public static void showWarningMessage(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
