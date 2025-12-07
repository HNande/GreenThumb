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
    TradeOfferList allTradeOffers = new TradeOfferList();
    TaskList allTasks = new TaskList();
    MemberList allMembers = new MemberList();
    RecordedTaskList allRecordedTasks = new RecordedTaskList();
    Community community = Community.getInstance();
    Date today = new Date();

     Member newMember = new Member("John", "Doe", "+45123456", "green@gmail.com",
             67, "Green Street");

     newMember.setPoints(100);
    //Couple of examples
    allMembers.add(new Member());
    allMembers.add(newMember);
    allTradeOffers.add(new TradeOffer("balls", "Dummy balls text",69 ,newMember));
    allTradeOffers.add(new TradeOffer("Buvany", "God of War and Thunder with a sprinkle of love",69 ,new Member()));
    allTradeOffers.add(new TradeOffer("Supreme Allan", "The one who grades",69 ,new Member()));
    allTasks.add(new Task("Watching paint dry", 420,2));
    allTasks.add(new Task("Eating KFC", 600,1));
    allTasks.add(new Task("Doing it sexy style", 420,2));
    allRecordedTasks.add(new Task("Doing it sexy style", 420,2).recordTask(new Member(),4,12,2025,false));
    GreenThumbManager.saveRecordedTasks(allRecordedTasks);
    GreenThumbManager.saveTasks(allTasks);
    GreenThumbManager.saveTradeOffers(allTradeOffers);
    GreenThumbManager.saveMembers(allMembers);
    GreenThumbManager.saveCommunity(Community.getInstance());
    launch(args);
  }

  /**
   * @param primaryStage the primary stage for this application, onto which
   * the application scene can be set.
   * Applications may create other stages, if needed, but they will not be
   * primary stages.
   * @throws IOException
   */
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
    Scene scene = new Scene(loader.load());
    primaryStage.setTitle("Green Thumb ");
    primaryStage.setScene(scene);
    primaryStage.setOnCloseRequest(event -> {
      System.out.println("Application is closing...");
      GreenThumbManager.saveFileToJson(GreenThumbManager.getAllTasks());
      GreenThumbManager.saveFileToJson(GreenThumbManager.getCommunity());
      GreenThumbManager.saveFileToJson(GreenThumbManager.getAllRecordedTasks());
      GreenThumbManager.saveFileToJson(GreenThumbManager.getAllTradeOffers());
      System.out.println("Files saved to Json");
    });
    primaryStage.show();
  }
}