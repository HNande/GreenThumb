public class Community
{
  private int rewardThreshold;
  private String rewardDescription;
  private static int communityPoints;

  public static void addCommunityPoints(int pointAmount)
  {
    communityPoints += pointAmount;
  }
}
