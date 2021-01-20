package Logic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class GraphicHandler {
    
    private static Scene scene = null;

    public static final int MAIN_SCREEN = 0;
    public static final int PLAY_SCREEN = 1;
    public static final int DECK_LIST_SCREEN = 2;
    public static final int DECK_EDIT_SCREEN = 3;
    public static final int CREATE_CARD_SCREEN = 4;

    public static final int NO_STREAM = 0;
    public static final int OPEN_STREAM = 1;

    private void init () {
        if ( scene == null ) {
            Parent root;
            try {
                root = FXMLLoader.load( getClass().getResource("StartScreen.fxml") );
                scene.setRoot( root );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Scene getScene () {
        init();
        return scene;
    }

    public static void displayScreen (int display, int stream ) {
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
            Parent root = null;
            if ( stream == OPEN_STREAM ) {
                FXMLLoader loader = new FXMLLoader();
                root = loader.load(GraphicHandler.class.getResource(file_name).openStream());
            }
            else if ( stream == NO_STREAM )
                root = FXMLLoader.load( GraphicHandler.class.getResource(file_name) );
            else
                System.out.println( "Stream loading error.");
            scene.setRoot( root );

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
