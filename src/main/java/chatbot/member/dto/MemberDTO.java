package chatbot.member.dto;

import chatbot.member.dao.MemberDAO;

import java.util.Objects;


public class MemberDTO {

    private int mno;
    private String id;
    private String password;
    private String name;
    private String mobile;
    private boolean withdraw = false;

    private MemberDTO(String id, String password, String name, String mobile) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
    }

    private MemberDTO(int mno, String id, String password, String name, String mobile) {
        this.mno = mno;
        this.id = id;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
    }

    public static MemberDTO joinMember(String id, String password, String name, String mobile) {
        MemberDAO.getInstance().insert(new MemberDTO(id, password, name, mobile));
        return MemberDAO.getInstance().getMember(id);


    }

    public static MemberDTO loadMember(int mno, String id, String password, String name, String mobile) {
        return new MemberDTO(mno, id, password, name, mobile);
    }

    public int getMno() {
        return mno;
    }

    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public boolean getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(boolean withdraw) {
        this.withdraw = withdraw;
    }

    public boolean checkDuplication(String id) {
        if (this.id.equals(id)) return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberDTO memberDTO = (MemberDTO) o;
        return id.equals(memberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
