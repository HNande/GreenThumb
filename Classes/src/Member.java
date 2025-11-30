import java.io.Serializable;

public class Member implements Serializable {
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String email;
  private int points;
  private boolean boost;
  private Address address;
  private int lastRecordTime;
  public Member(String firstName, String lastName, String phoneNumber, String email, int houseNumber, String street) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    address = new Address(houseNumber, street);
    points = 0;
    boost = false;
    lastRecordTime = 0;
  }
  //Default constructor for testing purposes
  public Member(){
    firstName = "Bob";
    lastName = "Green";
    phoneNumber = "+45694200";
    email = "greenbob@cloverville.com";
    address = new Address(42,"Bob's street");
    points = 0;
    boost = false;
    lastRecordTime = 0;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  public String getPhoneNumber() {
    return phoneNumber;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getEmail() {
    return email;
  }
  public void setAddress(Address address) {
    this.address = address;
  }
  public Address getAddress() {
    return address;
  }
  public void setAddressHouse(int houseNumber) {
    address.setHouseNumber(houseNumber);
  }
  public int getAddressHouse() {
    return address.getHouseNumber();
  }
  public void setAddressStreet(String street) {
    address.setStreet(street);
  }
  public String getAddressStreet() {
    return address.getStreet();
  }
  public int getLastRecordTime() {
    return lastRecordTime;
  }
  public void setLastRecordTime(int time) {
    lastRecordTime = time;
  }
  public void setBoost(boolean boost) {
    this.boost = boost;
  }
  public boolean isBoosted() {
    return boost;
  }
  public void setPoints(int points) {
    this.points = points;
  }
  public int getPoints() {
    return points;
  }
  public void convertPoints(){
    Community.getInstance().addCommunityPoints(points);
    points = 0;
  }
  public void timePassed(int time){
    lastRecordTime +=time;
  }
  public void addPoints(int pointAmount){
    points += pointAmount;
  }
  public void removePoints(int pointAmount){
    points -= pointAmount;
  }
  public boolean isEligibleForReset() {
    return points >= 100;  //This value is magical
  }
  public boolean equals(Member obj) {
    return firstName.equals(obj.getFirstName()) && lastName.equals(
        obj.getLastName()) && phoneNumber.equals(obj.getPhoneNumber())
        && email.equals(obj.getEmail()) && points == obj.getPoints()
        && boost == obj.isBoosted() && address.equals(obj.getAddress())
        && lastRecordTime == obj.getLastRecordTime();
  }
  public String toString() {
    String temp = "My name is %S %S, my phone number is: %s, my email is: %s, I live at %S %d, my points is: %d, time since last record: %d ,  am I boosted? %B";
    return String.format(temp, firstName, lastName, phoneNumber, email,
        getAddressStreet(), getAddressHouse(), lastRecordTime, points, boost);
  }
}
