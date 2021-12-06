package ui;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import server.ThreadedServer;

import java.io.IOException;

public class Controller {
    @FXML
    public TextArea textArea;
    @FXML
    public void startButtonClicked(Event e) {
        service.start();
    }

    Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws IOException {
                    ThreadedServer threadedServer = new ThreadedServer(textArea);
                    threadedServer.listen();
                    return null;
                }
            };
        }
    };
}
