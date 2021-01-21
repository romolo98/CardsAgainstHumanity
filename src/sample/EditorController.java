package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Carta;
import sample.DBConnector;

import java.io.IOException;
import java.sql.SQLException;

public class EditorController {

    public static int ID_Mazzo;

    private FXMLLoader loader = new FXMLLoader();

    private ObservableList<Carta> datiCarte = FXCollections.observableArrayList();

    @FXML
    private Button refreshButton;

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
    private TableColumn cardID;

    @FXML
    private TableColumn cardContent;

    @FXML
    private TableColumn cardType;

    @FXML
    private TableView cardTable;

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
        Parent root = loader.load(getClass().getResource("CardCreator.fxml"));
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
    void cancelEdit(ActionEvent event) throws IOException, SQLException {

    }

    public void RefreshPage(ActionEvent actionEvent) throws IOException, SQLException {
        for (int i = 1; i<= DBConnector.getInstance().getNoCarteMazzo(ID_Mazzo); i++) {
            datiCarte.add(new Carta(DBConnector.getInstance().getID_Carta(ID_Mazzo),DBConnector.getInstance().getContenuto(ID_Mazzo), DBConnector.getInstance().getTipologia(ID_Mazzo),ID_Mazzo));
        }
        cardID.setCellValueFactory(new PropertyValueFactory<Carta,String>("ID_Carta"));
        cardContent.setCellValueFactory(new PropertyValueFactory<Carta,Integer>("Contenuto"));
        cardType.setCellValueFactory(new PropertyValueFactory<Carta,Integer>("Tipologia"));
        cardTable.setItems(datiCarte);
    }
}