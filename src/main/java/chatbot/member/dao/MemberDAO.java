package chatbot.member.dao;

import chatbot.member.dto.MemberDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private static MemberDAO instance = new MemberDAO();

    private MemberDAO() {
    }

    public static MemberDAO getInstance() {
        return instance;
    }

    public void insert(MemberDTO data) {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        Connection conn = null;
        PreparedStatement ppst = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "bit", "bit");
            ppst = conn.prepareStatement("insert into member values(seq_memberinfo.nextval, ?, ?, ?, ?)");
            ppst.setString(1, data.getName());
            ppst.setString(2, data.getPassword());
            ppst.setString(3, data.getName());
            ppst.setString(4, data.getMobileNumber());
            ppst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<MemberDTO> getList() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        Connection conn = null;
        PreparedStatement ppst = null;
        ResultSet rs = null;
        List<MemberDTO> list = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "bit", "bit");
            ppst = conn.prepareStatement("select * from member");
            rs = ppst.executeQuery();

            if(rs.next()){
                list = new ArrayList<>();
                do{
                    list.add(
                    MemberDTO.joinMember(rs.getInt("mno"),
                            rs.getString("id"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("mobile"))
                    );
                }while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

}
