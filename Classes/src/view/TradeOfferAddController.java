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

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField costField;
    @FXML
    private TextField descriptionField;

    @FXML
    private TableView<Member> memberTable;
    @FXML
    private TableColumn<Member, String> firstNameColumn;
    @FXML
    private TableColumn<Member, String> lastNameColumn;

    private TradeOfferList tradeOfferList;
    private MemberList memberList;
    private Stage stage;

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
    public void handleConfirm(ActionEvent actionEvent) {

        Member selectedProposer = memberTable.getSelectionModel().getSelectedItem();
        String nameInput = nameField.getText().trim();
        String descriptionInput = descriptionField.getText().trim();
        String costInput = costField.getText().trim();

        boolean isValid = true;

        StringBuilder emptyErrors = new StringBuilder();

        if (isNullOrEmpty(nameInput)) {
            emptyErrors.append("- Name field is empty.\n");
            isValid = false;
        }
        if (isNullOrEmpty(descriptionInput)) {
            emptyErrors.append("- Description field is empty.\n");
            isValid = false;
        }
        if (isNullOrEmpty(costInput)) {
            emptyErrors.append("- Cost field is empty.\n");
            isValid = false;
        }
        if (selectedProposer == null) {
            emptyErrors.append("- Please select a Proposer from the table.\n");
            isValid = false;
        }

        if (!emptyErrors.isEmpty()) {
            showErrorMessage("Required Fields Missing", emptyErrors.toString());
            return;
        }

        if (nameInput.length() < 4 || nameInput.length() > 64) {
            showErrorMessage("Incorrect Name Length",
                    "Name must be between 4 and 64 characters.");
            isValid = false;
        }
        if (descriptionInput.length() > 500) {
            showErrorMessage("Incorrect Description Length",
                    "Description cannot be more than 500 characters.");
            isValid = false;
        }

        int cost = -1;

        if (isValid) {
            try {
                cost = Integer.parseInt(costInput);

                if (cost < 0) {
                    showErrorMessage(
                            "Incorrect Cost Value",
                            "Cost must be a non-negative whole number."
                    );
                    isValid = false;
                }

            } catch (NumberFormatException e) {
                showErrorMessage(
                        "Incorrect Cost Format",
                        "Please enter a valid whole number for cost."
                );
                isValid = false;
            }
        }

        if (isValid) {
            try {
                TradeOffer tradeOffer = new TradeOffer(nameInput, descriptionInput, cost, selectedProposer);
                tradeOfferList.add(tradeOffer);
                GreenThumbManager.saveTradeOffers(tradeOfferList);

                handleCancel(null);
            } catch (Exception e) {
                ControllerHelper.showErrorMessage("Creation Error", "An unexpected error occurred during trade offer creation.");
                e.printStackTrace();
            }
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
}