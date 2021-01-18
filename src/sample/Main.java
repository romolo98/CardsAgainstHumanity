package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        primaryStage.setTitle("Cards Against Humanity");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
        //DBConnector.getInstance().connect();
        //DBConnector.getInstance().prova();
    }

    public static void main(String[] args) throws SQLException {
        launch(args);

    }
}
