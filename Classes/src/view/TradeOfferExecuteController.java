package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import manager.GreenThumbManager;
import model.Member;
import model.MemberList;
import model.TradeOffer;

import static utils.ControllerHelper.*;

/**
 * Controller for executing a trade offer between members.
 *
 * Handles selecting the payer and the proposer, validates constraints,
 * and executes the trade offer when confirmed.
 *
 * @author Sofia Golban
 * @version 08.12.2025
 */
public class TradeOfferExecuteController
{

  @FXML private Label offerNameLabel;
  @FXML private Label payerStatusLabel;
  @FXML private Label proposerStatusLabel;
  @FXML private Button executeTradeButton;

  @FXML private TableView<Member> memberTable;
  @FXML private TableColumn<Member, String> firstNameColumn;
  @FXML private TableColumn<Member, String> lastNameColumn;
  @FXML private TableColumn<Member, Integer> pointsColumn;

  private Stage dialogStage;
  private TradeOffer selectedOffer;
  private MemberList memberList;

  private Member selectedPayer = null;
  private Member selectedProposer = null;

  private Member originalProposer = null;

  /**
   * Initializes the member table and disables the execute button.
   */
  @FXML
  public void initialize()
  {
    firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

    executeTradeButton.setDisable(true);
  }

  /**
   * Sets the stage of this dialog.
   *
   * @param dialogStage the Stage object
   */
  public void setDialogStage(Stage dialogStage)
  {
    this.dialogStage = dialogStage;
  }

  /**
   * Sets the trade offer data and member list for this dialog.
   * Initializes labels and table selection.
   *
   * @param offer the TradeOffer to execute
   * @param list the MemberList containing members
   */
  public void setTradeData(TradeOffer offer, MemberList list)
  {
    this.selectedOffer = offer;
    this.memberList = list;

    this.originalProposer = offer.getProposer();

    offerNameLabel.setText("Trade Offer: " + offer.getName() + " (Cost: " + offer.getCost() + " points)");

    memberTable.getItems().setAll(list.getMemberList());

    selectedProposer = this.originalProposer;

    memberTable.getSelectionModel().select(selectedProposer);

    updateStatusLabels();
    checkAndEnableExecuteButton();
  }

  /**
   * Updates the payer and proposer labels according to the current selection.
   */
  private void updateStatusLabels()
  {
    if (selectedPayer != null)
    {
      payerStatusLabel.setText(String.format("Payer: %s %s (Points: %d)",
          selectedPayer.getFirstName(), selectedPayer.getLastName(), selectedPayer.getPoints()));
    }
    else
    {
      payerStatusLabel.setText("Payer: (Not Selected)");
    }

    if (selectedProposer != null)
    {
      proposerStatusLabel.setText(String.format("Receiver: %s %s (Points: %d)",
          selectedProposer.getFirstName(), selectedProposer.getLastName(), selectedProposer.getPoints()));
    }
    else
    {
      proposerStatusLabel.setText("Receiver: (Not Selected)");
    }
    memberTable.refresh();
  }

  /**
   * Enables or disables the execute button based on the current selection.
   */
  private void checkAndEnableExecuteButton()
  {
    executeTradeButton.setDisable(selectedPayer == null || selectedProposer == null);
  }

  /**
   * Assigns the selected member as the payer.
   * Checks constraints and updates labels accordingly.
   *
   * @param event the ActionEvent triggered by button click
   */
  @FXML
  private void handleAssignPayer(ActionEvent event)
  {
    Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
    if (selectedMember == null)
    {
      showErrorMessage("No Member Selected", "Please select a member from the table first.");
      return;
    }

    if (selectedMember.equals(selectedProposer))
    {
      showErrorMessage("Invalid Assignment", "This member is already selected as the Receiver/Proposer.");
      return;
    }

    if (!selectedMember.equals(originalProposer) && selectedProposer != null && !selectedProposer.equals(originalProposer))
    {
      showErrorMessage("Constraint Violation",
          String.format("The Original Proposer (%s %s) must be either the Payer or the Receiver.",
              originalProposer.getFirstName(), originalProposer.getLastName()));
      return;
    }

    selectedPayer = selectedMember;
    updateStatusLabels();
    checkAndEnableExecuteButton();
  }

  /**
   * Assigns the selected member as the proposer (receiver).
   * Checks constraints and updates labels accordingly.
   *
   * @param event the ActionEvent triggered by button click
   */
  @FXML
  private void handleAssignProposer(ActionEvent event)
  {
    Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
    if (selectedMember == null)
    {
      showErrorMessage("No Member Selected", "Please select a member from the table first.");
      return;
    }

    if (selectedMember.equals(selectedPayer))
    {
      showErrorMessage("Invalid Assignment", "This member is already selected as the Payer.");
      return;
    }

    if (!selectedMember.equals(originalProposer) && selectedPayer != null && !selectedPayer.equals(originalProposer))
    {
      showErrorMessage("Constraint Violation",
          String.format("The Original Proposer (%s %s) must be either the Payer or the Receiver.",
              originalProposer.getFirstName(), originalProposer.getLastName()));
      return;
    }

    selectedProposer = selectedMember;
    updateStatusLabels();
    checkAndEnableExecuteButton();
  }

  /**
   * Executes the trade between the selected payer and proposer.
   * Validates points and confirms the action with the user.
   *
   * @param event the ActionEvent triggered by button click
   */
  @FXML
  private void handleExecuteTrade(ActionEvent event)
  {
    if (selectedPayer == null || selectedProposer == null)
    {
      showErrorMessage("Invalid Selection", "Please select both a Payer and a Receiver.");
      return;
    }

    if (selectedPayer.equals(selectedProposer))
    {
      showErrorMessage("Internal Error", "The Payer and the Receiver cannot be the same member.");
      return;
    }

    if (selectedPayer.getPoints() < selectedOffer.getCost())
    {
      showErrorMessage("Points Missing",
          String.format("The Payer (%s %s) only has %d points, but the trade costs %d.",
              selectedPayer.getFirstName(), selectedPayer.getLastName(), selectedPayer.getPoints(), selectedOffer.getCost()));
      return;
    }

    if (showConfirmationMessage("Confirm Execution",
        String.format("Confirm %s %s will pay %d points to %s %s for '%s'?",
            selectedPayer.getFirstName(), selectedPayer.getLastName(), selectedOffer.getCost(),
            selectedProposer.getFirstName(), selectedProposer.getLastName(), selectedOffer.getName())))
    {
      selectedOffer.executeTradeOffer(selectedPayer, selectedProposer);
      GreenThumbManager.saveMembers(memberList);
      dialogStage.close();
    }
  }

  /**
   * Cancels the trade execution and closes the dialog.
   *
   * @param event the ActionEvent triggered by button click
   */
  @FXML
  private void handleCancel(ActionEvent event)
  {
    dialogStage.close();
  }
}
