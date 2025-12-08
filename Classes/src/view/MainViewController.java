package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainViewController {
  @FXML private Tab tasksTab;
  @FXML private Tab taskRecordsTab;
  @FXML private Tab tradeOfferTab;
  @FXML private Tab communityTab;
  @FXML private Tab membersTab;
  public void initialize() {
    try {
      // Load each tab's content
      tasksTab.setContent(loadTabContent("/view/TaskView.fxml"));
      taskRecordsTab.setContent(loadTabContent("/view/RecordedTaskView.fxml"));
      tradeOfferTab.setContent(loadTabContent("/view/TradeOfferView.fxml"));
      communityTab.setContent(loadTabContent("/view/CommunityView.fxml"));
      membersTab.setContent(loadTabContent("/view/MemberView.fxml"));
    } catch (IOException e) {
      System.out.println("Error loading tabs: " + e.getMessage());
    }

  }
  private AnchorPane loadTabContent(String fxmlFile) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    return loader.load();
  }
}
