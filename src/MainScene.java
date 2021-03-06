import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MainScene extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Styles.fxml"));
        Scene scene = new Scene(root, 600,450);
        primaryStage.setTitle("Frol database");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
