package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CardCreatorController {

    private FXMLLoader loader = new FXMLLoader();

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
    void ActionSaveCard(ActionEvent event) throws SQLException, IOException {
        if (checkWhiteCard.isSelected())
            DBConnector.getInstance().addCarta(writeCard.getText(),"Bianca",EditorController.ID_Mazzo);
        if (checkBlackCard.isSelected())
            DBConnector.getInstance().addCarta(writeCard.getText(),"Nera",EditorController.ID_Mazzo);

        Stage stage = (Stage) saveCard.getScene().getWindow();
        stage.close();
    }

    @FXML
    void ActionWhiteCardSelected(ActionEvent event) {
        if (checkBlackCard.isSelected()){
            checkBlackCard.setSelected(false);
        }
    }

}

