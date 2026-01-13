
import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class SetupDB {
    // New driver class name for Connector/J 8.0+
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // Using standard params
    static final String DB_URL = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            
            System.out.println("Creating database...");
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS collegedata");
            System.out.println("Database created successfully...");
            
            stmt.close();
            conn.close();
            
            // Now connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegedata?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", USER, PASS);
            stmt = conn.createStatement();
            
            System.out.println("Importing SQL...");
            File sqlFile = new File("collegedata.sql");
            Scanner scanner = new Scanner(sqlFile);
            scanner.useDelimiter(";");
            
            while (scanner.hasNext()) {
                String sql = scanner.next().trim();
                // Basic cleanup
                if (sql.isEmpty() || sql.startsWith("/*")) {
                    continue;
                }
                
                try {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    // Ignore already exists
                    System.out.println("Note: " + e.getMessage());
                }
            }
            scanner.close();
            System.out.println("SQL Import complete.");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
