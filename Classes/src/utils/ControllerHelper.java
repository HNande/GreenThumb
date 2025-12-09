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
   * Returns whether parameter is a valid Task Type.
   *
   * @return boolean
   *
   * @param input to be checked
   */
  public static boolean isValidTaskType(int input) {
    try {
      if (input > 2 || input < 1 ) {
        showErrorMessage("Point Format error","Point amount must be either 1, or 2.");
        return false;
      }
    } catch (NumberFormatException e) {
      showErrorMessage("Point Format Error","Point amount must be a valid number without decimal points..");
      return false;
    }
    return true;
  }
  /**
   * Returns whether the ArrayList<Task> contains string..
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
   * Returns whether in the ArrayList<TradeOffer> the string already exists.
   *
   * @return boolean
   *
   * @param list the Arraylist to be checked
   * @param string the String that should be checked in the Arraylist
   */
  public static boolean tradeOfferNameAlreadyExists(ArrayList<TradeOffer> list, String string){
    for(int i = 0; i != list.size();i++){
      if (list.get(i).getName().equals(string.trim()))
        return true;
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
    for(int i = 0; i != list.size();i++){
      if (list.get(i).getFirstName().equals(firstName.trim()) && list.get(i).getLastName().equals(lastName.trim()))
        return true;
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
