package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controller for the main application view with multiple tabs.
 * Loads and displays content for each tab during initialization.
 *
 * @author Nandor Hock
 *
 * @version 08.12.2025
 */
public class MainViewController
{
  @FXML private Tab tasksTab;
  @FXML private Tab taskRecordsTab;
  @FXML private Tab tradeOfferTab;
  @FXML private Tab communityTab;
  @FXML private Tab membersTab;

  /**
   * Initializes the main view and loads FXML content for all tabs.
   */
  public void initialize()
  {
    try
    {
      tasksTab.setContent(loadTabContent("/view/TaskView.fxml"));
      taskRecordsTab.setContent(loadTabContent("/view/RecordedTaskView.fxml"));
      tradeOfferTab.setContent(loadTabContent("/view/TradeOfferView.fxml"));
      communityTab.setContent(loadTabContent("/view/CommunityView.fxml"));
      membersTab.setContent(loadTabContent("/view/MemberView.fxml"));
    }
    catch (IOException e)
    {
      System.out.println("Error loading tabs: " + e.getMessage());
    }
  }

  /**
   * Loads the FXML file and returns its root pane.
   *
   * @param fxmlFile path to the FXML file
   * @return AnchorPane root of the loaded UI
   * @throws IOException if the file cannot be loaded
   */
  private AnchorPane loadTabContent(String fxmlFile) throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    return loader.load();
  }
}
