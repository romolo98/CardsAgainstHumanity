package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

public class CardCreatorController {

    @FXML
    private RadioButton checkWhiteCard;

    @FXML
    private RadioButton checkBlackCard;

    @FXML
    private Button saveCard;

    @FXML
    private TextArea writeCard;

    @FXML
    private Label instructionLabel;

    @FXML
    void ActionBlackCardSelected(ActionEvent event) {
        if (checkWhiteCard.isSelected()){
            checkWhiteCard.setSelected(false);
        }

    }

    @FXML
    void ActionSaveCard(ActionEvent event) {

    }

    @FXML
    void ActionWhiteCardSelected(ActionEvent event) {
        if (checkBlackCard.isSelected()){
            checkBlackCard.setSelected(false);
        }
    }

}

