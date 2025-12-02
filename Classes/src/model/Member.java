package model;

import java.io.Serializable;

/**
 *
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
   *
   * @param firstName
   * @param lastName
   * @param phoneNumber
   * @param email
   * @param houseNumber
   * @param street
   */
  public Member(String firstName, String lastName, String phoneNumber, String email, int houseNumber, String street)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    address = new Address(houseNumber, street);
    points = 0;
    boost = false;
    lastRecordTime = 0;
  }

  /**
   *
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
    boost = false;
    lastRecordTime = 0;
  }

  /**
   *
   * @param firstName
   */
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  /**
   *
   * @return
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   *
   * @param lastName
   */
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  /**
   *
   * @return
   */
  public String getLastName()
  {
    return lastName;
  }

  /**
   *
   * @param phoneNumber
   */
  public void setPhoneNumber(String phoneNumber)
  {
    this.phoneNumber = phoneNumber;
  }

  /**
   *
   * @return
   */
  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  /**
   *
   * @param email
   */
  public void setEmail(String email)
  {
    this.email = email;
  }

  /**
   *
   * @return
   */
  public String getEmail()
  {
    return email;
  }

  /**
   *
   * @param address
   */
  public void setAddress(Address address)
  {
    this.address = address;
  }

  /**
   *
   * @return
   */
  public Address getAddress()
  {
    return address;
  }

  /**
   *
   * @param houseNumber
   */
  public void setAddressHouse(int houseNumber)
  {
    address.setHouseNumber(houseNumber);
  }

  /**
   *
   * @return
   */
  public int getAddressHouse()
  {
    return address.getHouseNumber();
  }

  /**
   *
   * @param street
   */
  public void setAddressStreet(String street)
  {
    address.setStreet(street);
  }

  /**
   *
   * @return
   */
  public String getAddressStreet()
  {
    return address.getStreet();
  }

  /**
   *
   * @return
   */
  public int getLastRecordTime()
  {
    return lastRecordTime;
  }

  /**
   *
   * @param time
   */
  public void setLastRecordTime(int time)
  {
    lastRecordTime = time;
  }

  /**
   *
   * @param boost
   */
  public void setBoost(boolean boost)
  {
    this.boost = boost;
  }

  /**
   *
   * @return
   */
  public boolean isBoosted()
  {
    return boost;
  }

  /**
   *
   * @param points
   */
  public void setPoints(int points)
  {
    this.points = points;
  }

  /**
   *
   * @return
   */
  public int getPoints()
  {
    return points;
  }

  /**
   *
   */
  public void convertPoints()
  {
    Community.getInstance().addCommunityPoints(points);
    points = 0;
  }

  /**
   *
   * @param time
   */
  public void timePassed(int time)
  {
    lastRecordTime +=time;
  }

  /**
   *
   * @param pointAmount
   */
  public void addPoints(int pointAmount)
  {
    points += pointAmount;
  }

  /**
   *
   * @param pointAmount
   */
  public void removePoints(int pointAmount)
  {
    points -= pointAmount;
  }

  /**
   *
   * @return
   */
  public boolean isEligibleForReset()
  {
    return points >= 100;  //This value is magical
  }

  /**
   *
   * @param obj
   * @return
   */
  public boolean equals(Member obj)
  {
    return firstName.equals(obj.getFirstName()) && lastName.equals(
        obj.getLastName()) && phoneNumber.equals(obj.getPhoneNumber())
        && email.equals(obj.getEmail()) && points == obj.getPoints()
        && boost == obj.isBoosted() && address.equals(obj.getAddress())
        && lastRecordTime == obj.getLastRecordTime();
  }

  /**
   *
   * @return
   */
  public String toString()
  {
    String temp = "My name is %S %S, my phone number is: %s, my email is: %s, I live at %S %d, my points is: %d, time since last record: %d, am I boosted? %B";
    return String.format(temp, lastName,firstName , phoneNumber, email,
        getAddressStreet(), getAddressHouse(), lastRecordTime, points, boost);
  }

}
