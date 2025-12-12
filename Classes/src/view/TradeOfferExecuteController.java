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

import java.util.Optional;

import static utils.ControllerHelper.*;

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
    private Member selectedReceiver = null;

    private Member originalProposer = null;

    private boolean tradeExecuted = false;

    @FXML
    public void initialize()
    {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        executeTradeButton.setDisable(true);
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public void setTradeData(TradeOffer offer, MemberList list)
    {
        this.selectedOffer = offer;

        this.memberList = GreenThumbManager.getAllMembers();

        Member proposerFromOffer = offer.getProposer();

        this.originalProposer = this.memberList.getMemberList().stream()
                .filter(m -> m.equals(proposerFromOffer))
                .findFirst()
                .orElse(proposerFromOffer);

        offer.setProposer(this.originalProposer);

        this.selectedReceiver = originalProposer;
        this.selectedPayer = null;

        offerNameLabel.setText("Trade Offer: " + offer.getName() +
                " (Cost: " + offer.getCost() + " points)");

        memberTable.getItems().setAll(memberList.getMemberList());
        memberTable.getSelectionModel().select(originalProposer);

        updateStatusLabels();
        updateExecuteButton();
    }

    public boolean isTradeExecuted()
    {
        return tradeExecuted;
    }

    private Member getCanonicalMember(Member m) {
        if (m == null) {
            return null;
        }

        Optional<Member> canonicalMember = this.memberList.getMemberList().stream()
                .filter(member -> member.equals(m))
                .findFirst();

        return canonicalMember.orElse(m);
    }

    private void updateStatusLabels()
    {
        payerStatusLabel.setText(
                (selectedPayer == null)
                        ? "Payer: (Not Selected)"
                        : String.format("Payer: %s %s (Points: %d)",
                        selectedPayer.getFirstName(),
                        selectedPayer.getLastName(),
                        selectedPayer.getPoints())
        );

        proposerStatusLabel.setText(
                (selectedReceiver == null)
                        ? "Receiver: (Not Selected)"
                        : String.format("Receiver: %s %s (Points: %d)",
                        selectedReceiver.getFirstName(),
                        selectedReceiver.getLastName(),
                        selectedReceiver.getPoints())
        );
    }

    private void updateExecuteButton()
    {
        executeTradeButton.setDisable(selectedPayer == null || selectedReceiver == null);
    }

    private boolean violatesRule(Member payerCandidate, Member receiverCandidate)
    {
        return !(originalProposer.equals(payerCandidate) ||
                originalProposer.equals(receiverCandidate));
    }

    @FXML
    private void handleAssignPayer(ActionEvent event)
    {
        Member m = memberTable.getSelectionModel().getSelectedItem();
        if (m == null)
        {
            showErrorMessage("No Member Selected", "Please select a member first.");
            return;
        }

        Member canonicalM = getCanonicalMember(m);

        Member newPayer = canonicalM;
        Member newReceiver = selectedReceiver;

        if (selectedReceiver != null && canonicalM.equals(selectedReceiver))
        {
            newReceiver = selectedPayer;

            newReceiver = getCanonicalMember(newReceiver);
        }

        if (violatesRule(newPayer, newReceiver))
        {
            showErrorMessage("Constraint Violation",
                    String.format("The Original Proposer (%s %s) must be either the Payer or the Receiver.",
                            originalProposer.getFirstName(), originalProposer.getLastName()));
            return;
        }

        if (newPayer != null && newReceiver != null && newPayer.equals(newReceiver)) {
            showErrorMessage("Invalid Assignment", "Payer and Receiver cannot be the same person.");
            return;
        }

        selectedPayer = getCanonicalMember(newPayer);
        selectedReceiver = getCanonicalMember(newReceiver);

        updateStatusLabels();
        updateExecuteButton();
    }

    @FXML
    private void handleAssignReceiver(ActionEvent event)
    {
        Member m = memberTable.getSelectionModel().getSelectedItem();
        if (m == null)
        {
            showErrorMessage("No Member Selected", "Please select a member first.");
            return;
        }

        Member canonicalM = getCanonicalMember(m);

        Member newReceiver = canonicalM;
        Member newPayer = selectedPayer;

        if (selectedPayer != null && canonicalM.equals(selectedPayer))
        {
            newPayer = selectedReceiver;

            newPayer = getCanonicalMember(newPayer);
        }

        if (violatesRule(newPayer, newReceiver))
        {
            showErrorMessage("Constraint Violation",
                    String.format("The Original Proposer (%s %s) must be either the Payer or the Receiver.",
                            originalProposer.getFirstName(), originalProposer.getLastName()));
            return;
        }

        if (newPayer != null && newReceiver != null && newPayer.equals(newReceiver)) {
            showErrorMessage("Invalid Assignment", "Payer and Receiver cannot be the same person.");
            return;
        }

        selectedReceiver = getCanonicalMember(newReceiver);
        selectedPayer = getCanonicalMember(newPayer);

        updateStatusLabels();
        updateExecuteButton();
    }

    @FXML
    private void handleExecuteTrade(ActionEvent event)
    {
        if (selectedPayer == null || selectedReceiver == null)
        {
            showErrorMessage("Invalid Selection", "You must assign both Payer and Receiver.");
            return;
        }

        if (selectedPayer.equals(selectedReceiver))
        {
            showErrorMessage("Invalid Pair", "Payer and Receiver cannot be the same person.");
            return;
        }

        if (selectedPayer.getPoints() < selectedOffer.getCost())
        {
            showErrorMessage("Not Enough Points",
                    String.format("The payer (%s %s) only has %d points.",
                            selectedPayer.getFirstName(), selectedPayer.getLastName(),
                            selectedPayer.getPoints()));
            return;
        }

        if (showConfirmationMessage("Confirm Trade",
                String.format("Confirm %s %s will pay %d points to %s %s?",
                        selectedPayer.getFirstName(), selectedPayer.getLastName(),
                        selectedOffer.getCost(),
                        selectedReceiver.getFirstName(), selectedReceiver.getLastName())))
        {
            selectedOffer.executeTradeOffer(selectedPayer, selectedReceiver);
            GreenThumbManager.saveMembers(memberList);

            tradeExecuted = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event)
    {
        dialogStage.close();
    }
}