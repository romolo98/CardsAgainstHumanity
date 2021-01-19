package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EditorController {

    public static int ID_Mazzo;

    private FXMLLoader loader = new FXMLLoader();

    @FXML
    private Button addCardButton;

    @FXML
    private Button editCardButton;

    @FXML
    private Label blackCardsNo;

    @FXML
    private Button deleteCardButton;

    @FXML
    private Label whiteCardsNo;

    @FXML
    private FlowPane cardList;

    @FXML
    private Label selectedCardsNo;

    @FXML
    private Label totalCardsNo1;

    @FXML
    private Button cancelButton;

    @FXML
    private Label totalCardsNo0;

    @FXML
    private ChoiceBox<?> sortList;

    @FXML
    private AnchorPane anchor;

    @FXML
    private TextField deckName;

    @FXML
    private Button saveButton;

    @FXML
    private CheckBox selectAllBox;

    public void setID_Mazzo(int a){
        ID_Mazzo = a;
    }

    public int getID_Mazzo() {return ID_Mazzo;}

    @FXML
    void selectAllCards(ActionEvent event) {

    }

    @FXML
    void addCard(ActionEvent event) throws IOException, SQLException {
        Parent root = loader.load(getClass().getResource("CardCreator.fxml").openStream());
        Stage stage = new Stage();
        stage.setTitle("Create card");
        stage.setScene(new Scene(root));
        stage.show();


        //CardCreatorController cardCreatorController = loader.getController();


        //DBConnector.getInstance().addCarta("","Bianca",ID_Mazzo);

    }

    @FXML
    void editCard(ActionEvent event) {

    }

    @FXML
    void deleteCard(ActionEvent event) {

    }

    @FXML
    void saveDeck(ActionEvent event) {

    }

    @FXML
    void saveNewName(ActionEvent event) {

    }

    @FXML
    void cancelEdit(ActionEvent event) {

    }

}