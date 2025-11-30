import java.time.LocalDate;
public class RunGreenThumbApplication
{
  public static void main(String[] args) {
    GreenThumbManager manager = new GreenThumbManager();
    TradeOfferList allTradeOffers = manager.getAllTradeOffers(); //Gets the list from file
    Date today = new Date();
    Member bob = new Member();
    allTradeOffers.add(new TradeOffer("balls", "Dummy balls text",69 ,bob));// Example of how you can add new stuff to the lists







  }
}
