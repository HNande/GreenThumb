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

  // Новое поле для хранения контроллера вкладки "Участники"
  private MemberViewController memberViewController;

  /**
   * Initializes the main view and loads FXML content for all tabs.
   */
  public void initialize()
  {
    try
    {
      tasksTab.setContent(loadTabContent("/view/TaskView.fxml", null));
      taskRecordsTab.setContent(loadTabContent("/view/RecordedTaskView.fxml", null));
      tradeOfferTab.setContent(loadTabContent("/view/TradeOfferView.fxml", null));
      communityTab.setContent(loadTabContent("/view/CommunityView.fxml", null));
      membersTab.setContent(loadMemberTabContent("/view/MemberView.fxml"));
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
   * Загружает FXML для вкладки Участников и сохраняет его контроллер.
   *
   * @param fxmlFile путь к FXML файлу
   * @return AnchorPane root загруженного UI
   * @throws IOException если файл не может быть загружен
   */
  private AnchorPane loadMemberTabContent(String fxmlFile) throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    AnchorPane root = loader.load();
    memberViewController = loader.getController();
    return root;
  }

  /**
   * Loads the FXML file and returns its root pane. (Обычный метод для остальных вкладок)
   *
   * @param fxmlFile path to the FXML file
   * @return AnchorPane root of the loaded UI
   * @throws IOException if the file cannot be loaded
   */
  private AnchorPane loadTabContent(String fxmlFile, Object unused) throws IOException
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    return loader.load();
  }
}