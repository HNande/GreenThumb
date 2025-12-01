import java.io.Serializable;

public class Community implements Serializable {
  private static final Community instance = new Community();
  private int rewardThreshold;
  private String rewardDescription;
  private  int communityPoints;

  private Community() {
    rewardThreshold = 0;
    rewardDescription = "";
    communityPoints = 0;
  }
  // Constructor example
  // model.Community community = model.Community.getInstance();
  // Whenever you want to call a method, use model.Community.getInstance()
  // For example model.Community.getInstance().setRewardThreshold();
  public static Community getInstance() {
    return instance;
  }
  public void setRewardThreshold(int rewardThreshold) {
    this.rewardThreshold = rewardThreshold;
  }
  public int getRewardThreshold() {
    return rewardThreshold;
  }
  public void setRewardDescription(String rewardDescription) {
    this.rewardDescription = rewardDescription;
  }
  public String getRewardDescription() {
    return rewardDescription;
  }
  public void setCommunityPoints(int communityPoints) {
    this.communityPoints = communityPoints;
  }
  public int getCommunityPoints() {
    return communityPoints;
  }
  public void addCommunityPoints(int pointAmount) {
    this.communityPoints += pointAmount;
  }
  public boolean isAboveThresold() {
    return communityPoints >= rewardThreshold;
  }
}
