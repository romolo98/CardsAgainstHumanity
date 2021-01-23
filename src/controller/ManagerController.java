package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.GraphicHandler;
import sample.Carta;
import sample.DBConnector;
import sample.Mazzo;

import java.io.IOException;
import java.sql.SQLException;

public class ManagerController {

    public static int ID_Mazzo;

    private FXMLLoader loader = new FXMLLoader();

    private ObservableList<Mazzo> datiMazzo = FXCollections.observableArrayList();

    @FXML
    private TableColumn<?, ?> colReady;

    @FXML
    private Button newDeckButton;

    @FXML
    private TableColumn<Mazzo, String> colName;

    @FXML
    private TableColumn<Mazzo, Integer> colTotal;

    @FXML
    private Label totalDecks;

    @FXML
    private TextField searchBar;

    @FXML
    private TableColumn<?, ?> colBlack;

    @FXML
    private TableColumn<?, ?> colWhite;

    @FXML
    private Button backButton;

    @FXML
    private Button editDeckButton;

    @FXML
    private TableView table;

    @FXML
    private TableColumn colID;

    public TableView getTable() {
        return table;
    }

    public ObservableList<Mazzo> getDatiMazzo() {
        return datiMazzo;
    }

    public TableColumn getColReady() {return colReady;}
    public TableColumn getColBlack() {return colBlack;}
    public TableColumn getColWhite() {return colWhite;}
    public TableColumn getColID(){
        return colID;
    }
    public TableColumn getColName(){
        return colName;
    }
    public TableColumn getColTotal(){
        return colTotal;
    }

    @FXML
    void searchDeck(ActionEvent event) {

    }

    @FXML
    void previousScreen(ActionEvent event) throws IOException {
        GraphicHandler.displayScreen( GraphicHandler.MAIN_SCREEN, GraphicHandler.NO_STREAM );
    }

    @FXML
    void createNewDeck(ActionEvent event) throws IOException, SQLException {
        ID_Mazzo = DBConnector.getInstance().addMazzo("New Deck");
        loader = GraphicHandler.displayScreen( GraphicHandler.DECK_EDIT_SCREEN, GraphicHandler.OPEN_STREAM );
        EditorController editorController  = loader.getController();
        editorController.setID_Mazzo(ID_Mazzo);
    }

    public void EditDeck(ActionEvent actionEvent) throws SQLException, IOException {
        Mazzo m = (Mazzo) table.getSelectionModel().getSelectedItem();
        if (m != null) {
            ID_Mazzo = m.getID_Mazzo();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "E SELEZIONARE NU DECK, OI CIUETU", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        loader = GraphicHandler.displayScreen( GraphicHandler.DECK_EDIT_SCREEN, GraphicHandler.OPEN_STREAM );

        EditorController editorController = loader.getController();
        editorController.setDeckName(m.getNome());

        for (int i=1;i<=DBConnector.getInstance().getNoCarteMazzo(ID_Mazzo);i++) {
            editorController.getDatiCarte().add(new Carta(DBConnector.getInstance().getID_Carta(i,ID_Mazzo), DBConnector.getInstance().getContenuto(i,ID_Mazzo), DBConnector.getInstance().getTipologia(i,ID_Mazzo), ID_Mazzo));
        }
        editorController.getCardContent().setCellValueFactory(new PropertyValueFactory<Carta,String>("contenuto"));
        editorController.getCardID().setCellValueFactory(new PropertyValueFactory<Carta,Integer>("ID_Carta"));
        editorController.getCardType().setCellValueFactory(new PropertyValueFactory<Carta,String>("tipologia"));
        editorController.getCardTable().setItems(editorController.getDatiCarte());
    }

    public void deleteDeck(ActionEvent actionEvent) throws SQLException {
        Mazzo m = (Mazzo) table.getSelectionModel().getSelectedItem();
        ID_Mazzo = m.getID_Mazzo();

        for (int j=0;j<datiMazzo.size();j++) {
            if (datiMazzo.get(j).getID_Mazzo() == m.getID_Mazzo()) {
                datiMazzo.remove(datiMazzo.get(j));
                DBConnector.getInstance().deleteMazzo(ID_Mazzo);
            }
        }

        colName.setCellValueFactory(new PropertyValueFactory<Mazzo,String>("nome"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("noCarte"));
        colID.setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("ID_Mazzo"));
        table.setItems(datiMazzo);
    }
}