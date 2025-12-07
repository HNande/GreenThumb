package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import manager.GreenThumbManager;
import model.Member;
import model.MemberList;
import model.TradeOffer;

import static utils.ControllerHelper.*;

public class TradeOfferExecuteController {

    @FXML private Label offerNameLabel;
    @FXML private ComboBox<Member> payerComboBox;
    @FXML private ComboBox<Member> proposerComboBox;
    @FXML private Button executeTradeButton;

    private Stage dialogStage;
    private TradeOffer selectedOffer;
    private MemberList memberList;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTradeData(TradeOffer offer, MemberList list) {
        this.selectedOffer = offer;
        this.memberList = list;

        offerNameLabel.setText("Trade Offer: " + offer.getName() + " (Cost: " + offer.getCost() + " points)");

        payerComboBox.getItems().setAll(list.getMemberList());
        proposerComboBox.getItems().setAll(list.getMemberList());

        proposerComboBox.getSelectionModel().select(offer.getProposer());
    }

    @FXML
    private void handleExecuteTrade(ActionEvent event) {
        Member payer = payerComboBox.getSelectionModel().getSelectedItem();
        Member proposer = proposerComboBox.getSelectionModel().getSelectedItem();
        int cost = selectedOffer.getCost();

        if (payer == null || proposer == null) {
            showErrorMessage("Invalid Selection", "Please select both a Payer and a Proposer.");
            return;
        }
        if (payer.equals(proposer)) {
            showErrorMessage("Invalid Selection", "The Payer and the Proposer cannot be the same member.");
            return;
        }
        if (payer.getPoints() < cost) {
            showErrorMessage("Points Missing",
                    String.format("The Payer (%s) only has %d points, but the trade costs %d.",
                            payer.getFirstName(), payer.getPoints(), cost));
            return;
        }
        if (showConfirmationMessage("Confirm Execution",
                String.format("Confirm %s will pay %d points to %s for '%s'?",
                        payer.getFirstName(), cost, proposer.getFirstName(), selectedOffer.getName()))) {

            selectedOffer.executeTradeOffer(payer, proposer);
            GreenThumbManager.saveMembers(memberList);

            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        dialogStage.close();
    }
}