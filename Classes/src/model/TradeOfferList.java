package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The class contains a list of trade offers and provides methods to manage them.
 *
 * @author Nandor Hock and Goofy ahh Buvan
 *
 * @version 02.12.2025
 */
public class TradeOfferList implements Serializable
{
  private ArrayList<TradeOffer> tradeOfferList;

  /**
   * Creates a new TradeOfferList as a copy of another list.
   *
   * @param tradeOfferList another TradeOfferList to copy
   */
  public TradeOfferList(TradeOfferList tradeOfferList)
  {
    this.tradeOfferList = tradeOfferList.getTradeOfferList();
  }

  /**
   * Creates an empty TradeOfferList.
   */
  public TradeOfferList()
  {
    tradeOfferList = new ArrayList<>();
  }

  /**
   * Returns a copy of the list of all trade offers.
   *
   * @return a new list containing all trade offers
   */
  public ArrayList<TradeOffer> getTradeOfferList()
  {
    return new ArrayList<>(tradeOfferList);
  }

  /**
   * Adds a trade offer to the list.
   *
   * @param tradeOffer added to the list
   */
  public void add(TradeOffer tradeOffer)
  {
    tradeOfferList.add(tradeOffer);
  }

  /**
   * Removes a trade offer from the list.
   *
   * @param tradeOffer removed from the list
   */
  public void remove(TradeOffer tradeOffer)
  {
    tradeOfferList.remove(tradeOffer);
  }

  /**
   * Removes a trade offer by its index in the list.
   *
   * @param i index of the trade offer to remove
   */
  public void remove(int i)
  {
  tradeOfferList.remove(i);
  }

  /**
   * Returns a trade offer by its index in the list.
   *
   * @param i index of the trade offer
   * @return the trade offer at the specified index
   */
  public TradeOffer getTradeOfferByIndex(int i)
  {
    return tradeOfferList.get(i);
  }
}

