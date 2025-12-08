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

public class CommunityViewController {

  @FXML private Button reset;
  @FXML private Button save; // is ts needed?
  @FXML private TextField communityPointsField;
  @FXML private TextField rewardThresholdField;
  @FXML private TextField rewardDescriptionField;

  private Community community = GreenThumbManager.getCommunity();

  @FXML
  public void initialize(){
    communityPointsField.setText(String.valueOf(Community.getInstance().getCommunityPoints()));
    rewardThresholdField.setText(String.valueOf(Community.getInstance().getRewardThreshold()));
    rewardDescriptionField.setText(Community.getInstance().getRewardDescription());


  }
  public void handleReset(ActionEvent actionEvent){
    if(ControllerHelper.showConfirmationMessage("Reset Confirmation", "Are you sure you want to reset Community?")) {
      communityPointsField.setText("0");
      rewardThresholdField.setText("0");
      rewardDescriptionField.clear();
    Community.getInstance().setCommunityPoints(0);

    }
  }

}
