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
public class Address implements Serializable
{

  private int houseNumber;
  private String Street;

  /**
   * Creates a new model.Address with the given house number and street name
   *
   * @param houseNumber the number of the house
   * @param Street the name of the street
   */
  public Address(int houseNumber, String Street)
  {
    this.houseNumber = houseNumber;
    this.Street = Street;
  }

  /**
   * Sets a new house number
   *
   * @param houseNumber the new house number
   */
  public void setHouseNumber(int houseNumber)
  {
    this.houseNumber = houseNumber;
  }

  /**
   * Returns the house number
   *
   * @return houseNumber
   */
  public int getHouseNumber()
  {
    return houseNumber;
  }

  /**
   * Sets a new street name
   *
   * @param street the new street name
   */
  public void setStreet(String street)
  {
    Street = street;
  }

  /**
   * Returns the street name
   *
   * @return Street
   */
  public String getStreet()
  {
    return Street;
  }

}
