package view;

import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
  @FXML private TextArea rewardDescriptionArea;
  @FXML private ProgressBar progressBar;
  @FXML private Text progressTitle;

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
    rewardDescriptionArea.setText((Community.getInstance().getRewardDescription()));
    double progress = (float) Community.getInstance().getCommunityPoints()/Community.getInstance().getRewardThreshold();
    progressBar.setProgress(progress);
    if(progress >= 1){
      progressTitle.setText("Progress reached!");
      progressTitle.setFill(Paint.valueOf("green"));
      progressTitle.setFont(Font.font("",20));
      progressBar.setStyle("-fx-accent: #2ecc71;");
    }
    else {
      progressTitle.setText("Progress to next reward");
      progressTitle.setFill(Paint.valueOf("black"));
      progressTitle.setFont(Font.font(16));
      progressBar.setStyle("-fx-accent: #9b59b6;");
    }
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
        "Are you sure you want to reset the community points, as well as the reward threshold and description?")) {
      communityPointsField.setText("0");
      rewardThresholdField.setText("0");
      rewardDescriptionArea.clear();
      double progress = (float) Community.getInstance().getCommunityPoints()/Community.getInstance().getRewardThreshold();
      progressBar.setProgress(progress);
      Community.getInstance().setCommunityPoints(0);
      Community.getInstance().setRewardThreshold(0);
      Community.getInstance().setRewardDescription("");
    }
  }

  /**
   * Saves updated community values from the GUI.
   *
   * Validates input fields and updates points, threshold,
   * and description. Also refreshes the progress bar.
   *
   * @param actionEvent the save button event
   */
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
      ControllerHelper.showErrorMessage("Point value input error", "Please enter whole number.");
      rewardThresholdField.clear();
      rewardThresholdField.setText(String.valueOf((Community.getInstance().getRewardThreshold())));
      return;
    }
    Community.getInstance().setRewardThreshold(points);
  }
    catch(NumberFormatException e) {
  ControllerHelper.showErrorMessage("Point value input error", "Please enter a valid number.");
  rewardThresholdField.clear();
  rewardThresholdField.setText(String.valueOf((Community.getInstance().getRewardThreshold())));
  }
    if(rewardDescriptionArea.getText().trim().length() > 67 ){
      ControllerHelper.showErrorMessage("Keep it short bromazon rainforest",
          "Please adhere to a charactercount of less than 67, including spaces.");
      rewardDescriptionArea.clear();
      rewardDescriptionArea.setText((Community.getInstance().getRewardDescription()));
      return;
    }
    Community.getInstance().setRewardDescription(rewardDescriptionArea.getText().trim());
    double progress = (float) Community.getInstance().getCommunityPoints()/Community.getInstance().getRewardThreshold();
    progressBar.setProgress(progress);
    if(progress >= 1){
      progressTitle.setText("Progress reached!");
      progressTitle.setFill(Paint.valueOf("green"));
      progressTitle.setFont(Font.font("",20));
      progressBar.setStyle("-fx-accent: #2ecc71;");
    }
    else {
      progressTitle.setText("Progress to next reward");
      progressTitle.setFill(Paint.valueOf("black"));
      progressTitle.setFont(Font.font(16));
      progressBar.setStyle("-fx-accent: #9b59b6;");
    }
  }

  /**
   * Refreshes all GUI fields with the newest data from Community.
   *
   * Updates points, threshold, description, progress bar,
   * and progress title styling.
   */
  public void refreshView()
  {
    communityPointsField.setText(String.valueOf((Community.getInstance().getCommunityPoints())));
    rewardThresholdField.setText(String.valueOf((Community.getInstance().getRewardThreshold())));
    rewardDescriptionArea.setText((Community.getInstance().getRewardDescription()));
    double progress = (float) Community.getInstance().getCommunityPoints()/Community.getInstance().getRewardThreshold();
    progressBar.setProgress(progress);
    if(progress >= 1){
      progressTitle.setText("Progress reached!");
      progressTitle.setFill(Paint.valueOf("green"));
      progressTitle.setFont(Font.font(20));
      progressBar.setStyle("-fx-accent: #2ecc71;");
    }
    else {
      progressTitle.setText("Progress to next reward");
      progressTitle.setFill(Paint.valueOf("black"));
      progressTitle.setFont(Font.font(16));
      progressBar.setStyle("-fx-accent: #9b59b6;");
    }
  }
  }

