import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML Button btnExecute;
    @FXML TextArea showTable;
    @FXML TextField sqlQuery;
    static final String DB_URL = "jdbc:mysql://localhost/Alpinism";
    static final String USER = "root";
    static final String PASS = "";
    ArrayList<Object> resultOfTable;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller controller = new Controller();
        btnExecute.setOnAction((e)->{
            String sql = sqlQuery.getText();
            try {
                databaseQuery(sql);
                showTable.setText(String.valueOf(resultOfTable));
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
    private void databaseQuery(String sql) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;
        //STEP 2: Register JDBC driver
        Class.forName("com.mysql.jdbc.Driver");

        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        //STEP 4: Execute a query
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        //String sql;
        //sql = "SELECT * FROM People";
        ResultSet rs = stmt.executeQuery(sql);

        //STEP 5: Extract data from result set
        resultOfTable = new ArrayList<>();
        while (rs.next()) {
            //Retrieve by column name
            int id = rs.getInt("id");
            String first = rs.getString("FirstName");
            String last = rs.getString("LastName");
            String address = rs.getString("Address");
            resultOfTable.add(id);
            resultOfTable.add(first);
            resultOfTable.add(last);
            resultOfTable.add(address);
        }
        //STEP 6: Clean-up environment
        rs.close();
        stmt.close();
        conn.close();
    }
}
