import java.sql.*;
import java.util.Scanner;

public class DatabaseTest {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Alpinism";
    static final String USER = "root";
    static final String PASS = "";

    private void printTable() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM Employee";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("ID");
                int age = rs.getInt("Age");
                String first = rs.getString("First");
                String last = rs.getString("Last");
                int Money = rs.getInt("Money");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.print(", Last: " + last);
                System.out.println(", Moneyt: " + Money);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    private void createNewTable(String name) {
        // SQLite connection string

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + name +" (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Database "+name+" created");
    }

    private void dropTable(String databaseName) {
        String sql = "DROP TABLE"+databaseName;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()){
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database deleted!");
    }

    public static void main(String[] args) {
        DatabaseTest databaseTest = new DatabaseTest();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your query");
        String sql = sc.next();


    }
}