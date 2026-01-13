
import java.sql.*;

public class GetCredentials {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/collegedata?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            
            System.out.println("--- CREDENTIALS FOUND IN DATABASE ---");

            // Admin
            ResultSet rs = stmt.executeQuery("SELECT password FROM admin LIMIT 1");
            if(rs.next()) {
                System.out.println("Admin: UserID='admin', Password='" + rs.getString("password") + "'");
            }
            rs.close();

            // Faculty
            rs = stmt.executeQuery("SELECT facultyid, password, facultyname FROM faculties LIMIT 1");
            if(rs.next()) {
                System.out.println("Faculty: UserID='" + rs.getString("facultyid") + "', Password='" + rs.getString("password") + "' (Name: " + rs.getString("facultyname") + ")");
            } else {
                 System.out.println("Faculty: No faculty found in database. You need to create one via Admin login.");
            }
            rs.close();

            // Student
            rs = stmt.executeQuery("SELECT userid, password, firstname FROM students LIMIT 1");
            if(rs.next()) {
                System.out.println("Student: UserID='" + rs.getString("userid") + "', Password='" + rs.getString("password") + "' (Name: " + rs.getString("firstname") + ")");
            } else {
                System.out.println("Student: No students found in database. You need to create one via Admin login.");
            }
            rs.close();
             System.out.println("-------------------------------------");
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
