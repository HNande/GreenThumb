public class Community
{
  private int rewardThreshold;
  private String rewardDescription;
  private static int communityPoints;

  public Community(int rewardThreshold, String rewardDescription, int communityPoints){
    this.rewardThreshold = rewardThreshold;
    this.rewardDescription = rewardDescription;
    //this.communityPoints = communityPoints;??
    Community.communityPoints = communityPoints;
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
  public static void setCommunityPoints(int communityPoints) {
    Community.communityPoints = communityPoints;
  }
  public static int getCommunityPoints() {
    return communityPoints;
  }
  public static void addCommunityPoints(int pointAmount)
  {
    communityPoints += pointAmount;
  }
  public boolean isAboveThresold(){
    return communityPoints >= rewardThreshold;
  }
}
