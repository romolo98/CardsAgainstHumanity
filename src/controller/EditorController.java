package controller;

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
import logic.GraphicHandler;
import sample.Carta;
import sample.DBConnector;
import sample.Mazzo;

import java.io.IOException;
import java.sql.SQLException;

public class EditorController {

    public static int ID_Carta;

    private int ID_Mazzo;

    private FXMLLoader loader = new FXMLLoader();
    private FXMLLoader loader2 = new FXMLLoader();

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

    public void setDeckName(String nome) { deckName.setText(nome); }

    public ObservableList getDatiCarte (){ return datiCarte; }

    public void setID_Mazzo(int a){
        ID_Mazzo = a;
    }

    public int getID_Mazzo() {return ID_Mazzo;}

    public TableColumn getCardID (){return cardID;}

    public TableColumn getCardType (){return cardType;}

    public TableColumn getCardContent (){return cardContent;}

    public TableView getCardTable (){return cardTable;}

    @FXML
    void selectAllCards(ActionEvent event) {

    }

    @FXML
    void addCard(ActionEvent event) throws IOException, SQLException {
        Parent root = loader2.load(getClass().getResource(GraphicHandler.path + "CardCreator.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Create card");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void editCard(ActionEvent event) throws IOException {
        loader2 = new FXMLLoader();
        Parent root = loader2.load(getClass().getResource(GraphicHandler.path + "CardCreator.fxml").openStream());
        Stage stage = new Stage();
        stage.setTitle("Edit card");
        Carta c = (Carta)cardTable.getSelectionModel().getSelectedItem();
        ID_Carta = c.getID_Carta();
    /*
        CardCreatorController cardCreatorController = (CardCreatorController) loader2.getController();

        cardCreatorController.setTextArea(c.getContenuto());
        if (c.getTipologia() == "Bianca"){
            cardCreatorController.setCheckWhiteCard();
        }
        else{
            cardCreatorController.setCheckBlackCard();
        }

        QUESTO é IL CODICE PER RIPORTARE LE INFO DELLA CARTA NELLA SCHERMATA DI EDIT. FUNZIONA SOLO LA PRIMA VOLTA POI CRASHA
        FORSE UN GIORNO LO AGGIUSTEREMO. MA NON é QUESTO IL GIORNO.
     */
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void deleteCard(ActionEvent event) throws SQLException {
        ID_Mazzo = ManagerController.ID_Mazzo;
        Carta c = (Carta) cardTable.getSelectionModel().getSelectedItem();


        for (int j=0;j<datiCarte.size();j++) {
            if (datiCarte.get(j).getID_Carta() == c.getID_Carta()){
                datiCarte.remove(datiCarte.get(j));
                DBConnector.getInstance().deleteCard(c.getID_Carta());
            }
        }
        cardContent.setCellValueFactory(new PropertyValueFactory<Carta,String>("contenuto"));
        cardID.setCellValueFactory(new PropertyValueFactory<Carta,Integer>("ID_Carta"));
        cardType.setCellValueFactory(new PropertyValueFactory<Carta,String>("tipologia"));
        cardTable.setItems(datiCarte);
    }

    //IMPLEMENTATI SAVE DECK MA SONO DA TESTARE VISTO CHE NON POSSO LANCIARE IL CODICE

    @FXML
    void saveDeck(ActionEvent event) throws SQLException {
        String name = deckName.getText();
        DBConnector.getInstance().setNome(name,ManagerController.ID_Mazzo);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cambiato nome del mazzo", ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    void saveNewName(ActionEvent event) throws SQLException {
        String name = deckName.getText();
        DBConnector.getInstance().setNome(name,ManagerController.ID_Mazzo);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cambiato nome del mazzo", ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    void cancelEdit(ActionEvent event) throws IOException, SQLException {
        loader = GraphicHandler.displayScreen( GraphicHandler.DECK_LIST_SCREEN, GraphicHandler.OPEN_STREAM);
        ManagerController managerController = loader.getController();

        for (int i=1;i<=DBConnector.getInstance().getNoMazzi();i++) {
            managerController.getDatiMazzo().add(new Mazzo(DBConnector.getInstance().getID_Mazzo(i), DBConnector.getInstance().getNome(i), DBConnector.getInstance().getNoCarte(i), DBConnector.getInstance().getGiocabile(i), DBConnector.getInstance().getNoCarteNereMazzo(DBConnector.getInstance().getID_Mazzo(i)),DBConnector.getInstance().getNoCarteBiancheMazzo(DBConnector.getInstance().getID_Mazzo(i))));
        }
        managerController.getColName().setCellValueFactory(new PropertyValueFactory<Mazzo,String>("nome"));
        managerController.getColTotal().setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("noCarte"));
        managerController.getColID().setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("ID_Mazzo"));
        managerController.getColBlack().setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("black"));
        managerController.getColWhite().setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("white"));
        managerController.getColReady().setCellValueFactory(new PropertyValueFactory<Mazzo,Boolean>("giocabile"));
        managerController.getTable().setItems(managerController.getDatiMazzo());
    }

    public void RefreshPage(ActionEvent actionEvent) throws IOException, SQLException {
        ID_Mazzo = ManagerController.ID_Mazzo;
        boolean check = true;
        for (int i=1;i<=DBConnector.getInstance().getNoCarteMazzo(ID_Mazzo);i++) {
            Carta c = new Carta(DBConnector.getInstance().getID_Carta(i,ID_Mazzo), DBConnector.getInstance().getContenuto(i,ID_Mazzo), DBConnector.getInstance().getTipologia(i,ID_Mazzo), ID_Mazzo);
            for (int j=0;j<datiCarte.size();j++) {
                if (datiCarte.get(j).getID_Carta() == c.getID_Carta())
                    check = false;
                if (datiCarte.get(j).getID_Carta() == c.getID_Carta() && (datiCarte.get(j).getContenuto() != c.getContenuto() || datiCarte.get(j).getTipologia() != c.getTipologia())){
                    datiCarte.set(j,c);
                    System.out.println("entro");
                }
            }
            if (check){
                datiCarte.add(c);
            }
            check = true;
        }
        cardContent.setCellValueFactory(new PropertyValueFactory<Carta,String>("contenuto"));
        cardID.setCellValueFactory(new PropertyValueFactory<Carta,Integer>("ID_Carta"));
        cardType.setCellValueFactory(new PropertyValueFactory<Carta,String>("tipologia"));
        cardTable.setItems(datiCarte);
    }
}