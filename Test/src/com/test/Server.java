package com.test;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void getLink() {
        try {
            ServerSocket s = new ServerSocket(5000);
            while (true) {
                Socket socket = s.accept();
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.println("this is server speaking");
                pw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.getLink();
    }
}
