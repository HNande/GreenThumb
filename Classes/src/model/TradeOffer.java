package model;

import java.io.Serializable;

/**
 * The class represents a trade offer with a name, description, cost, and proposer.
 * It provides methods to manage and execute the trade between members.
 *
 * @author Nandor Hock and Goofy ahh Buvan
 *
 * @version 02.12.2025
 */
public class TradeOffer implements Serializable
{
  private String name;
  private String description;
  private int cost;
  private Member proposer;

  /**
   * Creates a new TradeOffer with specified name, description, cost, and proposer.
   *
   * @param name the name of the trade offer
   * @param description description of the offer
   * @param cost points cost of the offer
   * @param proposer the member proposing the trade
   */
  public TradeOffer(String name, String description, int cost, Member proposer)
  {
    this.name = name;
    this.description = description;
    this.cost = cost;
    this.proposer = proposer;
  }

  /**
   * Returns the name of the trade offer.
   *
   * @return trade offer name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets the name of the trade offer.
   *
   * @param name new trade offer name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Returns the description of the trade offer.
   *
   * @return trade offer description
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * Sets the description of the trade offer.
   *
   * @param description new description
   */
  public void setDescription(String description)
  {
    this.description = description;
  }

  /**
   * Returns the cost of the trade offer.
   *
   * @return points cost
   */
  public int getCost()
  {
    return cost;
  }

  /**
   * Sets the cost of the trade offer.
   *
   * @param cost new cost
   */
  public void setCost(int cost)
  {
    this.cost = cost;
  }

  /**
   * Returns the proposer of the trade offer.
   *
   * @return proposer member
   */
  public Member getProposer()
  {
    return proposer;
  }

  /**
   * Sets the proposer of the trade offer.
   *
   * @param proposer new proposer member
   */
  public void setProposer(Member proposer)
  {
    this.proposer = proposer;
  }

  /**
   * Executes the trade offer between two members.
   * Points are transferred depending on the proposer.
   *
   * @param payer member who pays points
   * @param receiver member who receives points
   */
  public void executeTradeOffer(Member payer, Member receiver)
  {
      payer.removePoints(cost);
      receiver.addPoints(cost);
  }

  /**
   * Returns a string representation of the trade offer.
   *
   * @return string with trade offer details
   */
  public String toString() {
    return "Trade offer: " + name + "\n Description: " + description
        + "\n Cost: " + cost;
  }
}
