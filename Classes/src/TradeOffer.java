public class TradeOffer {
  private String name;
  private String description;
  private int cost;
  private Member proposer;

  public TradeOffer(String name, String description, int cost,
      Member proposer) {
    this.name = name;
    this.description = description;
    this.cost = cost;
    this.proposer = proposer;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public Member getProposer() {
    return proposer;
  }

  public void setProposer(Member proposer) {
    this.proposer = proposer;
  }

  public void executeTradeOffer(Member payer, Member reciever) {
    if (proposer.equals(payer)) {
      reciever.addPoints(cost);
      proposer.removePoints(cost);

    } else if (proposer.equals(reciever)) {
      proposer.addPoints(cost);
      payer.removePoints(cost);
    }else{
      System.out.println("Proposer must be one of the members");
    }
  }

  public String toString() {
    return "Trade offer: " + name + "\n Description: " + description
        + "\n Cost: " + cost;
  }

}
