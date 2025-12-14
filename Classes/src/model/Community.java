package model;
import java.io.Serializable;
/**
 * A class representing the community, containing the community's points, the reward description, and reward threshold.
 *
 * @author Nandor Hock
 *
 * @version 09.12.2025
 */
public class Community implements Serializable
{ 
  private static Community instance;
  private int rewardThreshold;
  private String rewardDescription;
  private int communityPoints;

  /**
   * Creates a new Community object without parameters
   */
  private Community() {
    rewardThreshold = 0;
    rewardDescription = "";
    communityPoints = 0;
  }
  /**
   * Returns the single instance of Community, creating it if necessary.
   *
   * @return the Community instance
   */
  public static Community getInstance() {
    if (instance == null) {
      instance = new Community();
    }
    return instance;
  }
  /**
   * Sets the Community instance from the loaded object.
   * This is called before application startup.
   *
   * @param loadedCommunity the community object loaded from file
   */
  public static void setInstance(Community loadedCommunity) {
    if (loadedCommunity != null) {
      instance = loadedCommunity;
    }
  }

  /**
   * Sets the new reward threshold
   *
   * @param rewardThreshold the new reward threshold
   */
  public void setRewardThreshold(int rewardThreshold)
  {
    this.rewardThreshold = rewardThreshold;
  }

  /**
   * Returns the reward threshold
   *
   * @return the number of points required to receive a reward
   */
  public int getRewardThreshold()
  {
    return rewardThreshold;
  }

  /**
   * Sets the new reward description
   *
   * @param rewardDescription the new reward description
   */
  public void setRewardDescription(String rewardDescription)
  {
    this.rewardDescription = rewardDescription;
  }

  /**
   * Returns the reward description
   *
   * @return the text describing the reward
   */
  public String getRewardDescription()
  {
    return rewardDescription;
  }

  /**
   * Sets the amount of community points.
   *
   * @param communityPoints the new total number of points
   */
  public void setCommunityPoints(int communityPoints)
  {
    this.communityPoints = communityPoints;
  }

  /**
   * Returns number of community points
   *
   * @return the total number of community points
   */
  public int getCommunityPoints()
  {
    return communityPoints;
  }

  /**
   * Add the given amount of points to current value
   *
   * @param pointAmount the number of points we add
   */
  public void addCommunityPoints(int pointAmount)
  {
    this.communityPoints += pointAmount;
  }

  /**
   * Returns whether the community has reached or exceeded
   * the reward threshold.
   *
   * @return true if communityPoints >= rewardThreshold, otherwise false
   */
  public boolean isAboveThreshold()
  {
    return communityPoints >= rewardThreshold;
  }

}