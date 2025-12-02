package main;

import model.Date;
import model.Member;
import model.TradeOffer;
import model.TradeOfferList;

public class RunGreenThumbApplication
{
  public static void main(String[] args) {
    GreenThumbManager manager = new GreenThumbManager();
    TradeOfferList allTradeOffers = new TradeOfferList();
    Date today = new Date();
    //Couple of examples
    allTradeOffers.add(new TradeOffer("balls", "Dummy balls text",69 ,new Member()));
    allTradeOffers.add(new TradeOffer("Buvany", "God of War and Thunder with a sprinkle of love",69 ,new Member()));
    allTradeOffers.add(new TradeOffer("Supreme Allan", "The one who grades",69 ,new Member()));
    for(int i = 0; i != allTradeOffers.getTradeOfferList().size();i++) {
      System.out.println(allTradeOffers.getTradeOfferByIndex(i).getName());
    }
    manager.saveTradeOffers(allTradeOffers);
    allTradeOffers = manager.getAllTradeOffers();
    for(int i = 0; i != allTradeOffers.getTradeOfferList().size();i++) {
      System.out.println(allTradeOffers.getTradeOfferByIndex(i).getName());
    }







  }
}
