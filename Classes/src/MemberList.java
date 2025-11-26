import java.util.ArrayList;
public class MemberList {
  private final ArrayList<Member> memberList = new ArrayList<>();
  public MemberList() {
  }
  public ArrayList<Member> getTaskList() {
    return new ArrayList<>(memberList);
  }
  public void add(Member member) {
    memberList.add(member);
  }
  public void remove(Member member){
    memberList.remove(member);
  }
}
