package utils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.Member;
import model.Task;
import model.TradeOffer;

import java.util.ArrayList;
import java.util.Optional;

public class ControllerHelper {

  public static boolean isNullOrEmpty(String input) {
    return input == null || input.trim().isEmpty();
  }
  public static boolean taskNameAlreadyExists(ArrayList<Task> list, String string){
    for(int i = 0; i != list.size();i++){
      if (list.get(i).getName().equals(string.trim()))
        return true;
    }
    return false;
  }
  public static boolean tradeOfferNameAlreadyExists(ArrayList<TradeOffer> list, String string){
    for(int i = 0; i != list.size();i++){
      if (list.get(i).getName().equals(string.trim()))
        return true;
    }
    return false;
  }
  public static boolean memberNameAlreadyExists(ArrayList<Member> list, String firstName, String lastName){
    for(int i = 0; i != list.size();i++){
      if (list.get(i).getFirstName().equals(firstName.trim()) && list.get(i).getLastName().equals(lastName.trim()))
        return true;
    }
    return false;
  }
  public static void showErrorMessage(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
  public static boolean showConfirmationMessage(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
  }
  public static void showWarningMessage(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }


}
