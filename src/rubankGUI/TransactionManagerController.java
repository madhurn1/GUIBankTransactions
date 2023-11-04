package rubankGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TransactionManagerController {
    @FXML
    private TextField inputField;

    @FXML
    private Button submitButton;

    @FXML
    public void handleButtonClick() {
        String inputText = inputField.getText();
   }
}
