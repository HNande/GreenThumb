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

    @FXML private ComboBox<Member> proposerCombo;
    @FXML private ComboBox<Member> payerCombo;

    private Stage stage;
    private TradeOfferList tradeOfferList = GreenThumbManager.getAllTradeOffers();
    private final MemberList memberList = GreenThumbManager.getAllMembers();

    @FXML
    public void initialize() {
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

            if (newValue == null) {
                tradeOffer.setCost(oldValue);

            } else if (newValue < 0) {
                ControllerHelper.showErrorMessage("Invalid cost", "Cost must be a whole non-negative number.");
                tradeOffer.setCost(oldValue);

            } else {
                tradeOffer.setCost(newValue);
                GreenThumbManager.saveTradeOffers(tradeOfferList);
            }
            tradeOfferTable.refresh();
        });

        description.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            String oldValue = event.getOldValue();
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

    public void handleAddButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TradeOfferAddView.fxml"));
            Scene scene = new Scene(loader.load());

            TradeOfferAddController dialogController = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Trade Offer");
            dialogStage.initOwner(stage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogController.setStage(dialogStage);
            dialogController.setLists(tradeOfferList, memberList);

            dialogStage.setScene(scene);
            dialogStage.showAndWait();

            tradeOfferList = GreenThumbManager.getAllTradeOffers();
            tradeOfferTable.getItems().clear();
            tradeOfferTable.getItems().addAll(tradeOfferList.getTradeOfferList());

        } catch (IOException ex) {
            ex.printStackTrace();
            showErrorMessage("Dialog Load Error", "Could not load the Add Trade Offer dialog.");
        }
    }

    public void handleEvents(ActionEvent e) {

        TradeOffer selectedTradeOffer = tradeOfferTable.getSelectionModel().getSelectedItem();

        if (e.getSource() == deleteButton && selectedTradeOffer != null) {
            if (showConfirmationMessage("Deletion confirmation", "Do you really want to delete: " + selectedTradeOffer.getName() + "?")) {
                tradeOfferTable.getItems().remove(selectedTradeOffer);
                tradeOfferList.remove(selectedTradeOffer);
                GreenThumbManager.saveTradeOffers(tradeOfferList);

                tradeOfferList = GreenThumbManager.getAllTradeOffers();
                tradeOfferTable.getItems().clear();
                tradeOfferTable.getItems().addAll(tradeOfferList.getTradeOfferList());

                showWarningMessage("Delete successful", "Object has been deleted successfully");
            }
        } else if (e.getSource() == executeButton) {
            if (selectedTradeOffer == null) {
                showErrorMessage("Invalid Execution", "No trade offer found to execute. Please select one.");
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TradeOfferViewExecute.fxml"));
                Scene scene = new Scene(loader.load());

                TradeOfferExecuteController dialogController = loader.getController();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Execute Trade Offer: " + selectedTradeOffer.getName());
                dialogStage.initOwner(tradeOfferTable.getScene().getWindow());
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.setScene(scene);
                dialogController.setDialogStage(dialogStage);

                dialogController.setTradeData(selectedTradeOffer, memberList);

                dialogStage.showAndWait();

                tradeOfferTable.refresh();

            } catch (IOException ex) {
                ex.printStackTrace();
                showErrorMessage("Dialog Load Error", "Could not load the Execute Trade Offer dialog.");
            }
        }
    }

}