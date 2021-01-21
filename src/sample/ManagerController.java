package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;

public class ManagerController {

    public static int ID_Mazzo;

    private FXMLLoader loader = new FXMLLoader();

    private ObservableList<Mazzo> datiMazzo = FXCollections.observableArrayList();

    @FXML
    private TableColumn<?, ?> colCommands;

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
        Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        backButton.getScene().setRoot(root);
    }

    @FXML
    void createNewDeck(ActionEvent event) throws IOException, SQLException {
        ID_Mazzo = DBConnector.getInstance().addMazzo("Prova");
        Parent root = loader.load(getClass().getResource("DeckScreen.fxml").openStream());
        newDeckButton.getScene().setRoot(root);
        EditorController editorController  = loader.getController();
        editorController.setID_Mazzo(ID_Mazzo);
    }

    public void EditDeck(ActionEvent actionEvent) throws SQLException, IOException {
        Mazzo m = (Mazzo) table.getSelectionModel().getSelectedItem();
        ID_Mazzo = m.getID_Mazzo();
        System.out.println(ID_Mazzo);

        Parent root = loader.load(getClass().getResource("DeckScreen.fxml").openStream());
        editDeckButton.getScene().setRoot(root);

        EditorController editorController = loader.getController();
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
        System.out.println(ID_Mazzo);

        for (int j=0;j<datiMazzo.size();j++) {
            if (datiMazzo.get(j).getID_Mazzo() == m.getID_Mazzo()) {
                datiMazzo.remove(datiMazzo.get(j));
                DBConnector.getInstance().deleteMazzo(ID_Mazzo);
                System.out.println("entro");
            }
        }

        colName.setCellValueFactory(new PropertyValueFactory<Mazzo,String>("nome"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("noCarte"));
        colID.setCellValueFactory(new PropertyValueFactory<Mazzo,Integer>("ID_Mazzo"));
        table.setItems(datiMazzo);
    }
}