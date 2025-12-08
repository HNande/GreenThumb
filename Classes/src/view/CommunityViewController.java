package view;

import model.Community;
import utils.ControllerHelper;
import utils.ControllerHelper.*;
import manager.GreenThumbManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.application.Application;

import java.awt.event.ActionEvent;

/**
 * Controller class responsible for handling the GUI interactions
 * related to the Community settings view.
 *
 * This class connects the JavaFX UI elements with the Community
 * model and provides methods for displaying and resetting values.
 *
 * @author Buvan
 *
 * @version 08.12.2025
 */
public class CommunityViewController
{

  @FXML private Button reset;
  @FXML private Button save; // is this needed?
  @FXML private TextField communityPointsField;
  @FXML private TextField rewardThresholdField;
  @FXML private TextField rewardDescriptionField;

  private Community community = GreenThumbManager.getCommunity();

  /**
   * Initializes the controller after its root element has been completely loaded.
   *
   * This method populates the text fields with the current data
   * from the Community singleton instance.
   */
  @FXML
  public void initialize()
  {
    communityPointsField.setText(String.valueOf(Community.getInstance().getCommunityPoints()));
    rewardThresholdField.setText(String.valueOf(Community.getInstance().getRewardThreshold()));
    rewardDescriptionField.setText(Community.getInstance().getRewardDescription());
  }

  /**
   * Handles the reset button action.
   *
   * Displays a confirmation dialog, and if confirmed, resets
   * the Community values in both the model and GUI fields.
   *
   * @param actionEvent the event triggered by pressing the reset button
   */
  public void handleReset(ActionEvent actionEvent)
  {
    if (ControllerHelper.showConfirmationMessage(
        "Reset Confirmation",
        "Are you sure you want to reset Community?"))
    {
      communityPointsField.setText("0");
      rewardThresholdField.setText("0");
      rewardDescriptionField.clear();

      Community.getInstance().setCommunityPoints(0);
    }
  }

}
