package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The class holds information related to dates, containing day, month and year.
 * Used to store information about dates.
 *
 * @author Nandor Hock
 *
 * @version 02.12.2025
 */
public class Date implements Serializable
{

  private int day;
  private int month;
  private int year;

  /**
   * Creates new Date object with current moment information.
   */
  public Date()
  {
    LocalDate localDate = LocalDate.now();
    day = localDate.getDayOfMonth();
    month = localDate.getMonthValue();
    year = localDate.getYear();
  }

  /**
   * Creates a new date object using the given date.
   *
   * @param day current day in month
   * @param month current month
   * @param year current year
   */
  public Date(int day, int month, int year)
  {
    this.day = day;
    this.month = month;
    this.year = year;
  }

  /**
   * Creates a new Date object representing today's date.
   *
   * @param date object which from which we take information
   */
  public Date(Date date)
  {
    day = date.getDay();
    month = date.getMonth();
    year = date.getYear();
  }

  /**
   * Returns the date
   *
   * @return the new date object with info from current date
   */
  public Date today()
  {
    return new Date(day,month,year);
  }

  /**
   * Sets the new day
   *
   * @param day the new day
   */
  public void setDay(int day)
  {
    this.day = day;
  }

  /**
   * Returns the day.
   *
   * @return the day from this date
   */
  public int getDay()
  {
    return day;
  }

  /**
   * Sets the new month
   *
   * @param month the new month
   */
  public void setMonth(int month)
  {
    this.month = month;
  }

  /**
   * Returns the month.
   *
   * @return the month from this date
   */
  public int getMonth()
  {
    return month;
  }

  /**
   * Sets the new year
   *
   * @param year the new year
   */
  public void setYear(int year)
  {
    this.year = year;
  }

  /**
   * Returns the year.
   *
   * @return the year from this date
   */
  public int getYear()
  {
    return year;
  }

}
