package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class EditorController {

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

    @FXML
    void selectAllCards(ActionEvent event) {

    }

    @FXML
    void addCard(ActionEvent event) {

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