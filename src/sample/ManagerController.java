package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class ManagerController {

    private int ID_Mazzo;

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
    private TableColumn<?, ?> colID;

    @FXML
    void searchDeck(ActionEvent event) {

    }

    @FXML
    void previousScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateRoomScreen.fxml"));
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