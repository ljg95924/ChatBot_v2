package chatbot.member.service;

import chatbot.common.InputMessage;
import chatbot.member.dao.MemberDAO;
import chatbot.member.dto.MemberDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Login {
    private List<MemberDTO> memberList = new ArrayList<>();
    private MemberDTO member;
    private BufferedReader br;
    private PrintWriter pw;

    public Login(BufferedReader br, PrintWriter pw) {
        this.br = br;
        this.pw = pw;
    }


    private boolean joinMember() throws IOException {
        String id;
        String password;
        String name;
        String mobile;

        id = inputId();
        password = inputPassword();
        name = inputName();
        mobile = inputPhoneNum();

        member = MemberDTO.joinMember(id, password, name, mobile);
        pw.println("회원가입완료");
        pw.flush();
        InputMessage.input(pw);
        //MemberWriteFile.memberAddFile(member);
        return true;
    }

    private String inputPhoneNum() throws IOException {
        String mobileNumber;
        do {
            pw.println("전화번호 입력");
            pw.flush();
            InputMessage.input(pw);
            mobileNumber = br.readLine();
            logger.info("[Client Send] MobileNumber: {}", mobileNumber);
        } while (!checkNumberValidation(mobileNumber));
        return mobileNumber;
    }

    private String inputName() throws IOException {
        String name;
        do {
            pw.println("이름 입력");
            pw.flush();
            InputMessage.input(pw);
            name = br.readLine();
            logger.info("[Client Send] Name: {}", name);

        } while (!checkNameValidation(name));
        return name;
    }

    private String inputPassword() throws IOException {
        pw.println("비밀번호 입력");
        pw.flush();
        InputMessage.input(pw);
        String password = br.readLine();
        logger.info("[Client Send] Password: {}", password);
        return password;
    }

    private String inputId() throws IOException {
        String id;
        do {
            pw.println("회원가입할 아이디를 입력");
            pw.flush();
            InputMessage.input(pw);
            id = br.readLine();
            logger.info("[Client Send] Id: {}", id);
        } while (!checkId(id));
        return id;
    }


    private boolean tryLogin() throws IOException {
        String id;
        String password;
        do {
            pw.println("아이디를 입력해주세요.");
            pw.flush();
            InputMessage.input(pw);
            id = br.readLine();
            logger.info("[Client Send] Id: {}", id);
            pw.println("비밀번호를 입력해주세요.");
            pw.flush();
            InputMessage.input(pw);
            password = br.readLine();
            logger.info("[Client Send] Password: {}", password);

        } while (!loginMember(id, password));
        return true;
    }

    public MemberDTO getMember() {
        return member;
    }

    private boolean checkId(String id) {
        if (!checkIdValidation(id)) {
            pw.println("숫자와 영어만 입력해주세요. ");
            pw.flush();
            return false;
        }
        loadingMemberList();
        for (MemberDTO member : memberList
        ) {
            if (!member.checkDuplication(id)) {
                pw.append(id).println(" 는 이미 존재하는 ID 입니다.  ");
                pw.flush();
                return false;
            }
        }
        pw.append(id).println(" 는 사용 가능한 ID 입니다.  ");
        pw.flush();
        return true;
    }

    private boolean checkIdValidation(String id) {
        return Pattern.matches("^[a-zA-z0-9]*$", id);
    }

    private boolean checkNumberValidation(String phoneNumber) {
        if (!Pattern.matches("^\\d{3}\\d{3,4}\\d{4}$", phoneNumber)) {
            pw.append("번호 형식 오류 ");
        }
        return Pattern.matches("^\\d{3}\\d{3,4}\\d{4}$", phoneNumber);
    }

    private boolean checkNameValidation(String name) {
        if (!(Pattern.matches("^[가-힣]*$", name) || Pattern.matches("^[a-zA-Z]*$", name))) {
            pw.append("이름 형식 오류 ");
        }
        return Pattern.matches("^[가-힣]*$", name) || Pattern.matches("^[a-zA-Z]*$", name);
    }

    private Boolean loginMember(String id, String password) throws IOException {
        if (id.equals("admin") && password.equals("admin")) {
            AdminService adminService = new AdminService(pw,br);
            adminService.selectMode();
        }
        loadingMemberList();
        for (MemberDTO member : memberList
        ) {
            if (member.getId().equals(id)) {
                if (member.getPassword().equals(password)) {
                    this.member = member;
                    pw.println(id + " 로그인 성공");
                    pw.flush();
                    logger.info("{} 로그인 성공", id);
                    return true;
                }
                pw.println("비밀번호 잘못 입력 ");
                pw.flush();

                return false;
            }
        }
        pw.println("존재하지 않는 아이디 입니다. ");
        pw.flush();
        return false;
    }

    public final MemberDTO selectLoginOrJoin() throws IOException {
        while (true) {
            pw.println("로그인은1번, 회원가입은 2번");
            pw.flush();
            InputMessage.input(pw);
            String str = br.readLine();
            if (str.equals("1")) {
                tryLogin();
                return member;
            }
            if (str.equals("2")) {
                joinMember();
                return member;
            }
            pw.println("다시 입력해주세요.");
        }
    }

    public final void loadingMemberList() {
        memberList = MemberDAO.getInstance().getList();

    }
}
