package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import manager.GreenThumbManager;
import model.Task;
import model.TaskList;

import java.io.IOException;

/**
 *
 *
 * @author Artem Salatskyi
 *
 * @version 03.12.2025
 */
public class RecordedTaskViewController
{

  @FXML private TableView<Task> taskTable;
  @FXML private TableColumn<Task, String> taskName;
  @FXML private TableColumn<Task, Integer> pointAmount;
  @FXML private TableColumn<Task, Integer> taskType;
  @FXML private TableColumn<Task, Integer> totalCount;

  @FXML private Button task;
  @FXML private Button recorderTasks;
  @FXML private Button tradeOffers;
  @FXML private Button communit;
  @FXML private Button members;

  @FXML private Button taskButton;

  @FXML
  public void initialize()
  {

    taskName.setCellValueFactory(new PropertyValueFactory<>("name"));
    pointAmount.setCellValueFactory(new PropertyValueFactory<>("pointAmount"));
    taskType.setCellValueFactory(new PropertyValueFactory<>("taskType"));
    totalCount.setCellValueFactory(new PropertyValueFactory<>("totalCount"));
    GreenThumbManager manager = new GreenThumbManager();
    TaskList taskList = manager.getAllTasks();

    taskTable.getItems().addAll(taskList.getTaskList());

  }

  @FXML
  private void handTask()
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TaskView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) taskButton.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  public void handRecorderTasks()
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecordedTaskView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) taskButton.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  public void handTradeOffers()
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TradeOfferView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) taskButton.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  public void handleCommunit()
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/viev/CommunityView"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) taskButton.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  private void handMembers()
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MemberView.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = (Stage) taskButton.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

}
