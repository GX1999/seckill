package com.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public void sendLink() {
        try {
            Socket socket = new Socket("127.0.0.1", 5000);
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println("get the messages from server: " + line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.sendLink();
    }
}
