package server;

import javafx.scene.control.TextArea;
import parser.DataProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private TextArea textArea;
    private final static String LOGIN = "LOGIN", SEND="SEND", LEAVE="LEAVE";

    public ServerThread(Socket s, TextArea textArea) {
        this.socket = s;
        this.textArea = textArea;
    }

    public void run() {
        //Try with resources
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //Read client request
            String request = reader.readLine();
            if(request != null) {
                String[] req = request.split(":");
                String command = req[0];
                switch (command) {
                    case LOGIN: {
                        String username = req[1];
                        ThreadedServer.addClientSocket(username, this.socket);
                        sendMessage("OK");
                        break;
                    }
                    case SEND:
                        String destUsername = req[1];
                        String message = req[2];
                        Socket socket = ThreadedServer.getClientSocket(destUsername);
                        sendMessage(socket, message);
                        break;
                    case LEAVE: {
                        String username = req[1];
                        ThreadedServer.removeClientSocket(username);
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println(message);
    }

    public void sendMessage(Socket socket1, String message) throws IOException {
        PrintWriter out = new PrintWriter(socket1.getOutputStream(), true);
        out.println(message);
    }
}
