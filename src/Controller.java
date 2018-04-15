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
    @FXML
    Button btnExecute;
    @FXML
    TextArea showTable;
    @FXML
    TextField sqlQuery;
    static final String DB_URL = "jdbc:mysql://localhost/Alpinism";
    static final String USER = "root";
    static final String PASS = "";
    ArrayList<Object> resultOfTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnExecute.setOnAction((e) -> {
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
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        //String sql;
        //sql = "SELECT * FROM People";
        String[]arrName = getColumnName(stmt);
        ResultSet rs = stmt.executeQuery(sql);
        printTable(rs, arrName);
        rs.close();
        stmt.close();
        conn.close();
    }

    private void printTable(ResultSet rs, String arr[]) throws SQLException {
        resultOfTable = new ArrayList<>();
        while (rs.next()) {
            //Retrieve by column name
            for (int i = 0; i < arr.length; i++) {
                String id = rs.getString(arr[i]);
                resultOfTable.add(id);
            }
        }
    }

    private String[] getColumnName(Statement statement) throws SQLException {
        ResultSet results = statement.executeQuery("SELECT * FROM People");
        ResultSetMetaData metaData = results.getMetaData();
        int count = metaData.getColumnCount(); //number of column
        String columnName[] = new String[count];
        for (int i = 1; i <= count; i++) {
            columnName[i - 1] = metaData.getColumnLabel(i);
            System.out.println(columnName[i - 1]);
        }
        results.close();
        return columnName;
    }
}
