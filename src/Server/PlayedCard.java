package Server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PlayedCard {

    private AnchorPane model = null;
    private int playerID;
    private String cardType;
    private String cardContent;
    private int cardID;
    private int deckID;

    @FXML
    private Label cardText;


    public PlayedCard ( int playerID, String cardType, String cardText, int cardID, int deckID ) {
        try {
            model = FXMLLoader.load( getClass().getResource( "view/CardModel.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.playerID = playerID;
        this.cardType = cardType;
        this.cardText.setText( cardText );
        this.cardContent = cardText;
        this.cardID = cardID;
        this.deckID = deckID;
    }

    public AnchorPane getModel() {
        return model;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardContent() {
        return cardContent;
    }

    public int getCardID() {
        return cardID;
    }

    public int getDeckID() {
        return deckID;
    }
}
