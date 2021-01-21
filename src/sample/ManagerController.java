package sample;

import com.sun.javafx.menu.SeparatorMenuItemBase;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ManagerController {

    private int ID_Mazzo;

    private ObservableList<Mazzo> datiMazzo = FXCollections.observableArrayList();

    @FXML
    private TableColumn<?, ?> colCommands;

    @FXML
    private Button newDeckButton;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTotal;

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
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("DeckScreen.fxml").openStream());
        newDeckButton.getScene().setRoot(root);
        EditorController editorController  = loader.getController();
        editorController.setID_Mazzo(ID_Mazzo);
    }

}