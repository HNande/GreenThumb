package view;

import javafx.scene.control.*;
import model.Community;
import utils.ControllerHelper;
import utils.ControllerHelper.*;
import manager.GreenThumbManager;
import javafx.fxml.FXML;
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
  @FXML private Button save;
  @FXML private TextField communityPointsField;
  @FXML private TextField rewardThresholdField;
  @FXML private TextField rewardDescriptionField;
  @FXML private ProgressBar progressBar;

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
    rewardThresholdField.setText(String.valueOf((Community.getInstance().getRewardThreshold())));
    rewardDescriptionField.setText((Community.getInstance().getRewardDescription()));
    double progress = (float) Community.getInstance().getCommunityPoints()/Community.getInstance().getRewardThreshold();
    progressBar.setProgress(progress);
  }

  /**
   * Handles the reset button action.
   *
   * Displays a confirmation dialog, and if confirmed, resets
   * the Community values in both the model and GUI fields.
   *
   * @param actionEvent the event triggered by pressing the reset button
   */
  public void handleReset(javafx.event.ActionEvent actionEvent)
  {
    if (ControllerHelper.showConfirmationMessage(
        "Reset Confirmation",
        "Are you sure you want to reset reward threshold and description??")) {
      rewardThresholdField.clear();
      rewardDescriptionField.clear();
      double progress = (float) Community.getInstance().getCommunityPoints()/Community.getInstance().getRewardThreshold();
      progressBar.setProgress(progress);
      Community.getInstance().setRewardThreshold(0);
      Community.getInstance().setRewardDescription("");
    }
  }
  public void handleSave(javafx.event.ActionEvent actionEvent){
    try{
      int points = Integer.parseInt(communityPointsField.getText().trim());
      if(points < 0){
        ControllerHelper.showErrorMessage("Point value input error", "Please enter whole number.");
        communityPointsField.clear();
        communityPointsField.setText(String.valueOf((Community.getInstance().getCommunityPoints())));
        return;
      }
      Community.getInstance().setCommunityPoints(points);
    }
    catch(NumberFormatException e) {
      ControllerHelper.showErrorMessage("Point value input error", "Please enter a valid number.");
      communityPointsField.clear();
      communityPointsField.setText(String.valueOf((Community.getInstance().getCommunityPoints())));
    }

    try{
    int points = Integer.parseInt(rewardThresholdField.getText().trim());
    if(points < 0){
      ControllerHelper.showErrorMessage("Threshold value input error", "Please enter whole number.");
      rewardThresholdField.clear();
      rewardThresholdField.setText(String.valueOf((Community.getInstance().getRewardThreshold())));
      return;
    }
    Community.getInstance().setRewardThreshold(points);
  }
    catch(NumberFormatException e) {
  ControllerHelper.showErrorMessage("Threshold value input error", "Please enter a valid number.");
  rewardThresholdField.clear();
  rewardThresholdField.setText(String.valueOf((Community.getInstance().getRewardThreshold())));
  }
    if(rewardDescriptionField.getText().trim().length() > 67 ){
      ControllerHelper.showErrorMessage("Keep it short bromazon rainforest",
          "Please adhere to a character count of less than 67, including spaces.");
      rewardDescriptionField.clear();
      rewardDescriptionField.setText((Community.getInstance().getRewardDescription()));
      return;
    }
    Community.getInstance().setRewardDescription(rewardDescriptionField.getText().trim());
    double progress = (float) Community.getInstance().getCommunityPoints()/Community.getInstance().getRewardThreshold();
    progressBar.setProgress(progress);
  }

  public void refreshView()
  {
    communityPointsField.setText(String.valueOf((Community.getInstance().getCommunityPoints())));
    rewardThresholdField.setText(String.valueOf((Community.getInstance().getRewardThreshold())));
    rewardDescriptionField.setText((Community.getInstance().getRewardDescription()));
    double progress = (float) Community.getInstance().getCommunityPoints()/Community.getInstance().getRewardThreshold();
    progressBar.setProgress(progress);
  }

}
