import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    @FXML
    TableView TableList;
    @FXML TextField address;
    @FXML TextField loginDB;
    @FXML TextField pwdDB;
    @FXML Button connect;
    @FXML TextArea state;
//    static final String DB_URL = "jdbc:mysql://localhost/Alpinism";
//    static final String USER = "root";
//    static final String PASS = "";
    private ArrayList<Object> resultOfTable;
    private ArrayList<Object> TableName;
    private Connection con;
    private String addressURL;
    private String login;
    private String pass;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        state.setDisable(true);
        showTable.setDisable(true);
        showTable.setStyle("-fx-opacity: 2.0 ;");
        connect.setOnAction((e) ->{
            addressURL = address.getText();
            login = loginDB.getText();
            pass = pwdDB.getText();
            try {
               con = connection(addressURL, login, pass);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        btnExecute.setOnAction((e) -> {
            String sql = sqlQuery.getText();
            try {
                databaseQuery(sql, con);
                showTable.setText(String.valueOf(resultOfTable));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
    private Connection connection(String DB_URL, String USER, String PASS) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        checkConnect(DB_URL, USER, PASS);
        return conn;
    }
    private void checkConnect(String DB_URL, String USER, String PASS) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)){
            state.setText("Access");
        } catch (SQLException e){
            state.setText("Can't connect");
        }
    }
    private Connection getConn(String DB_URL, String USER, String PASS){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch(Exception e){}
        return null;
    }
    private void databaseQuery(String sql, Connection conn) throws SQLException {
        Statement stmt = null;
        stmt = conn.createStatement();
        //String sql;
        //sql = "SELECT * FROM People";
        //String[]arrName = getColumnName(stmt, sql);
        //stmt.executeUpdate(sql);
            if(sql.charAt(0) == 'S'){
                ResultSet rs = stmt.executeQuery(sql);
                printTable(rs);
                rs.close();
            } else {
                stmt.execute(sql);
                getConn(addressURL, login, pass);
            }
            stmt.close();
    }

    private void printTable(ResultSet rs) throws SQLException {
        ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();
        resultOfTable = new ArrayList<>();
        while (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    String id = rs.getString(i+1);
                    resultOfTable.add(id);
                }
        }
    }

    private ArrayList<Object> getTableName(Connection conn) throws SQLException {
        TableName = new ArrayList<>();
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        while (rs.next()) {
            TableName.add(rs.getString(3));
        }
        return TableName;
    }
    private String[] getColumnName(Statement statement , String sql) throws SQLException {
        ResultSet results = statement.executeQuery(sql);
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
