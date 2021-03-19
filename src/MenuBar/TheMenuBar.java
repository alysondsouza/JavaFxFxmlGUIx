package MenuBar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class TheMenuBar extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent fxmlRoot = FXMLLoader.load(getClass().getResource("FXMLmenu.fxml"));
        primaryStage.setScene(new Scene(fxmlRoot, 800, 800));
        primaryStage.show();
    }
}