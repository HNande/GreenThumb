import java.io.Serializable;
import java.time.LocalDate;
public class Date implements Serializable {
  private int day;
  private int month;
  private int year;
  public Date(){
    LocalDate localDate = LocalDate.now();
    day = localDate.getDayOfMonth();
    month = localDate.getMonthValue();
    year = localDate.getYear();
  }
  public Date(int day, int month, int year) {
    this.day = day;
    this.month = month;
    this.year = year;
  }
  public Date(Date date) {
    day = date.getDay();
    month = date.getMonth();
    year = date.getYear();
  }
  public Date today(){
    return new Date(day,month,year);
  }
  public void setDay(int day) {
    this.day = day;
  }
  public int getDay() {
    return day;
  }
  public void setMonth(int month) {
    this.month = month;
  }
  public int getMonth() {
    return month;
  }
  public void setYear(int year) {
    this.year = year;
  }
  public int getYear() {
    return year;
  }
}
