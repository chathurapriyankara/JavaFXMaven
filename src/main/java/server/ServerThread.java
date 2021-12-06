package server;

import javafx.scene.control.TextArea;
import parser.DataProcessor;
import parser.Food;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private Socket socket;
    private DataProcessor dataProcessor;
    private TextArea textArea;

    public ServerThread(Socket s, TextArea textArea) {
        this.socket = s;
        dataProcessor = new DataProcessor();
        this.textArea = textArea;
    }

    public void run() {
        //Try with resources
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            //Read client request
            String request = reader.readLine();
            textArea.appendText("\nThe client is searching for: "+request);
            //Get the requested food object
            ArrayList<Food> foods = dataProcessor.findFoodByName(request);
            //Convert food object to XML
            String xml = dataProcessor.marshallFood(foods);
            //Send the XML to client as the response
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(xml);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
