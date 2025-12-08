package model;

import java.io.Serializable;

/**
 * The class contains information about the Member.
 *
 * @author Nandor Hock
 *
 * @version 02.12.2025
 */
public class Member implements Serializable
{

  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String email;
  private int points;
  private boolean boost;
  private Address address;
  private int lastRecordTime;

  /**
   * Create new Member object with Member personal info
   *
   * @param firstName of member
   * @param lastName of member
   * @param phoneNumber of member
   * @param email of member
   * @param houseNumber where member live
   * @param street where placed members house
   */
  public Member(String firstName, String lastName, String phoneNumber, String email, int houseNumber, String street)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    address = new Address(houseNumber, street);
    points = 0;
    lastRecordTime = 0;
  }

  /**
   * Create a new Member object with placeholders instead of information
   */
  //Default constructor for testing purposes
  public Member()
  {
    firstName = "Bob";
    lastName = "Green";
    phoneNumber = "+45694200";
    email = "greenbob@cloverville.com";
    address = new Address(42,"Bob's street");
    points = 0;
    lastRecordTime = 0;
  }
  /**
   * Sets the different firstName
   *
   * @param firstName of the member
   */
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  /**
   * Returns firstName of the member
   *
   * @return firstName of the member
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * Sets the different lastName
   *
   * @param lastName of the member
   */
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  /**
   * Returns lastName of the member
   *
   * @return lastName of the member
   */
  public String getLastName()
  {
    return lastName;
  }

  /**
   * Sets the different phoneNumber
   *
   * @param phoneNumber of the member
   */
  public void setPhoneNumber(String phoneNumber)
  {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Returns phoneNumber of the member
   *
   * @return phoneNumber of the member
   */
  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  /**
   * Sets the different email
   *
   * @param email of the member
   */
  public void setEmail(String email)
  {
    this.email = email;
  }

  /**
   * Returns email of the member
   *
   * @return email of the member
   */
  public String getEmail()
  {
    return email;
  }

  /**
   * Sets the different address
   *
   * @param address of the member
   */
  public void setAddress(Address address)
  {
    this.address = address;
  }

  /**
   * Returns address object of the member
   *
   * @return address object of the member
   */
  public Address getAddress()
  {
    return address;
  }

  /**
   * Sets the different houseNumber
   *
   * @param houseNumber of the member
   */
  public void setAddressHouse(int houseNumber)
  {
    address.setHouseNumber(houseNumber);
  }

  /**
   * Returns house number of the member house
   *
   * @return house number of the member house
   */
  public int getAddressHouse()
  {
    return address.getHouseNumber();
  }

  /**
   * Sets the different street
   *
   * @param street of the member
   */
  public void setAddressStreet(String street)
  {
    address.setStreet(street);
  }

  /**
   * Returns steet name of the member house
   *
   * @return steet name of the member house
   */
  public String getAddressStreet()
  {
    return address.getStreet();
  }

  /**
   * Returns the number of time units passed since the last record was updated.
   *
   * @return the time since the last record
   */
  public int getLastRecordTime()
  {
    return lastRecordTime;
  }

  /**
   * Sets the last recorded time value.
   *
   * @param time the new value representing the last recorded time
   */
  public void setLastRecordTime(int time)
  {
    lastRecordTime = time;
  }

  /**
   * Sets the total number of points this member currently has.
   *
   * @param points the new point value
   */
  public void setPoints(int points)
  {
    this.points = points;
  }

  /**
   * Returns the number of points this member currently has.
   *
   * @return the number of points
   */
  public int getPoints()
  {
    return points;
  }

  /**
   * Converts all member points into community points,
   * after conversion, the member's points are reset to zero.
   */
  public void convertPoints()
  {
    Community.getInstance().addCommunityPoints(points);
    points = 0;
  }

  /**
   * Increases the last record time value by the given amount.
   *
   * @param time the amount of time to add
   */
  public void timePassed(long time)
  {
    lastRecordTime += (int) time;
  }

  /**
   * Adds the given number of points to the member's point total.
   *
   * @param pointAmount the number of points to add
   */
  public void addPoints(int pointAmount)
  {
    points += pointAmount;
  }

  /**
   * Removes the given number of points from the member's point total.
   *
   * @param pointAmount the number of points to remove
   */
  public void removePoints(int pointAmount)
  {
    points -= pointAmount;
  }

  /**
   * Checks whether this member is eligible for a points reset.
   * A member is eligible if they have at least 100 points.
   *
   * @return true if points >= 100, false otherwise
   */
  public boolean isEligibleForReset()
  {
    return points >= 100;  //This value is magical
  }

  /**
   * Compares two Member objects for equality based on all fields.
   *
   * @param obj the Member to compare with
   * @return true if all fields match, false otherwise
   */
  public boolean equals(Member obj)
  {
      if (obj == null || getClass() != obj.getClass())
      {
          return false;
      }
      else
      {
          Member member = (Member) obj;

          return firstName.equals(obj.getFirstName()) && lastName.equals(
                  obj.getLastName()) && phoneNumber.equals(obj.getPhoneNumber())
                  && email.equals(obj.getEmail()) && points == obj.getPoints()
                  && lastRecordTime == obj.getLastRecordTime();
      }
  }

  /**
   * Returns a formatted String containing all details about this member.
   *
   * @return a formatted string with member information
   */
  public String toString()
  {
    String temp = "My name is %s %s, my phone number is: %s, my email is: %s, I live at %S %d, my points is: %d, time since last record: %d";
    return String.format(temp, lastName,firstName , phoneNumber, email,
        getAddressStreet(), getAddressHouse(), lastRecordTime, points);
  }

}
