package chatbot.common;

import java.io.PrintWriter;

public class InputMessage {
    public static void input(PrintWriter pw) {
        pw.println("input");
        pw.flush();
    }

    public static void disconnect(PrintWriter pw) {
        pw.println("Disconnect");
        pw.flush();
    }
}
