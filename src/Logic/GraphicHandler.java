package Logic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class GraphicHandler {

    private Parent root = null;
    private Scene scene = null;

    public static final int MAIN_SCREEN = 0;
    public static final int PLAY_SCREEN = 1;
    public static final int DECK_LIST_SCREEN = 2;
    public static final int DECK_EDIT_SCREEN = 3;
    public static final int CREATE_CARD_SCREEN = 4;

    private void init () {
        if ( scene == null ) {
            try {
                root = FXMLLoader.load( getClass().getResource("StartScreen.fxml") );
            } catch (IOException e) {
                e.printStackTrace();
            }
            scene.setRoot( root );
        }
    }

    public Scene getScene () {
        init();
        return scene;
    }

    public void displayScreen ( int display ) {
        String file_name;
        switch ( display ) {
            case MAIN_SCREEN:
                file_name = "StartScreen.fxml";
                break;
            case PLAY_SCREEN:
                file_name = "PlayScreen.fxml";
                break;
            case DECK_LIST_SCREEN:
                file_name = "DeckManager.fxml";
                break;
            case DECK_EDIT_SCREEN:
                file_name = "DeckScreen.fxml";
                break;
            case CREATE_CARD_SCREEN:
                file_name = "CardCreator.fxml";
                break;
            default:
                System.out.println("Cannot find selected display.");
                return;
        }

        try {
            root = FXMLLoader.load( getClass().getResource(file_name) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.setRoot( root );
    }
}
