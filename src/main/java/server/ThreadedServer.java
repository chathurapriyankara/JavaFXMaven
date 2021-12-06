package server;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ThreadedServer {
    private static final int PORT = 8585;
    private TextArea textArea;

    public ThreadedServer(TextArea textArea) {
        this.textArea = textArea;
    }

    public void listen() throws IOException {
        //Try with resources
        try (ServerSocket listener = new ServerSocket(PORT)) {
            textArea.appendText("The server started on " + PORT);
            while (true) {
                Socket socket = listener.accept();
                textArea.appendText("\nA client request received : " + socket);
                new ServerThread(socket, textArea).start();
            }
        }
    }
}
