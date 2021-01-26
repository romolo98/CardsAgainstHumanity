package logic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class GraphicHandler {

    private static Scene scene = null;
    private static FXMLLoader loader = null;
    public static final String path = "../view/";

    public static final int MAIN_SCREEN = 0;
    public static final int PLAY_SCREEN = 1;
    public static final int DECK_LIST_SCREEN = 2;
    public static final int DECK_EDIT_SCREEN = 3;
    public static final int CREATE_CARD_SCREEN = 4;
    public static final int CREATE_ROOM_SCREEN = 5;
    public static final int CONNECT_ROOM_SCREEN = 6;

    public static final int NO_STREAM = 0;
    public static final int OPEN_STREAM = 1;

    private static void init() {
        if ( scene == null ) {
            System.out.println("LANCIO INIT");
            Parent root;
            loader = new FXMLLoader();
            try {
                root = loader.load( GraphicHandler.class.getResource(path + "StartScreen.fxml") );
                scene = new Scene( root );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Scene getScene () {
        init();
        return scene;
    }

    public static FXMLLoader getLoader () {
        init();
        return loader;
    }

    public static FXMLLoader displayScreen (int display, int stream ) {
        init();
        String file_name;
        switch ( display ) {
            case MAIN_SCREEN:
                file_name = "StartScreen.fxml";
                break;
            case PLAY_SCREEN:
                file_name = "PlayScreen.fxml";
                break;
            case CREATE_ROOM_SCREEN:
                file_name = "CreateRoomScreen.fxml";
                break;
            case CONNECT_ROOM_SCREEN:
                file_name = "ConnectRoomScreen.fxml";
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
                return null;
        }

        System.out.println("ResetLoaderDisplayScreen");

        loader = new FXMLLoader();
        try {
            Parent root = null;
            if ( stream == OPEN_STREAM ) {
                root = loader.load( GraphicHandler.class.getResource(path + file_name).openStream() );
            }
            else if ( stream == NO_STREAM )
                root = loader.load( GraphicHandler.class.getResource(path + file_name) );
            else
                System.out.println( "Stream loading error.");
            scene.setRoot( root );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return loader;
    }

}
