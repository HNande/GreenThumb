package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import manager.GreenThumbManager;
import model.*;
import utils.ControllerHelper;

import java.io.IOException;
import javafx.event.ActionEvent;

import static utils.ControllerHelper.*;

public class TradeOfferViewController {

  @FXML private TableView<TradeOffer> tradeOfferTable;
  @FXML private TableColumn<TradeOffer, String> name;
  @FXML private TableColumn<TradeOffer, Integer> cost;
  @FXML private TableColumn<TradeOffer, String> description;
  @FXML private TableColumn<TradeOffer, String> proposer;

    @FXML private Button deleteButton;
    @FXML private Button executeButton;
    @FXML private Button addButton;

  @FXML private Button tasks;
  @FXML private Button recordedTasks;
  @FXML private Button tradeOffers;
  @FXML private Button community;
  @FXML private Button members;

  @FXML private TextField nameField;
  @FXML private TextField costField;
  @FXML private TextField descriptionField;
  @FXML private TextField proposerNameField;
  @FXML private TextField proposerLastNameField;

    @FXML private ComboBox<Member> proposerCombo;
    @FXML private ComboBox<Member> payerCombo;

    private Stage stage;
    private TradeOfferList tradeOfferList = GreenThumbManager.getAllTradeOffers();
    private final MemberList memberList = GreenThumbManager.getAllMembers();

  @FXML public void initialize() {
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    description.setCellValueFactory(new PropertyValueFactory<>("description"));
    proposer.setCellValueFactory(cellData -> new SimpleStringProperty(
        cellData.getValue().getProposer().getFirstName() + " " + cellData.getValue().getProposer().getLastName()));

    tradeOfferTable.setEditable(true);
    tradeOfferTable.getItems().addAll(tradeOfferList.getTradeOfferList());

    name.setCellFactory(TextFieldTableCell.forTableColumn());
    cost.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    description.setCellFactory(TextFieldTableCell.forTableColumn());

        proposerCombo.getItems().setAll(memberList.getMemberList());
        payerCombo.getItems().setAll(memberList.getMemberList());

        name.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            String oldValue = event.getOldValue();
            TradeOffer tradeOffer = event.getRowValue();

            if (isNullOrEmpty(newValue)) {
                showErrorMessage("Empty value error", "Name cannot be empty.");
                tradeOffer.setName(oldValue);
            } else if (newValue.trim().length() < 4 || newValue.trim().length() > 64) {
                showErrorMessage("Name length error", "Name must be between 4 and 64 characters.");
                tradeOffer.setName(oldValue);
            } else {
                tradeOffer.setName(newValue.trim());
                GreenThumbManager.saveTradeOffers(tradeOfferList);
            }
            tradeOfferTable.refresh();
        });

        // TradeOfferViewController.java, inside initialize()

// ... (existing code) ...

