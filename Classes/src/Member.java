public class Member
{
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String email;
  private int points;
  private boolean boost;
  private Address address;
  private Date lastRecordTime;
  public Member(String firstName, String lastName, String phoneNumber, String email ,int houseNumber, String street)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    address = new Address(houseNumber,street);
    points = 0;
    boost = false;
    lastRecordTime = new Date();
  }
  public void setFirstName(String firstName)
  {this.firstName = firstName;}

  public String getFirstName()
  {return firstName;}

  public void setLastName(String lastName)
  {this.lastName = lastName;}

  public String getLastName()
  {return lastName;}

  public void setPhoneNumber(String phoneNumber)
  {this.phoneNumber = phoneNumber;}

  public String getPhoneNumber()
  {return phoneNumber;}

  public void setEmail(String email)
  {this.email = email;}

  public String getEmail()
  {return email;}

  public void setAddress(Address address)
  {this.address = address;}

  public Address getAddress()
  {return address;}

  public void setAddressHouse(int houseNumber)
  {address.setHouseNumber(houseNumber);}

  public int getAddressHouse()
  {return address.getHouseNumber();}

  public void setAddressStreet(String street)
  {address.setStreet(street);}

  public String getAddressStreet()
  {return address.getStreet();}

  public Date getLastRecordTime()
  {return lastRecordTime;}

  public void setLastRecordTime(){}
  public void setBoost(boolean boost)
  {this.boost = boost;}

  public boolean isBoosted()
  {return boost;}

  public void setPoints(int points)
  {this.points = points;}

  public int getPoints()
  {return points;}

  public boolean isEligibleForReset()
  {return points >= 100;}

 public boolean equals(Member obj)
  {
    return
        firstName.equals(obj.getFirstName()) &&
        lastName.equals(obj.getLastName()) &&
        phoneNumber.equals(obj.getPhoneNumber()) &&
        email.equals(obj.getEmail()) &&
        points == obj.getPoints()  &&
        boost == obj.isBoosted() &&
        address.equals(obj.getAddress());
        //lastRecordTime.equals(obj.getDate());
  }
}
