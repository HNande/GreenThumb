package model;

import java.io.Serializable;
import java.util.ArrayList;
public class TradeOfferList implements Serializable {
  private ArrayList<TradeOffer> tradeOfferList;
  public TradeOfferList(TradeOfferList tradeOfferList) {
    this.tradeOfferList = tradeOfferList.getTradeOfferList();
  }
  public TradeOfferList(){
    tradeOfferList = new ArrayList<>();
  }
  public ArrayList<TradeOffer> getTradeOfferList() {
    return new ArrayList<>(tradeOfferList);
  }
  public void add(TradeOffer tradeOffer) {
    tradeOfferList.add(tradeOffer);
  }
  public void remove(TradeOffer tradeOffer){
    tradeOfferList.remove(tradeOffer);
  }
  public void remove(int i){
  tradeOfferList.remove(i);
  }
  public TradeOffer getTradeOfferByIndex(int i){
    return tradeOfferList.get(i);
  }
}

