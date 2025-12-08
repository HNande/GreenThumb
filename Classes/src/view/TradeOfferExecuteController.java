package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import manager.GreenThumbManager;
import model.Member;
import model.MemberList;
import model.TradeOffer;

import static utils.ControllerHelper.*;

public class TradeOfferExecuteController {

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

    @FXML
    public void initialize() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        executeTradeButton.setDisable(true);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTradeData(TradeOffer offer, MemberList list) {
        this.selectedOffer = offer;
        this.memberList = list;

        offerNameLabel.setText("Trade Offer: " + offer.getName() + " (Cost: " + offer.getCost() + " points)");

        memberTable.getItems().setAll(list.getMemberList());

        selectedProposer = offer.getProposer();

        memberTable.getSelectionModel().select(selectedProposer);

        updateStatusLabels();
        checkAndEnableExecuteButton();
    }

    private void updateStatusLabels() {
        if (selectedPayer != null) {
            payerStatusLabel.setText(String.format("Payer: %s %s (Points: %d)",
                    selectedPayer.getFirstName(), selectedPayer.getLastName(), selectedPayer.getPoints()));
        } else {
            payerStatusLabel.setText("Payer: (Not Selected)");
        }

        if (selectedProposer != null) {
            proposerStatusLabel.setText(String.format("Receiver (Proposer): %s %s (Points: %d)",
                    selectedProposer.getFirstName(), selectedProposer.getLastName(), selectedProposer.getPoints()));
        } else {
            proposerStatusLabel.setText("Receiver (Proposer): (Not Selected)");
        }
        memberTable.refresh();
    }

    private void checkAndEnableExecuteButton() {
        executeTradeButton.setDisable(selectedPayer == null || selectedProposer == null);
    }

    @FXML
    private void handleAssignPayer(ActionEvent event) {
        Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
        if (selectedMember == null) {
            showErrorMessage("No Member Selected", "Please select a member from the table first.");
            return;
        }

        if (selectedMember.equals(selectedProposer)) {
            showErrorMessage("Invalid Assignment", "This member is already selected as the Receiver/Proposer.");
            return;
        }

        selectedPayer = selectedMember;
        updateStatusLabels();
        checkAndEnableExecuteButton();
    }

    @FXML
    private void handleAssignProposer(ActionEvent event) {
        Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
        if (selectedMember == null) {
            showErrorMessage("No Member Selected", "Please select a member from the table first.");
            return;
        }

        if (selectedMember.equals(selectedPayer)) {
            showErrorMessage("Invalid Assignment", "This member is already selected as the Payer.");
            return;
        }

        selectedProposer = selectedMember;
        updateStatusLabels();
        checkAndEnableExecuteButton();
    }

    @FXML
    private void handleExecuteTrade(ActionEvent event) {

        if (selectedPayer == null || selectedProposer == null) {
            showErrorMessage("Invalid Selection", "Please select both a Payer and a Receiver.");
            return;
        }

        if (selectedPayer.equals(selectedProposer)) {
            showErrorMessage("Internal Error", "The Payer and the Receiver cannot be the same member.");
            return;
        }

        if (selectedPayer.getPoints() < selectedOffer.getCost()) {
            showErrorMessage("Points Missing",
                    String.format("The Payer (%s %s) only has %d points, but the trade costs %d.",
                            selectedPayer.getFirstName(), selectedPayer.getLastName(), selectedPayer.getPoints(), selectedOffer.getCost()));
            return;
        }

        if (showConfirmationMessage("Confirm Execution",
                String.format("Confirm %s %s will pay %d points to %s %s for '%s'?",
                        selectedPayer.getFirstName(), selectedPayer.getLastName(), selectedOffer.getCost(),
                        selectedProposer.getFirstName(), selectedProposer.getLastName(), selectedOffer.getName()))) {

                selectedOffer.executeTradeOffer(selectedPayer, selectedProposer);

            GreenThumbManager.saveMembers(memberList);

            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        dialogStage.close();
    }
}