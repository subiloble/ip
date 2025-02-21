package eureka;

import java.io.IOException;

import eureka.Eureka;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private final Eureka eureka = new Eureka();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/images/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setEureka(eureka);  // inject the Eureka instance
            fxmlLoader.<MainWindow>getController().welcome();  // load greetings
            stage.setTitle("Eureka Chatbot"); // Set the window title here
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
