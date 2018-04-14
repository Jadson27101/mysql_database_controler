import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML Button btnExecute;
    @FXML TextArea showTable;
    @FXML TextField sqlExecute;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnExecute.setOnAction((e)->{
            showTable.setText("Hey bro");
        });
    }
}
