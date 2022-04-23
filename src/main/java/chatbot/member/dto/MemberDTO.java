package chatbot.member.dto;

import java.util.Objects;


public class MemberDTO {

    private int mno;
    private String id;
    private String password;
    private String name;
    private String mobileNumber;

    private MemberDTO(int mno, String id, String password, String name, String mobileNumber) {
        this.mno = mno;
        this.id = id;
        this.password = password;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public static MemberDTO joinMember(int mno, String id, String password, String name, String mobileNumber) {
        return new MemberDTO(mno, id, password, name, mobileNumber);
    }

    public String getMobileNumber() {
        return mobileNumber;
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
