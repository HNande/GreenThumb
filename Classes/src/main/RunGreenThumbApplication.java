package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import manager.GreenThumbManager;

import java.io.IOException;

/**
 * The main class to run the GreenThumb application.
 * It demonstrates creating trade offers, adding them to a list,
 * and saving/loading them using GreenThumbManager.
 *
 * @author Nandor Hock
 *
 * @version 02.12.2025
 */
public class RunGreenThumbApplication extends Application
{

  /**
   * The entry point of the application.
   * Demonstrates example usage of TradeOffer and TradeOfferList.
   *
   * @param args ...
   */
  public static void main(String[] args)
  {
    GreenThumbManager manager = new GreenThumbManager();
    TradeOfferList allTradeOffers = new TradeOfferList();
    TaskList allTasks = new TaskList();
    Date today = new Date();
    //Couple of examples
    allTradeOffers.add(new TradeOffer("balls", "Dummy balls text",69 ,new Member()));
    allTradeOffers.add(new TradeOffer("Buvany", "God of War and Thunder with a sprinkle of love",69 ,new Member()));
    allTradeOffers.add(new TradeOffer("Supreme Allan", "The one who grades",69 ,new Member()));
    allTasks.add(new Task("Watching paint dry", 420,2));
    allTasks.add(new Task("Eating KFC", 600,1));
    allTasks.add(new Task("Doing it sexy style", 420,2));
    manager.saveTasks(allTasks);
    launch(args);
  }

  public void start(Stage primaryStage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TaskView.fxml"));
    Scene scene = new Scene(loader.load());
    primaryStage.setTitle("Task View");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
