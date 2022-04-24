package chatbot.member.service;

import chatbot.common.InputMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AdminService {
    PrintWriter pw;
    BufferedReader br;

    public AdminService(PrintWriter pw, BufferedReader br) {
        this.pw = pw;
        this.br = br;
    }

    public void selectMode() throws IOException {
        while (true) {
            pw.println("1. 제휴 업체 추가, 2. 제휴 업체 삭제");
            pw.flush();
            InputMessage.input(pw);
            String mode = br.readLine();
            if (mode.equals("1")) {
                addStore();
                break;
            }
            if (mode.equals("2")) {
                deleteStore();


                break;
            }
            pw.println("다시 입력해주세요.");
        }

    }

    private void addStore() {
        pw.println("추가할 제휴 업체명을 입력해주세요.");
        pw.flush();
        InputMessage.input(pw);
    }

    private void deleteStore() {
        pw.println("삭제할 제휴 업체명을 입력해주세요.");
        pw.flush();
        InputMessage.input(pw);
    }
}
