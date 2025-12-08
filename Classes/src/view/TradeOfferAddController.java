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

public class TradeOfferAddController {

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

    @FXML
    public void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        memberTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLists(TradeOfferList tradeOfferList, MemberList memberList) {
        this.tradeOfferList = tradeOfferList;
        this.memberList = memberList;
        if (memberList != null) {
            memberTable.getItems().setAll(memberList.getMemberList());
        }
    }

    @FXML
    public void handleTradeOfferName(ActionEvent actionEvent) {
        String input = nameField.getText().trim();
        validName = false;

        if (isNullOrEmpty(input)) {
            ControllerHelper.showErrorMessage("Name Empty Error", "Name must not be empty.");
        } else if (input.length() < 4 || input.length() > 64) {
            ControllerHelper.showErrorMessage("Name Length Error", "Name must be between 4 and 64 characters.");
        } else if (tradeOfferNameAlreadyExists(tradeOfferList.getTradeOfferList(), input)) {
            ControllerHelper.showErrorMessage("Duplicate TradeOffer", "A trade offer with this name already exists.");
        } else {
            validName = true;
        }
        if (!validName && actionEvent != null) nameField.clear();
    }

    @FXML
    public void handleTradeOfferDescription(ActionEvent actionEvent) {
        String input = descriptionField.getText().trim();
        validDescription = false;

        if (isNullOrEmpty(input)) {
            ControllerHelper.showErrorMessage("Description Empty Error", "Description must not be empty.");
        } else if (input.length() > 500) {
            ControllerHelper.showErrorMessage("Description Length Error", "Description cannot exceed 500 characters.");
        } else {
            validDescription = true;
        }
        if (!validDescription && actionEvent != null) descriptionField.clear();
    }

    @FXML
    public void handleTradeOfferCost(ActionEvent actionEvent) {
        String input = costField.getText().trim();
        validCost = false;

        if (isNullOrEmpty(input)) {
            ControllerHelper.showErrorMessage("Cost Empty Error", "Cost must not be empty.");
            if (actionEvent != null) costField.clear();
            return;
        }

        try {
            int cost = Integer.parseInt(input);
            if (cost < 0) {
                ControllerHelper.showErrorMessage("Cost Format Error","Cost amount must be a non-negative number.");
                if (actionEvent != null) costField.clear();
                return;
            }
            validCost = true;
        } catch (NumberFormatException e){
            ControllerHelper.showErrorMessage("Cost Format Error","Cost must be a valid whole number.");
            if (actionEvent != null) costField.clear();
        }
    }

    @FXML
    public void handleCancel(ActionEvent actionEvent) {
        nameField.clear();
        costField.clear();
        descriptionField.clear();
        memberTable.getSelectionModel().clearSelection();
        stage.close();
    }

    @FXML
    public void handleConfirm(ActionEvent actionEvent) {
        handleTradeOfferName(null);
        handleTradeOfferDescription(null);
        handleTradeOfferCost(null);

        // NEW: Get selected proposer from TableView
        Member selectedProposer = memberTable.getSelectionModel().getSelectedItem();

        if (selectedProposer == null) {
            ControllerHelper.showErrorMessage("Missing Proposer", "Please select a member from the table to be the proposer.");
            return;
        }

        if (validName && validCost && validDescription && selectedProposer != null) {
            try {
                String name = nameField.getText().trim();
                int cost = Integer.parseInt(costField.getText().trim());
                String description = descriptionField.getText().trim();

                TradeOffer tradeOffer = new TradeOffer(name, description, cost, selectedProposer);
                tradeOfferList.add(tradeOffer);
                GreenThumbManager.saveTradeOffers(tradeOfferList);

                handleCancel(null);
            } catch (Exception e) {
                ControllerHelper.showErrorMessage("Creation Error", "An unexpected error occurred during trade offer creation.");
                e.printStackTrace();
            }
        } else {
            ControllerHelper.showErrorMessage("Missing or Invalid Input Error","Please fill in all fields with correct data and select a proposer before confirming.");
        }
    }
}