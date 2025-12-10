package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import manager.GreenThumbManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The main class to run the GreenThumb application.
 * It demonstrates creating trade offers, adding them to a list,
 * and saving/loading them using GreenThumbManager.
 *
 * @author Nandor Hock
 *
 * @version 09.12.2025
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
    Community.setInstance(GreenThumbManager.getCommunity());
    System.out.println(Community.getInstance().getCommunityPoints());
    TaskList allTasks = GreenThumbManager.getAllTasks();
    if(allTasks == null){
      allTasks = new TaskList();
      allTasks.add(new Task("Helping the elderly", 40,2));
      allTasks.add(new Task("Eating veggies", 30,1));
      GreenThumbManager.saveTasks(allTasks);
      System.out.println("Tasklist Created");
    }
    MemberList allMembers = GreenThumbManager.getAllMembers();
    if(allMembers == null){
      allMembers = new MemberList();
      allMembers.add(new Member());
      GreenThumbManager.saveMembers(allMembers);
    }
    TradeOfferList allTradeOffers = GreenThumbManager.getAllTradeOffers();
    if(allTradeOffers == null){
      allTradeOffers = new TradeOfferList();
      allTradeOffers.add(new TradeOffer("Buying carrots", "In need of 500 carrots for my carrot pie project",500 ,new Member()));
      GreenThumbManager.saveTradeOffers(allTradeOffers);
    }
    RecordedTaskList allRecordedTasks = GreenThumbManager.getAllRecordedTasks();
    if(allRecordedTasks == null){
      allRecordedTasks = new RecordedTaskList();
      GreenThumbManager.saveRecordedTasks(allRecordedTasks);
    }
    Date oldDate = GreenThumbManager.getDate();
    if(oldDate != null ){
      LocalDate today = LocalDate.now();
      allMembers.updateTimeForMembers((oldDate.timePeriod(today)));
    } else {
      oldDate = new Date();
      GreenThumbManager.saveDate(oldDate);
    }


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
    primaryStage.setTitle("Green Thumb Application ");
    primaryStage.setScene(scene);
    primaryStage.setOnCloseRequest(event -> {
      System.out.println("Application closing");
      GreenThumbManager.saveCommunity(Community.getInstance());
      GreenThumbManager.saveFileToJson(Community.getInstance());
      GreenThumbManager.saveFileToJson(GreenThumbManager.getAllRecordedTasks());
      GreenThumbManager.saveFileToJson(GreenThumbManager.getAllTradeOffers());
      System.out.println("Files saved to Json");
    });
    primaryStage.show();
  }
}
