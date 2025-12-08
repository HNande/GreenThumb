package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import manager.GreenThumbManager;
import model.Member;
import model.MemberList;
import model.TradeOffer;
import model.TradeOfferList;
import utils.ControllerHelper;

import static utils.ControllerHelper.*;

/**
 * Controller for adding new trade offers.
 *
 * Handles user input, validates fields, and creates a new TradeOffer.
 * Also manages selecting a proposer from the member table.
 *
 * @author Sofia Golban
 *
 * @version 08.12.2025
 */
public class TradeOfferAddController
{

  @FXML private Button confirmButton;
  @FXML private Button cancelButton;
  @FXML private TextField nameField;
  @FXML private TextField costField;
  @FXML private TextField descriptionField;

  @FXML private TableView<Member> memberTable;
  @FXML private TableColumn<Member, String> firstNameColumn;
  @FXML private TableColumn<Member, String> lastNameColumn;

  private TradeOfferList tradeOfferList;
  private MemberList memberList;
  private Stage stage;

  private boolean validName = false;
  private boolean validCost = false;
  private boolean validDescription = false;

  /**
   * Initializes the member table columns.
   */
  @FXML
  public void initialize()
  {
    firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

    memberTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      // Listener can be used later if needed
    });
  }

  /**
   * Sets the stage for this dialog.
   *
   * @param stage the Stage object to be set
   */
  public void setStage(Stage stage)
  {
    this.stage = stage;
  }

  /**
   * Sets the trade offer and member lists to be used in this dialog.
   * Fills the member table with the members.
   *
   * @param tradeOfferList the list of trade offers
   * @param memberList the list of members
   */
  public void setLists(TradeOfferList tradeOfferList, MemberList memberList)
  {
    this.tradeOfferList = tradeOfferList;
    this.memberList = memberList;
    if (memberList != null)
    {
      memberTable.getItems().setAll(memberList.getMemberList());
    }
  }

  /**
   * Handles confirming the creation of a trade offer.
   * Validates input fields and creates a new TradeOffer if all data is valid.
   *
   * @param actionEvent the ActionEvent triggered by clicking confirm
   */
  @FXML
  public void handleConfirm(ActionEvent actionEvent)
  {

    Member selectedProposer = memberTable.getSelectionModel().getSelectedItem();

    String nameInput = nameField.getText().trim();
    String descriptionInput = descriptionField.getText().trim();
    String costInput = costField.getText().trim();

    StringBuilder consolidatedError = new StringBuilder();
    int emptyCount = 0;

    if (isNullOrEmpty(nameInput))
    {
      consolidatedError.append("The Name field is empty.\n");
      emptyCount++;
    }
    if (isNullOrEmpty(descriptionInput))
    {
      consolidatedError.append("The Description field is empty.\n");
      emptyCount++;
    }
    if (isNullOrEmpty(costInput))
    {
      consolidatedError.append("The Cost field is empty.\n");
      emptyCount++;
    }
    if (selectedProposer == null)
    {
      consolidatedError.append("Please select a Proposer from the table.\n");
    }

    if (emptyCount >= 2 || selectedProposer == null)
    {
      ControllerHelper.showErrorMessage(
          "Missing Required Fields",
          "Please fill in the missing information:\n" + consolidatedError.toString()
      );
      return;
    }

    if (validName && validCost && validDescription && selectedProposer != null)
    {
      try
      {
        String name = nameInput;
        int cost = Integer.parseInt(costInput);
        String description = descriptionInput;

        TradeOffer tradeOffer = new TradeOffer(name, description, cost, selectedProposer);
        tradeOfferList.add(tradeOffer);
        GreenThumbManager.saveTradeOffers(tradeOfferList);

        handleCancel(null);
      }
      catch (Exception e)
      {
        ControllerHelper.showErrorMessage("Creation Error", "An unexpected error occurred during trade offer creation.");
        e.printStackTrace();
      }
    }
    else
    {
      ControllerHelper.showErrorMessage(
          "Missing or Invalid Input Error",
          "Please check all fields for correct data and ensure a proposer is selected."
      );
    }
  }

  /**
   * Cancels the trade offer creation and closes the dialog.
   * Clears all input fields and selection.
   *
   * @param actionEvent the ActionEvent triggered by clicking cancel
   */
  @FXML
  public void handleCancel(ActionEvent actionEvent)
  {
    nameField.clear();
    costField.clear();
    descriptionField.clear();
    memberTable.getSelectionModel().clearSelection();
    stage.close();
  }
}
