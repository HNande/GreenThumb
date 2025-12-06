package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    @FXML private TextField proposerNameField;
    @FXML private TextField proposerLastNameField;

    private TradeOfferList tradeOfferList;
    private MemberList memberList;
    private Stage stage;

    private boolean validName = false;
    private boolean validCost = false;
    private boolean validDescription = false;
    private Member selectedProposer = null;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLists(TradeOfferList tradeOfferList, MemberList memberList) {
        this.tradeOfferList = tradeOfferList;
        this.memberList = memberList;
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
        if (!validName) nameField.clear();
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
        if (!validDescription) descriptionField.clear();
    }

    @FXML
    public void handleTradeOfferCost(ActionEvent actionEvent) {
        String input = costField.getText().trim();
        validCost = false;

        if (isNullOrEmpty(input)) {
            ControllerHelper.showErrorMessage("Cost Empty Error", "Cost must not be empty.");
            costField.clear();
            return;
        }

        try {
            int cost = Integer.parseInt(input);
            if (cost < 0) {
                ControllerHelper.showErrorMessage("Cost Format Error","Cost amount must be a non-negative number.");
                costField.clear();
                return;
            }
            validCost = true;
        } catch (NumberFormatException e){
            ControllerHelper.showErrorMessage("Cost Format Error","Cost must be a valid whole number.");
            costField.clear();
        }
    }

    @FXML
    public void handleProposerFields(ActionEvent actionEvent) {
        selectedProposer = null;
        String firstName = proposerNameField.getText().trim();
        String lastName = proposerLastNameField.getText().trim();

        if (isNullOrEmpty(firstName) || isNullOrEmpty(lastName)) {
            ControllerHelper.showErrorMessage("Missing Proposer Data", "Please enter both the First Name and Last Name of the proposer.");
            return;
        }

        for (int i = 0; i < memberList.getMemberList().size(); i++) {
            Member member = memberList.getMemberList().get(i);

            if (member.getFirstName().equals(firstName) &&
                    member.getLastName().equals(lastName)) {
                selectedProposer = member;
                break;
            }
        }

        if (selectedProposer == null) {
            ControllerHelper.showErrorMessage("Invalid Proposer", "No member found with that name.");
        }
    }

    @FXML
    public void handleCancel(ActionEvent actionEvent) {
        nameField.clear();
        costField.clear();
        descriptionField.clear();
        proposerNameField.clear();
        proposerLastNameField.clear();
        stage.close();
    }

    @FXML
    public void handleConfirm(ActionEvent actionEvent) {
        handleTradeOfferName(null);
        handleTradeOfferDescription(null);
        handleTradeOfferCost(null);
        handleProposerFields(null);

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
            ControllerHelper.showErrorMessage("Missing or Invalid Input Error","Please fill in all fields with correct data before confirming.");
        }
    }
}