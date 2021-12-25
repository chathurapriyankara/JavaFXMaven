package server;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class ThreadedServer {
    private static final int PORT = 8585;
    private TextArea textArea;
    private static HashMap<String,Socket> clientsSockets = new HashMap<>();

    public ThreadedServer(TextArea textArea) {
        this.textArea = textArea;
    }

    public void listen() throws IOException {
        ServerSocket listener = new ServerSocket(PORT);
        textArea.appendText("The server started on " + PORT);
        while (true) {
            Socket socket = listener.accept();
            textArea.appendText("\nA client request received : " + socket);
            new ServerThread(socket, textArea).start();
        }
    }

    public static void addClientSocket(String username, Socket socket) {
        clientsSockets.put(username, socket);
    }

    public static Socket getClientSocket(String username) {
        return clientsSockets.get(username);
    }

    public static void removeClientSocket(String username) {
        clientsSockets.remove(username);
    }
}
