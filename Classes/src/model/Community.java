package model;

import java.io.Serializable;

/**
 * A class representing an address, containing the house number and street name.
 * Used to store information about the location of a person or object
 *
 * @author Nandor Hock
 *
 * @version 02.12.2025
 */
public class Community implements Serializable
{

  private static final Community instance = new Community();
  private int rewardThreshold;
  private String rewardDescription;
  private  int communityPoints;

  /**
   * Creates a new Community object without parameters
   */
  private Community()
  {
    rewardThreshold = 0;
    rewardDescription = "";
    communityPoints = 0;
  }

  // Constructor example
  // Community community = Community.getInstance();
  // Whenever you want to call a method, use Community.getInstance()
  // For example Community.getInstance().setRewardThreshold();

  /**
   * Returns the single instance of Community
   *
   * @return the shared Community instance
   */
  public static Community getInstance()
  {
    return instance;
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
  public boolean isAboveThresold()
  {
    return communityPoints >= rewardThreshold;
  }

}
