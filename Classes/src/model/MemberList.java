package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The class contains an array of all members and information about them.
 *
 * @author Nandor Hock
 *
 * @version 02.12.2025
 */
public class MemberList implements Serializable
{

  private final ArrayList<Member> memberList = new ArrayList<>();

  /**
   * Creates an empty MemberList.
   */
  public MemberList()
  {
  }
  public void updateTimeForMembers(long time){
    for (Member member : memberList) {
      member.timePassed(time);
    }

  }

  /**
   * Returns a copy of the list of all members.
   *
   * @return a new list containing all members
   */
  public ArrayList<Member> getMemberList()
  {
    return new ArrayList<>(memberList);
  }

  /**
   * Add the new member to the list
   *
   * @param member added to list of all members
   */
  public void add(Member member)
  {
    memberList.add(member);
  }

  /**
   * Remove the member from the list
   *
   * @param member removed from the list of members
   */
  public void remove(Member member){
    memberList.remove(member);
  }
}
