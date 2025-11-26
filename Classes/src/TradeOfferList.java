import java.util.ArrayList;
public class TradeOfferList {
  private final ArrayList<TradeOffer> tradeOfferList = new ArrayList<>();
  public TradeOfferList() {
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
}