// --- Setup for the 'cost' column with inline exception handling ---
        cost.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {

            // Override fromString() here to include try-catch
            @Override
            public Integer fromString(String value) {
                if (value == null || value.trim().isEmpty()) {
                    ControllerHelper.showErrorMessage("Invalid or Empty Cost",
                            "Input was left empty. Value reverted.");
                    return null;
                }
                try {
                    return Integer.valueOf(value.trim());
                } catch (NumberFormatException e) {
                    showErrorMessage("Input Error",
                            "Cost must be a whole number. Input failed.");
                    return null;
                }
            }
        }));

        cost.setOnEditCommit(event -> {
            Integer newValue = event.getNewValue();
            Integer oldValue = event.getOldValue();
            TradeOffer tradeOffer = event.getRowValue();

      if (input == null) {
        showErrorMessage("Empty value error message", "Edited value cannot be empty.");
        pass = false;
      } else if (input < 0) {
        showErrorMessage("Wrong number format", "Edited value must be a whole positive number.");
        pass = false;
      }
      if (pass) {
        tradeOffer.setCost(input);
        tradeOfferTable.refresh();
      }
    });

    description.setOnEditCommit(event -> {
      boolean pass = true;
      String input = event.getNewValue();
      TradeOffer tradeOffer = event.getRowValue();

            if (isNullOrEmpty(newValue)) {
                showErrorMessage("Empty value error", "Description cannot be empty.");
                tradeOffer.setDescription(oldValue);
            } else if (newValue.trim().length() > 500) {
                showErrorMessage("Description length error", "Description cannot be more than 500 characters.");
                tradeOffer.setDescription(oldValue);
            } else {
                tradeOffer.setDescription(newValue.trim());
                GreenThumbManager.saveTradeOffers(tradeOfferList);
            }
            tradeOfferTable.refresh();
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

  public void handleTasks() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TaskView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) tasks.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void handleRecordedTasks() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecordedTaskView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) recordedTasks.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void handleTradeOffers() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TradeOfferView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) tradeOffers.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void handleCommunity() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CommunityView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) community.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void handleMembers() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MemberView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) members.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void handleEvents(javafx.event.ActionEvent e) {

    TradeOffer selectedTradeOffer = tradeOfferTable.getSelectionModel().getSelectedItem();

    if (e.getSource() == deleteButton && selectedTradeOffer != null) {
      showConfirmationMessage("Deletion confirmation", "Do you really want to delete: " + selectedTradeOffer.getName() + "?");
      tradeOfferTable.getItems().remove(selectedTradeOffer);
      tradeOfferList.remove(selectedTradeOffer);
      tradeOfferTable.refresh();
      showWarningMessage("Delete successful", "Object has been deletd successfully");
    } else if (e.getSource() == addButton) {
      boolean pass = true;

      String nameText = nameField.getText().trim();
      String costText = costField.getText().trim();
      String descriptionText = descriptionField.getText().trim();
      String proposerName = proposerNameField.getText().trim();
      String proposerLastName = proposerLastNameField.getText().trim();

      Member selectedMember = null;

      for (int i = 0; i < memberList.getMemberList().size(); i++) {
        Member member = memberList.getMemberList().get(i);
        if (member.getFirstName().equals(proposerName) && member.getLastName().equals(proposerLastName)) {
          selectedMember = member;
          break;
        }
      }

      if (isNullOrEmpty(nameText)) {
        showErrorMessage("Empty value error message", "Entered value cannot be empty.");
        pass = false;
      } else if (nameText.trim().length() < 4
          || nameText.trim().length() > 64) {
        showErrorMessage("Name outside bounds", "Name must be between 4 and 64 characters.");
        pass = false;
      } else if (isNullOrEmpty(descriptionText)) {
        showErrorMessage("Empty value error message", "Entered value cannot be empty.");
        pass = false;
      } else if (descriptionText.trim().length() > 500) {
        showErrorMessage("Description outside of bounds", "Description cannot be more than 500 characters.");
        pass = false;
      } else if (isNullOrEmpty(costText)) {
        showErrorMessage("Empty value error message", "Entered value cannot be empty.");
        pass = false;
      } else if (Integer.parseInt(costText) < 0) {
        showErrorMessage("Wrong number format", "Entered value must be a whole positive number.");
        pass = false;
      } else if (ControllerHelper.tradeOfferNameAlreadyExists(
          tradeOfferList.getTradeOfferList(), nameText)) {
        showErrorMessage("Duplicate TradeOffer", "A trade offer with this name already exists.");
        pass = false;
      } else if (selectedMember == null) {
        showErrorMessage("Invalid Proposer", "No member found with that first and last name.");
        pass = false;
      }
      if (pass) {
        TradeOffer tradeOffer = new TradeOffer(nameText, descriptionText,
            Integer.parseInt(costText), selectedMember);
        tradeOfferList.add(tradeOffer);
        tradeOfferTable.getItems().add(tradeOffer);
        GreenThumbManager.saveTradeOffers(tradeOfferList);
      }

      nameField.clear();
      costField.clear();
      descriptionField.clear();
      proposerNameField.clear();
      proposerLastNameField.clear();

      tradeOfferTable.refresh();
    } else if (e.getSource() == executeButton) {
      proposerCombo.setVisible(true);
      proposerCombo.setManaged(true);
      payerCombo.setVisible(true);
      payerCombo.setManaged(true);

      Member payer = payerCombo.getSelectionModel().getSelectedItem();
      Member proposer = proposerCombo.getSelectionModel().getSelectedItem();

      if (payer == proposer) {
        showErrorMessage("The proposer is the same as the payer", "The proposer and the payer can't be the same.");
      }

      TradeOffer tradeOfferExecution = tradeOfferTable.getSelectionModel()
          .getSelectedItem();

      if (tradeOfferExecution == null) {
        showErrorMessage("Invalid Execution", "No trade offer found.");
      }

      if (payer.getPoints() < tradeOfferExecution.getCost()) {
        showErrorMessage("Points missing", "Not enough points for paying.");
      } else {
        tradeOfferExecution.executeTradeOffer(payer, proposer);

      }
      GreenThumbManager.saveMembers(memberList);
      proposerCombo.setVisible(false);
      proposerCombo.setManaged(false);
      payerCombo.setVisible(false);
      payerCombo.setManaged(false);
    }

  }
}
