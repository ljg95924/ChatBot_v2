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
            conn = DriverManager.getConnection(url, "chatbot", "chatbot");
            ppst = conn.prepareStatement("insert into member values(seq_memberinfo.nextval, ?, ?, ?, ?)");
            ppst.setString(1, data.getName());
            ppst.setString(2, data.getPassword());
            ppst.setString(3, data.getName());
            ppst.setString(4, data.getMobile());
            ppst.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ppst != null) ppst.close();
                if (conn != null) conn.close();
            } catch (Exception e2) {
                e2.getStackTrace();
            }
        }
    }

    public List<MemberDTO> getList() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        Connection conn = null;
        PreparedStatement ppst = null;
        ResultSet rs = null;
        List<MemberDTO> memberList = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "chatbot", "chatbot");
            ppst = conn.prepareStatement("select * from member");
            rs = ppst.executeQuery();

            if (rs.next()) {
                memberList = new ArrayList<>();
                do {
                    memberList.add(
                            MemberDTO.loadMember(rs.getInt("mno"),
                                    rs.getString("id"),
                                    rs.getString("password"),
                                    rs.getString("name"),
                                    rs.getString("mobile"))
                    );
                } while (rs.next());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ppst != null) ppst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return memberList;
    }

    public MemberDTO getMember(String id) {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        MemberDTO member = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "chatbot", "chatbot");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from member where id =" + id);
            member = MemberDTO.loadMember(rs.getInt("mno"),
                    rs.getString("id"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("mobile"));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return member;
    }

    public void update(MemberDTO data) {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        Connection conn = null;
        PreparedStatement ppst = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "chatbot", "chatbot");
            ppst = conn.prepareStatement("update member set withdraw = ?, ");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ppst != null)
                    ppst.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e2) {
                e2.getStackTrace();
            }
        }
    }

}
