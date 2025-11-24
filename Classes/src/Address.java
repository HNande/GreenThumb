public class Address
{
  private int houseNumber;
  private String Street;

  public Address(int houseNumber, String Street){
    this.houseNumber = houseNumber;
    this.Street = Street;
  }
  public void setHouseNumber(int houseNumber)
  {this.houseNumber = houseNumber;}

  public int getHouseNumber()
  {return houseNumber;}

  public void setStreet(String street)
  {Street = street;}

  public String getStreet()
  {return Street;}
}
