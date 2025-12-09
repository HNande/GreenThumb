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
 * @author Nandor Hock & Artem Salatskyi
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

  private MemberViewController memberViewController;
  private CommunityViewController communityViewController;
  private TaskViewController taskViewController;
  private RecordedTaskViewController recordedTaskViewController;
  private TradeOfferViewController tradeOfferViewController;

  /**
   * Initializes the main view and loads FXML content for all tabs.
   */
  public void initialize()
  {
    try
    {
      tasksTab.setContent(loadTaskTabContent());
      tasksTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue && taskViewController != null) {
          taskViewController.refreshView();
        }
      });
      taskRecordsTab.setContent(loadRecordedTabContent());
      taskRecordsTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue && recordedTaskViewController != null) {
          recordedTaskViewController.refreshView();
        }
      });
      tradeOfferTab.setContent(loadTradeOfferTabContent());
      tradeOfferTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue && tradeOfferViewController != null) {
          tradeOfferViewController.refreshView();
        }
      });
      communityTab.setContent(loadCommunityTabContent());
      /*
      communityTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue && communityViewController != null) {
          communityViewController.refreshView();
        }
      });
      */
      membersTab.setContent(loadMemberTabContent());
      membersTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue && memberViewController != null) {
          memberViewController.refreshView();
        }
      });
    }
    catch (IOException e)
    {
      System.out.println("Error loading tabs: " + e.getMessage());
    }
  }
  /**
   * Loads FXML for the Members tab and saves its controller.
   *
   * @return AnchorPane root of the loaded UI
   * @throws IOException if the file cannot be loaded
   */
  private AnchorPane loadMemberTabContent() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MemberView.fxml"));
    AnchorPane root = loader.load();
    memberViewController = loader.getController();
    return root;
  }
  /**
   * Loads FXML for the Tasks tab and saves its controller.
   *
   * @return AnchorPane root of the loaded UI
   * @throws IOException if the file cannot be loaded
   */
  private AnchorPane loadTaskTabContent() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TaskView.fxml"));
    AnchorPane root = loader.load();
    taskViewController = loader.getController();
    return root;
  }
  /**
   * Loads FXML for the Recorded Tasks tab and saves its controller.
   *
   * @return AnchorPane root of the loaded UI
   * @throws IOException if the file cannot be loaded
   */
  private AnchorPane loadRecordedTabContent() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecordedTaskView.fxml"));
    AnchorPane root = loader.load();
    recordedTaskViewController = loader.getController();
    return root;
  }
  /**
   * Loads FXML for the Trade Offers tab and saves its controller.
   *
   * @return AnchorPane root of the loaded UI
   * @throws IOException if the file cannot be loaded
   */
  private AnchorPane loadTradeOfferTabContent() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TradeOfferView.fxml"));
    AnchorPane root = loader.load();
    tradeOfferViewController = loader.getController();
    return root;
  }
  /**
   * Loads FXML for the Community tab and saves its controller.
   *
   * @return AnchorPane root of the loaded UI
   * @throws IOException if the file cannot be loaded
   */
  private AnchorPane loadCommunityTabContent() throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CommunityView.fxml"));
    AnchorPane root = loader.load();
    communityViewController = loader.getController();
    return root;
  }

}