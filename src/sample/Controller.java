package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    public TextArea textArea;

    @FXML
    public TextField textField;
    public MenuItem btn;
    public MenuBar menuBar;


    @FXML
    public void clickSendButton(ActionEvent actionEvent) {
        String inputTxt = textField.getText();
        if (inputTxt.length() > 0) {
            textArea.appendText(inputTxt + "\n");
            textField.clear();
            textField.requestFocus();
        }
    }

    @FXML
    public void clickExit() {
        Platform.runLater(() -> {
            Stage stage = (Stage) menuBar.getScene().getWindow();
            stage.close();
        });
    }
}
